package jm.task.core.jdbc.util;

import java.sql.*;

public class Util {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    private static final String HOST = "jdbc:mysql://localhost:3306/katadb";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection =  DriverManager.getConnection(HOST, USERNAME, PASSWORD);
            System.out.println("Соединение с БД установлено!");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Соединение с БД не установлено!");
        }
        return connection;
    }
}


