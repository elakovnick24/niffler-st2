package niffler.db.dao;

import niffler.db.ServiceDB;
import niffler.db.entity.AuthorityEntity;
import niffler.db.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.UUID;

import static niffler.db.DataSourceProvider.INSTANCE;
import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public class NifflerUsersDAOJdbc implements NifflerUsersDAO {

    private static final DataSource ds = INSTANCE.getDataSource(ServiceDB.NIFFLER_AUTH);
    private static final PasswordEncoder encoder = createDelegatingPasswordEncoder();

    @Override
    public int createUser(UserEntity user) {
        int executeUpdate;

        try (Connection conn = ds.getConnection()) {

            conn.setAutoCommit(false);

            try (PreparedStatement insertUserSt = conn.prepareStatement("INSERT INTO users "
                    + "(username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired) "
                    + " VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement insertAuthoritySt = conn.prepareStatement(
                         "INSERT INTO authorities (user_id, authority) VALUES (?, ?)")) {
                insertUserSt.setString(1, user.getUsername());
                insertUserSt.setString(2, encoder.encode(user.getPassword()));
                insertUserSt.setBoolean(3, user.getEnabled());
                insertUserSt.setBoolean(4, user.getAccountNonExpired());
                insertUserSt.setBoolean(5, user.getAccountNonLocked());
                insertUserSt.setBoolean(6, user.getCredentialsNonExpired());
                executeUpdate = insertUserSt.executeUpdate();

                final UUID finalUserId;

                try (ResultSet generatedKeys = insertUserSt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        finalUserId = UUID.fromString(generatedKeys.getString(1));
                        user.setId(finalUserId);
                    } else {
                        throw new SQLException("Creating user failed, no ID present");
                    }
                }

                for (AuthorityEntity authority : user.getAuthorities()) {
                    insertAuthoritySt.setObject(1, finalUserId);
                    insertAuthoritySt.setString(2, authority.getAuthority().name());
                    insertAuthoritySt.addBatch();
                    insertAuthoritySt.clearParameters();
                }
                insertAuthoritySt.executeBatch();
            } catch (SQLException e) {
                conn.rollback();
                conn.setAutoCommit(true);
                throw new RuntimeException(e);
            }

            conn.commit();
            conn.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return executeUpdate;
    }

    @Override
    public UserEntity getUser(UUID uuid) {
        UserEntity user = null;
        try (Connection conn = ds.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT FROM users WHERE id = ?")) {
            st.setObject(1, uuid);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                user.setId(UUID.fromString(resultSet.getString(1)));
                user.setUsername(resultSet.getString(2));
                user.setPassword(resultSet.getString(3));
                user.setEnabled(resultSet.getBoolean(4));
                user.setAccountNonExpired(resultSet.getBoolean(5));
                user.setAccountNonLocked(resultSet.getBoolean(6));
                user.setCredentialsNonExpired(resultSet.getBoolean(7));
                return user;
            } else {
                throw new IllegalArgumentException("Can't find user by given UUID: " + uuid);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUser(UserEntity user) {
        int executeUpdate;

        try (Connection conn = ds.getConnection();
             PreparedStatement st = conn.prepareStatement("UPDATE users SET "
                     + "(username, password, enabled, account_non_expired, account_non_locked, credentials_non_expired)="
                     + "(?, ?, ?, ?, ?, ?) WHERE id=(?)")) {
            st.setString(1, user.getUsername());
            st.setString(2, encoder.encode(user.getPassword()));
            st.setBoolean(3, user.getEnabled());
            st.setBoolean(4, user.getAccountNonExpired());
            st.setBoolean(5, user.getAccountNonLocked());
            st.setBoolean(6, user.getCredentialsNonExpired());
            st.setObject(7, user.getId());
            executeUpdate = st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return executeUpdate;
    }

    @Override
    public void deleteUser(UserEntity user) throws SQLException {
        UUID uuid = user.getId();
        int rowsDeleted1 = 0;
        int rowsDeleted2 = 0;
        try (Connection conn = ds.getConnection()) {
            conn.setAutoCommit(false);
            try (
                    PreparedStatement st1 = conn.prepareStatement("DELETE FROM authorities WHERE user_id = ? ");
                    PreparedStatement st2 = conn.prepareStatement("DELETE FROM users WHERE id = ? ")
            ) {
                st1.setObject(1, uuid);
                rowsDeleted1 = st1.executeUpdate();
                st2.setObject(1, uuid);
                rowsDeleted2 = st2.executeUpdate();
            } catch (SQLException e) {
                // Эту обработку взял из доки as is
                if (conn != null) {
                    try {
                        System.err.print("Transaction is being rolled back");
                        conn.rollback();
                    } catch (SQLException excep) {
                        throw new RuntimeException(excep);
                    } finally {
                        conn.setAutoCommit(true);
                    }
                }
            }
            conn.commit();
            System.out.println("Total deleted from authorities: " + rowsDeleted1 + " rows");
            System.out.println("Total deleted from users: " + rowsDeleted2 + " rows");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getUserId(String username) {
        try (Connection conn = ds.getConnection();
             PreparedStatement st = conn.prepareStatement("SELECT * FROM users WHERE username = ? ")) {
            st.setString(1, username);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            } else {
                throw new IllegalArgumentException("Can't find user by given username: " + username);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
