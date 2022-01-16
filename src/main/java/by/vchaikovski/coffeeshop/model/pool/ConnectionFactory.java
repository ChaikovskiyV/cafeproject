package by.vchaikovski.coffeeshop.model.pool;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATABASE_PROPERTIES = "database";
    private static final String PROPERTY_URL = "db.url";
    private static final String PROPERTY_USER = "db.user";
    private static final String PROPERTY_PASSWORD = "db.password";
    private static final String PROPERTY_DRIVER = "db.driver";
    private static final String DATABASE_URL;
    private static final String DATABASE_USER;
    private static final String DATABASE_PASSWORD;
    private static final String DATABASE_DRIVER;
    private static ConnectionFactory instance;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(DATABASE_PROPERTIES);
            DATABASE_URL = bundle.getString(PROPERTY_URL);
            DATABASE_USER = bundle.getString(PROPERTY_USER);
            DATABASE_PASSWORD = bundle.getString(PROPERTY_PASSWORD);
            DATABASE_DRIVER = bundle.getString(PROPERTY_DRIVER);
            Class.forName(DATABASE_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.fatal(() -> "Database driver was not found.", e);
            throw new RuntimeException("Database driver was not found.", e);
        }
    }

    private ConnectionFactory() {
    }

    static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
        }
        return instance;
    }

    Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } catch (SQLException e) {
            logger.error(() -> "Connection with " + DATABASE_URL + " was not created.", e);
        }
        return connection;
    }
}