package niffler.db.dao;

import niffler.db.entity.UserEntity;

import java.sql.SQLException;
import java.util.UUID;

public class NifflerUsersDAOSpringJdbc implements NifflerUsersDAO{
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
    public void deleteUser(UserEntity user) throws SQLException {

    }

    @Override
    public String getUserId(String username) {
        return null;
    }
}
