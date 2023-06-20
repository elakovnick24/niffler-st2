package niffler.db.dao;

import niffler.db.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.UUID;

import static org.springframework.security.crypto.factory.PasswordEncoderFactories.createDelegatingPasswordEncoder;

public interface NifflerUsersDAO {
    PasswordEncoder encoder = createDelegatingPasswordEncoder();
    int createUser(UserEntity user);

    UserEntity getUser(UserEntity userEntity);


    int updateUser(UserEntity user);

    int removeUser(UserEntity user) throws SQLException;

    String getUserId(String username);



}
