package by.vchaikovski.coffeeshop.pool;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static final ReentrantLock singletonLock = new ReentrantLock();
    private static final Properties properties = new Properties();
    private static final String DATABASE_PROPERTIES = "database.properties";
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
        try (InputStream inputStream = Files.newInputStream(Path.of(DATABASE_PROPERTIES))) {
            properties.load(inputStream);
            databaseUrl = properties.getProperty(PROPERTY_URL);
            databaseUser = properties.getProperty(PROPERTY_USER);
            databasePassword = properties.getProperty(PROPERTY_PASSWORD);
            databaseDriver = properties.getProperty(PROPERTY_DRIVER);
            Class.forName(databaseDriver);
        } catch (IOException e) {
            logger.fatal(() -> "The file " + DATABASE_PROPERTIES + " was not found.", e);
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            logger.fatal(() -> "Driver " + databaseDriver + " was not found.", e);
            throw new RuntimeException();
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
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}