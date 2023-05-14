package niffler.config;

public class DockerConfig implements Config{

    @Override
    public String getDBHost() {
        return "niffler-all-db";
    }

    @Override
    public int getDBPort() {
        return 5432;
    }

    @Override
    public String getDBLogin() {
        return "postgres";
    }

    @Override
    public String getDBPassword() {
        return "secret";
    }
}
