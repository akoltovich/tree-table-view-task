package by.akoltovich.task.connectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    Configuration configuration = Configuration.getInstance();
    BlockingQueue<Connection> availableConnections = new LinkedBlockingQueue<>(configuration.maxConnections);

    public ConnectionPool() {
        initConnectionPool();
    }

    private void initConnectionPool() {
        while (!isConnectionPoolIsFull()) {
            availableConnections.add(Objects.requireNonNull(createNewConnectionForPool()));
        }
    }

    private boolean isConnectionPoolIsFull() {
        final int maxPoolSize = configuration.maxConnections;
        return availableConnections.size() >= maxPoolSize;
    }

    private Connection createNewConnectionForPool() {
        try {
            return DriverManager.getConnection(configuration.url, configuration.login, configuration.password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection getConnectionFromPool() {
        Connection connection = null;
        if (availableConnections.size() > 0) {
            try {
                connection = availableConnections.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            availableConnections.remove(connection);
        }
        return connection;
    }

    public void returnConnectionToPool(Connection connection) {
        availableConnections.add(connection);
    }
}
