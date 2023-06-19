package niffler.db.jpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.metamodel.Metamodel;

import java.util.Map;

public class ThreadLocalEmf implements EntityManagerFactory {

    private final EntityManagerFactory delegate;
    private final ThreadLocal<EntityManager> threadLocalEmf;

    public ThreadLocalEmf(EntityManagerFactory delegate) {
        this.delegate = delegate;
        this.threadLocalEmf = ThreadLocal.withInitial(delegate::createEntityManager);
    }

    @Override
    public EntityManager createEntityManager() {
        return null;
    }

    @Override
    public EntityManager createEntityManager(Map map) {
        threadLocalEmf.set(delegate.createEntityManager(map));
        return threadLocalEmf.get();
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType) {
        threadLocalEmf.set(delegate.createEntityManager(synchronizationType));
        return threadLocalEmf.get();
    }

    @Override
    public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
        threadLocalEmf.set(delegate.createEntityManager(synchronizationType, map));
        return threadLocalEmf.get();
    }

    @Override
    public CriteriaBuilder getCriteriaBuilder() {
        return delegate.getCriteriaBuilder();
    }

    @Override
    public Metamodel getMetamodel() {
        return delegate.getMetamodel();
    }

    @Override
    public boolean isOpen() {
        return delegate.isOpen();
    }

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public Map<String, Object> getProperties() {
        return delegate.getProperties();
    }

    @Override
    public Cache getCache() {
        return delegate.getCache();
    }

    @Override
    public PersistenceUnitUtil getPersistenceUnitUtil() {
        return delegate.getPersistenceUnitUtil();
    }

    @Override
    public void addNamedQuery(String name, Query query) {
        delegate.addNamedQuery(name, query);
    }

    @Override
    public <T> T unwrap(Class<T> cls) {
        return null;
    }

    @Override
    public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
        delegate.addNamedEntityGraph(graphName, entityGraph);
    }
}
