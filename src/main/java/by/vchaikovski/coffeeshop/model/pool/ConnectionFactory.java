package by.vchaikovski.coffeeshop.model.pool;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final ReentrantLock singletonLock = new ReentrantLock();
    private static final Properties properties = new Properties();
    private static final String DATABASE_PROPERTIES = "database";
    private static final String PROPERTY_URL = "db.url";
    private static final String PROPERTY_USER = "db.user";
    private static final String PROPERTY_PASSWORD = "db.password";
    private static final String PROPERTY_DRIVER = "db.driver";
    private static String databaseUrl;
    private static String databaseUser;
    private static String databasePassword;
    private static String databaseDriver;
    private static ConnectionFactory instance;

    static {
        try {
            ResourceBundle bundle = ResourceBundle.getBundle(DATABASE_PROPERTIES);
            databaseUrl = bundle.getString(PROPERTY_URL);
            databaseUser = bundle.getString(PROPERTY_USER);
            databasePassword = bundle.getString(PROPERTY_PASSWORD);
            databaseDriver = bundle.getString(PROPERTY_DRIVER);
            Class.forName(databaseDriver);
        } catch (ClassNotFoundException e) {
            logger.fatal(() -> "Driver " + databaseDriver + " was not found.", e);
            throw new RuntimeException("Driver " + databaseDriver + " was not found.", e);
        }
    }

    private ConnectionFactory() {
    }

    static ConnectionFactory getInstance() {
        if (!isCreated.get()) {
            singletonLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionFactory();
                    isCreated.set(true);
                }
            } finally {
                singletonLock.unlock();
            }
        }
        return instance;
    }

    Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        } catch (SQLException e) {
            logger.error(() -> "Connection with " + databaseUrl + " was not created.", e);
            throw new RuntimeException("Connection with " + databaseUrl + " was not created.", e);
        }
        return connection;
    }
}