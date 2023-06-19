package niffler.db.dao;

import niffler.db.DataSourceProvider;
import niffler.db.ServiceDB;
import niffler.db.entity.UserEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;
import java.util.UUID;

public class NifflerUsersDAOSpringJdbc implements NifflerUsersDAO {

    private final TransactionTemplate transactionTemplate;
    private final JdbcTemplate jdbcTemplate;

    public NifflerUsersDAOSpringJdbc(TransactionTemplate transactionTemplate) {
        DataSourceTransactionManager transactionManager = new JdbcTransactionManager(
                DataSourceProvider.INSTANCE.getDataSource(ServiceDB.NIFFLER_AUTH)
        );
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.jdbcTemplate = new JdbcTemplate(DataSourceProvider.INSTANCE.getDataSource(ServiceDB.NIFFLER_AUTH));
    }

    @Override
    public int createUser(UserEntity user) {
        return 0;
    }

    @Override
    public UserEntity getUser(UUID uuid) {
        return null;
    }

    @Override
    public int updateUser(UserEntity user) {
        return 0;
    }

    @Override
    public int removeUser(UserEntity user) throws SQLException {
        return transactionTemplate.execute(st -> {
            jdbcTemplate.update("DELETE FROM authorities WHERE user_id = ?", user.getId());
            return jdbcTemplate.update("DELETE FROM users WHERE id = ?", user.getId());
        });
    }

    @Override
    public String getUserId(String username) {
        return jdbcTemplate.query("SELECT FROM users WHERE id = ?", rs -> {return rs.getString(1);},
                username
        );
    }
}
