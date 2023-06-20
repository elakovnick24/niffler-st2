package niffler.db.dao;

import niffler.db.DataSourceProvider;
import niffler.db.ServiceDB;
import niffler.db.entity.AuthorityEntity;
import niffler.db.entity.UserEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class NifflerUsersDAOSpringJdbc implements NifflerUsersDAO {

    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public NifflerUsersDAOSpringJdbc() {
        DataSourceTransactionManager transactionManager = new JdbcTransactionManager(
                DataSourceProvider.INSTANCE.getDataSource(ServiceDB.NIFFLER_AUTH)
        );
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.jdbcTemplate = new JdbcTemplate(DataSourceProvider.INSTANCE.getDataSource(ServiceDB.NIFFLER_AUTH));
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    }

    @Override
    public int createUser(UserEntity user) {
        transactionTemplate.execute(st -> {
            Map<String, Object> userEntity = new HashMap<>();
            userEntity.put("username", user.getUsername());
            userEntity.put("password", encoder.encode(user.getPassword()));
            userEntity.put("enabled", user.getEnabled());
            userEntity.put("account_non_expired", user.getAccountNonExpired());
            userEntity.put("account_non_locked", user.getAccountNonLocked());
            userEntity.put("credentials_non_expired", user.getCredentialsNonExpired());

            KeyHolder keyHolder = simpleJdbcInsert
                    .withTableName("users")
                    .usingColumns("username", "password", "enabled", "account_non_expired", "account_non_locked", "credentials_non_expired")
                    .usingGeneratedKeyColumns("id")
                    .withoutTableColumnMetaDataAccess()
                    .executeAndReturnKeyHolder(userEntity);

            UUID id = (UUID) Objects.requireNonNull(keyHolder.getKeys()).get("id");
            user.setId(id);

            for (AuthorityEntity authorityEntity : user.getAuthorities()) {
                jdbcTemplate.update("INSERT INTO authorities (user_id, authority) VALUES (?, ?)",
                        user.getId(), authorityEntity.getAuthority().name());
            }
            return 1;
        });
        return 1;
    }

    @Override
    public String getUserId(String username) {
        return jdbcTemplate.query("SELECT FROM users WHERE id = ?", rs -> {return rs.getString(1);},
                username
        );
    }

    @Override
    public UserEntity getUser(UserEntity userEntity) {
        return jdbcTemplate.query("SELECT FROM users WHERE id = ?", new Object[]{userEntity.getId()}, new BeanPropertyRowMapper<>(UserEntity.class))
                .stream().findAny().orElse(null);
    }

    @Override
    public int updateUser(UserEntity user) {
        return jdbcTemplate.update("UPDATE UserEntity SET" +
                "username=?, password=?,enabled=?,account_non_expired=?, account_non_locked=?, credentials_non_expired=?" +
                "WHERE id=?",
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                user.getAccountNonExpired(),
                user.getAccountNonLocked(),
                user.getCredentialsNonExpired());
    }

    @Override
    public int removeUser(UserEntity user) throws SQLException {
        return transactionTemplate.execute(st -> {
            jdbcTemplate.update("DELETE FROM authorities WHERE user_id = ?", user.getId());
            return jdbcTemplate.update("DELETE FROM users WHERE id = ?", user.getId());
        });
    }
}
