package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    public static Connection getConnection() {
        final String hostName = "localhost";
        final String dbName = "mysql";
        final String userName = "root";
        final String password = "root";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String connectionURL = String.format("jdbc:mysql://%s:3306/%s", hostName, dbName);
            return DriverManager.getConnection(connectionURL, userName, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}