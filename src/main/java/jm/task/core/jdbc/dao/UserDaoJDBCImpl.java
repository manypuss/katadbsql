package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    public void createUsersTable() {
        // Команда создания таблицы
        String createUTSQL = "CREATE TABLE IF NOT EXISTS users (Id BIGINT PRIMARY KEY AUTO_INCREMENT, Name VARCHAR(45), LastName VARCHAR(45),Age INT)";
        try {
            try (Statement statement = connection.createStatement()) {
                // Создание таблицы
                statement.executeUpdate(createUTSQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropUsersTable() {
        // Команда удаления таблицы
        String dropUTSQL = "DROP TABLE IF EXISTS users";
        try {
            try (Statement statement = connection.createStatement()) {
                // Удаление таблицы
                statement.executeUpdate(dropUTSQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        // Команда добавления пользователя
        String saveUserSQL = "INSERT INTO users (Name, LastName, Age) VALUES (?, ?, ?)";
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(saveUserSQL)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setInt(3, age);

                preparedStatement.executeUpdate();
                connection.commit();
                System.out.println("User с именем - " + name + " добавлен в базу данных");
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                throw new RuntimeException(e);
            }
        }

    }

    public void removeUserById(long id) {
        // Команда удаления пользователя из таблицы
        String remUsByIdSQL = "DELETE FROM users WHERE ID = ?";
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(remUsByIdSQL)) {
                // Удаление пользователя из таблицы по ID
                preparedStatement.setLong(1, id);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        // Команда полуения всех пользователей из таблицы
        String getAllUsersSQL = "SELECT * FROM users";
        try {
            try (Statement statement = connection.createStatement()) {
                // Получаем пользователей
                ResultSet resultSet = statement.executeQuery(getAllUsersSQL);

                while (resultSet.next()) {
                    User user = new User();
                    user.setId(resultSet.getLong("Id"));
                    user.setName(resultSet.getString("Name"));
                    user.setLastName(resultSet.getString("LastName"));
                    user.setAge(resultSet.getByte("Age"));

                    users.add(user);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        // Команда очистки таблицы
        String cleanUTSQL = "DELETE FROM users";
        try {
            try (Statement statement = connection.createStatement()) {
                // Очистка таблицы
                statement.executeUpdate(cleanUTSQL);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
