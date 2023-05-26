package niffler.db;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static niffler.config.Config.getConfig;

public enum DataSourceProvider {

    // Реализация Singleton через Enum
    INSTANCE;

    private final Map<ServiceDB, DataSource> dataSources = new ConcurrentHashMap<>();

    public DataSource getDataSource(ServiceDB service) {
        return dataSources.computeIfAbsent(service, serviceDB -> {
            String jdbcurl = serviceDB.getJdbcurl();
            String loginDB = getConfig().getDBLogin();
            String passwordDB = getConfig().getDBPassword();
            PGSimpleDataSource sds = new PGSimpleDataSource();

            sds.setURL(jdbcurl);
            sds.setUser(loginDB);
            sds.setPassword(passwordDB);
            return sds;
        });
    }
}
