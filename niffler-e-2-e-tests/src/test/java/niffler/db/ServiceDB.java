package niffler.db;

import niffler.config.Config;

import static niffler.config.Config.getConfig;

public enum ServiceDB {


    NIFFLER_AUTH("jdbc:postgresql://%s:%d/niffler-auth"),
    NIFFLER_SPEND("jdbc:postgresql://%s:%d/niffler-spend"),
    NIFFLER_USERDATA("jdbc:postgresql://%s:%d/niffler-userdata"),
    NIFFLER_CURRENCY("jdbc:postgresql://%s:%d/niffler-currency");

    private final String jdbcurl;

    ServiceDB(String jdbcurl) {
        this.jdbcurl = jdbcurl;
    }

    public String getJdbcurl() {
        return String.format(jdbcurl,
                getConfig().getDBHost(),
                getConfig().getDBPort());
    }
}
