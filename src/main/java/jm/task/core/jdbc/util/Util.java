package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static SessionFactory sessionFactory;
    private final static String HOST_NAME = "localhost";
    private final static String DB_NAME = "mysql";
    private final static String USER_NAME = "root";
    private final static String PASSWORD = "root";
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final static String CONNECTION_URL = String.format("jdbc:mysql://%s:3306/%s", HOST_NAME, DB_NAME);

    public static Connection getConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(CONNECTION_URL, USER_NAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            var settings = new Properties();
            settings.put(Environment.DRIVER, DRIVER);
            settings.put(Environment.URL, CONNECTION_URL);
            settings.put(Environment.USER, USER_NAME);
            settings.put(Environment.PASS, PASSWORD);
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");

            var configuration = new Configuration().setProperties(settings).addAnnotatedClass(User.class);
            var serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            try {
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }

        return sessionFactory;
    }
}