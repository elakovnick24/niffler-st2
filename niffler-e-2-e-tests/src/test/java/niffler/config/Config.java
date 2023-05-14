package niffler.config;

public interface Config {

    static Config getConfig() {
        if ("Docker".equals(System.getProperty("env"))) {
            return new DockerConfig();
        }
        return new LocalConfig();
    }
    String getDBHost();
    int getDBPort();

    String getDBLogin();
    String getDBPassword();

}
