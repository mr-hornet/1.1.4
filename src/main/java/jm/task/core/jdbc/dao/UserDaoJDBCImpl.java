package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        // Создать таблицу
        String createTable = "create table if not exists users \n " +
                "(\n" +
                "  id int NOT NULL AUTO_INCREMENT,\n" +
                "  name varchar(45) DEFAULT NULL,\n" +
                "  lastName varchar(45) DEFAULT NULL,\n" +
                "  age int DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ");";
        try (PreparedStatement statement = connection.prepareStatement(createTable)) {
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Успешное создание таблицы");
    }

    public void dropUsersTable() {
        // Удаление таблицы
        String dropUsers = "drop table if exists users";
        try (PreparedStatement statement = connection.prepareStatement(dropUsers)) {
            connection.setAutoCommit(false);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Успешное удаление таблицы");
    }

    public void saveUser(String name, String lastName, byte age) {
        // Сохранить пользователя
        String saveUser = "insert into users (name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(saveUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Пользователь с именем " + name + " добавлен в БД");
    }

    public void removeUserById(long id) {
        // Удаление пользователя по ID
        String removeUser = "delete from users where id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(removeUser)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Пользователь с ID " + id + " успешно удалён");
    }

    public List<User> getAllUsers() {
        // Получение всех пользователей
        List<User> list = new ArrayList<>();
        String getAll = "select * from users";
        try (PreparedStatement statement = connection.prepareStatement(getAll)) {
            connection.setAutoCommit(true);
            ResultSet resultSet = statement.executeQuery(getAll);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        // Очистить таблицу
        String cleanUsers = "delete from users";
        try (PreparedStatement statement = connection.prepareStatement(cleanUsers)) {
            connection.setAutoCommit(false);
            statement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Таблица очищена");
    }
}
