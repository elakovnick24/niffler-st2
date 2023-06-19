package niffler.db.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import niffler.db.ServiceDB;
import niffler.db.entity.UserEntity;
import niffler.db.jpa.EmfProvider;
import niffler.db.jpa.JpaTransactionManager;

import java.sql.SQLException;
import java.util.UUID;

public class NifflerUsersDAOHibernate extends JpaTransactionManager implements NifflerUsersDAO {

    public NifflerUsersDAOHibernate() {
        super(EmfProvider.INSTANCE.getEmf(ServiceDB.NIFFLER_AUTH).createEntityManager());
    }

    @Override
    public int createUser(UserEntity user) {
        user.setPassword(encoder.encode(user.getPassword()));
        persist(user);
        return 0;
    }

    @Override
    public String getUserId(String username) {
        return em.createQuery("select u from UserEntity u where username=:username", UserEntity.class)
                .setParameter("username", username)
                .getSingleResult()
                .getId()
                .toString();
    }

    @Override
    public int removeUser(UserEntity user) throws SQLException {
        remove(user);
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


}
