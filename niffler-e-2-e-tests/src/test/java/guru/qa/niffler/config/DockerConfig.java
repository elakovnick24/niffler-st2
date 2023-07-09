package guru.qa.niffler.config;

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

    @Override
    public String getSpendUrl() {
        return "niffler-spend";
    }

    @Override
    public String getAuthUrl() {
        return "http://niffler-frontend:3000/";
    }

    @Override
    public String getFrontUrl() {
        return "http://niffler-auth:9001/";
    }

    @Override
    public String getUserUrl() {
        return "http://niffler-userdata:8090";
    }
}
