package by.vchaikovski.coffeeshop.model.pool;

import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final int POOL_SIZE = 8;
    private static final ReentrantLock singletonLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static ConnectionPool instance;
    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> givenConnection;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        givenConnection = new LinkedBlockingQueue<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            freeConnections.offer(proxyConnection);
        }
        if (freeConnections.isEmpty()) {
            String message = "No connections were created";
            logger.fatal(message);
            throw new RuntimeException(message);
        }
    }

    public static ConnectionPool getInstance() {
        if (!isCreated.get()) {
            singletonLock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                singletonLock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            givenConnection.offer(connection);
        } catch (InterruptedException e) {
            String message = "The getConnection method can't be completed";
            logger.error(message, e);
            throw new ConnectionPoolException(message, e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (connection instanceof ProxyConnection) {
            givenConnection.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            String message = "Unknown connection: " + connection;
            logger.error(message);
            throw new ConnectionPoolException(message);
        }
    }

    public void destroyPool() {
        while (!freeConnections.isEmpty() || !givenConnection.isEmpty()) {
            try {
                if (!freeConnections.isEmpty()) {
                    freeConnections.poll().reallyClose();
                }
                if (!givenConnection.isEmpty()) {
                    givenConnection.poll().reallyClose();
                }
            } catch (SQLException e) {
                logger.error("The destroyPool method can't be completed", e);
            }
        }
        deregisterDrivers();
    }

    public void destroyPoolTake() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException | SQLException e) {
                logger.error("The destroyPoolTake method can't be completed", e);
            }
        }
        deregisterDrivers();
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(d -> {
            try {
                DriverManager.deregisterDriver(d);
            } catch (SQLException e) {
                logger.error("The  destroyedDrivers method can't be completed", e);
            }
        });
    }
}