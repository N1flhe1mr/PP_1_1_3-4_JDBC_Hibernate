package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS USERS (" +
            "ID BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(25) NOT NULL, " +
            "lastName VARCHAR(25) NOT NULL, " +
            "age TINYINT)";

    private static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS USERS";
    private static final String SAVE_USER = "INSERT INTO USERS (NAME, LASTNAME, AGE) VALUES (?,?,?)";
    private static final String REMOVE_USER_BY_ID = "DELETE FROM USERS WHERE ID=?";
    private static final String GET_ALL_USERS = "SELECT * FROM USERS";
    private static final String CLEAN_USERS_TABLE = "DELETE FROM USERS";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_USERS_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_USERS_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу данных.\n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ALL_USERS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("ID"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastName"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_USERS_TABLE)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        }
    }
}