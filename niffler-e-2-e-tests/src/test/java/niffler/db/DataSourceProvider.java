package niffler.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static niffler.config.Config.getConfig;


public enum DataSourceProvider {
    INSTANCE;

    private final Map<ServiceDB, DataSource> dataSources = new ConcurrentHashMap<>();

    public DataSource getDataSource(ServiceDB service) {
        return dataSources.computeIfAbsent(service, serviceDB -> {
            PGSimpleDataSource sds = new PGSimpleDataSource();
            sds.setURL(serviceDB.getJdbcUrl());
            sds.setUser(getConfig().getDBLogin());
            sds.setPassword(getConfig().getDBPassword());
            return sds;
        });
    }

}
