package niffler.db.dao;

import niffler.db.entity.UserEntity;

import java.sql.SQLException;
import java.util.UUID;

public interface NifflerUsersDAO {

    int createUser(UserEntity user);

    UserEntity getUser(UUID uuid);


    int updateUser(UserEntity user);

    void deleteUser(UserEntity user) throws SQLException;

    String getUserId(String username);



}
