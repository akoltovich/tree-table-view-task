package by.akoltovich.task.connectionPool;

public class Configuration {
    public String login;

    public String password;

    public String url;

    public Integer maxConnections;

    public Configuration() {
        init();
    }

    private static Configuration configuration;

    public static Configuration getInstance() {
        if (configuration == null) {
            configuration = new Configuration();
            return configuration;
        }
        return null;
    }

    private void init() {
        login = "root";
        password = "root";
        url = "jdbc:mysql://localhost:3306/treetableview?serverTimezone=UTC";
        maxConnections = 20;
    }

}
