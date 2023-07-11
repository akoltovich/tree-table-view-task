package by.akoltovich.task.DAO.impl;

import by.akoltovich.task.DAO.HumanDAO;
import by.akoltovich.task.connectionPool.DataSource;
import by.akoltovich.task.entity.Human;
import by.akoltovich.task.util.ConnectionUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HumanDAOImpl implements HumanDAO {

    private static final String SQL_FIND_BY_ID = "select * from human where id=?";
    private static final String SQL_FIND_ALL_HUMANS = "select * from human";
    private static final String SQL_CREATE_HUMAN = "insert into human (id,name,age,birthdate) values (?,?,?,?)";
    private static final String SQL_DELETE_HUMAN = "delete from human where id=?";
    private static final String SQL_UPDATE_HUMAN = "update human set name=?,age=?,birthdate=? where id=?";
    private static HumanDAOImpl instance;

    public static HumanDAOImpl getInstance() {
        if (instance == null) {
            instance = new HumanDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<Human> getHuman(Long id) {
        Connection connection = DataSource.getConnection();
        Human human = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                human = getHumanFromResultSet(resultSet);
            }
            preparedStatement.executeUpdate();
            ConnectionUtil.customClose(connection, preparedStatement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(human);
    }

    @Override
    public ObservableList<Human> getAllHumans() {
        List<Human> humans = new ArrayList<>();
        Human human;
        Connection connection = DataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_HUMANS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                human = getHumanFromResultSet(resultSet);
                humans.add(human);
            }
            ConnectionUtil.customClose(connection, preparedStatement, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(humans);
    }

    @Override
    public void saveHuman(Human human) {
        Connection connection = DataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_HUMAN);
            preparedStatement.setLong(1, human.getId());
            preparedStatement.setString(2, human.getName());
            preparedStatement.setInt(3, human.getAge());
            preparedStatement.setDate(4, human.getBirthDate());
            preparedStatement.executeUpdate();
            ConnectionUtil.customClose(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateHuman(Long id, Human human) {
        Connection connection = DataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_HUMAN);
            preparedStatement.setString(1, human.getName());
            preparedStatement.setInt(2, human.getAge());
            preparedStatement.setDate(3, human.getBirthDate());
            preparedStatement.setLong(4, id);
            preparedStatement.executeUpdate();
            ConnectionUtil.customClose(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteHuman(Long id) {
        Connection connection = DataSource.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_HUMAN);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.executeUpdate();
            ConnectionUtil.customClose(connection, preparedStatement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Human getHumanFromResultSet(ResultSet resultSet) {
        Human human = new Human();
        try {
            human.setId(resultSet.getLong("id"));
            human.setName(resultSet.getString("name"));
            human.setAge(resultSet.getInt("age"));
            human.setBirthDate(resultSet.getDate("birthdate"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return human;
    }
}
