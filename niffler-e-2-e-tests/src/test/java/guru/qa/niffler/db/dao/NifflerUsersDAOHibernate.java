package guru.qa.niffler.db.dao;

import guru.qa.niffler.db.entity.UserEntity;
import guru.qa.niffler.db.jpa.EmfProvider;
import guru.qa.niffler.db.jpa.JpaTransactionManager;
import guru.qa.niffler.db.ServiceDB;

import java.sql.SQLException;

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
    public UserEntity getUser(String username) {
        return null;
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
    public UserEntity getUser(UserEntity userEntity) {
        return em.createQuery("select u from UserEntity u where id=:id", UserEntity.class)
                .setParameter("id", userEntity.getId())
                .getSingleResult();
    }

    @Override
    public int updateUser(UserEntity user) {
        merge(user);
        return 0;
    }


}
