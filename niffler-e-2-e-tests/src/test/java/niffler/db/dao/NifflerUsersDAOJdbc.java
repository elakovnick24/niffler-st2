package niffler.db.dao;

import niffler.db.ServiceDB;
import niffler.db.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

import static niffler.db.DataSourceProvider.INSTANCE;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public class NifflerUsersDAOJdbc implements NifflerUsersDAO {

    // DataSource используется для подключения к базе данных
    private static final DataSource ds = INSTANCE.getDataSource(ServiceDB.NIFFLER_AUTH);
    // encoder - используется для хэширования паролей пользователей
    private static final PasswordEncoder encoder = createDelegatingPasswordEncoder();

    // Метод принимает объект UserEntity в качестве аргумента, представляющий информацию о создаваемом пользователе
    @Override
    public int createUser(UserEntity user) {
        // Реализация через Statements
//        String insertSql = "INSERT INTO users "
//                + "(username, password, enabled, accountNonExpired, accountNonLocked, credentialsNonExpired) "
//                + "VALUES ("
//                + user.getUsername()
//                + "', '"
//                + user.getPassword()
//                + "', '"
//                + user.getEnabled()
//                + "', '"
//                + user.getAccountNonExpired()
//                + "', '"
//                + user.getAccountNonExpired()
//                + "', '"
//                + user.getCredentialsNonExpired()
//                + ");";
//        String userId = null;
        int executeUpdate;

        // Создается подключение к бд с помощью ds.getConnection()
        try (Connection conn = ds.getConnection();
//             Statement st = conn.createStatement()) {

             // PreparedStatement используется для выполнения безопасной вставки данных пользователя
             // SQL-выражение с заполнителями (?) для значений, которые будут переданы в качестве параметров,
             // Затем эти параметры устанавливаются с помощью методов setString(), setBoolean()
             // Это обеспечивает безопасность от SQL-инъекций и предотвращает несанкционированный доступ к данным
             PreparedStatement st = conn.prepareStatement("INSERT INTO users "
                             + "(username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) "
                             + "VALUES (?, ?, ?, ?, ?, ?)")) {
            st.setString(1, user.getUsername());
            st.setString(2, encoder.encode(user.getPassword()));
            st.setBoolean(3, user.getEnabled());
            st.setBoolean(4, user.getAccountNonExpired());
            st.setBoolean(5, user.getAccountNonLocked());
            st.setBoolean(6, user.getCredentialsNonExpired());
            // Выполняет SQL-запрос на вставку и сохраняет результат в переменной
            executeUpdate = st.executeUpdate();

/*            Реализация через Statements
            ResultSet resultSet = st.executeQuery(
                    "SELECT * FROM users WHERE username = '" + user.getUsername() + "'"
            );

            if (resultSet.next()) {
                userId = resultSet.getString(0);
            }*/
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String insertAuthoritiesSql =
                "INSERT INTO authorities (user_id, authority) VALUES ('%s', '%s')";

        final String finalUserId = getUserId(user.getUsername());
        // Для каждой AuthorityEntity в списке user.getAuthorities() выполняется следующее:
        List<String> sqls = user.getAuthorities()
                .stream()
                //Вызывается метод getAuthority().name() для получения имени Authority (роли) в виде строки
                .map(ae -> ae.getAuthority().name())
                // С помощью метода String.format() заполняется SQL-запрос insertAuthoritiesSql значениями 'finalUserId' и имени роли 'a'
                .map(a -> String.format(insertAuthoritiesSql, finalUserId, a))
                .toList();
        // Полученная сформированная строка добавляется в список sqls

        for (String sql : sqls) {
            // Коннект к бд. Connection с помощью метода ds.getConnection() --> ds - DataSource
            try (Connection conn = ds.getConnection();
                 // Создается объект Statement для выполнения SQL-запросов с помощью метода conn.createStatement()
                 Statement st = conn.createStatement()) {
                // Вызывается метод st.executeUpdate(sql) для выполнения SQL-запроса и внесения изменений в бд
                st.executeUpdate(sql);
                // Обработка исключения, если в процессе выполнения SQL-запроса возникает исключение SQLException
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // Возвращается значение переменной executeUpdate, которое было получено после выполнения первого SQL-запроса.
        return executeUpdate;
    }

    @Override
    public String getUserId(String username) {
        try (Connection conn = ds.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE username = ?")) {
            st.setString(1, username);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                throw new IllegalArgumentException("Can't find user by giben username: " + username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
