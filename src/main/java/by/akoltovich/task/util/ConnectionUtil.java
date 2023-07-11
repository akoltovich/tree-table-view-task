package by.akoltovich.task.util;

import by.akoltovich.task.connectionPool.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionUtil {

    public static void customClose(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
        try {
            preparedStatement.close();
            resultSet.close();
            DataSource.returnConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void customClose(Connection connection, PreparedStatement preparedStatement) {
        try {
            preparedStatement.close();
            DataSource.returnConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
