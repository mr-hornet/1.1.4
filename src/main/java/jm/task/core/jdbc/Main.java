package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService userService = new UserServiceImpl();
        userService.cleanUsersTable();
        userService.saveUser("John", "Klein", (byte) 15);
        userService.saveUser("Mike", "Tyson", (byte) 38);
        userService.saveUser("Villi", "Vodka", (byte) 41);

        for (User user : userService.getAllUsers()) {
            System.out.println(user);
        }

    }
}
