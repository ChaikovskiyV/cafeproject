package by.vchaikovski.coffeeshop.pool;

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
    private BlockingQueue<ProxyConnection> freeConnections;
    private BlockingQueue<ProxyConnection> givenConnection;

    private ConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        givenConnection = new LinkedBlockingQueue<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            freeConnections.offer(proxyConnection);
        }
        if(freeConnections.isEmpty()) {
            logger.fatal("No connections were created");
            throw new RuntimeException("No connections were created");
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

    public ProxyConnection getConnection() throws ConnectionPoolException {
        ProxyConnection connection;
        try {
            connection = freeConnections.take();
            givenConnection.offer(connection);
        } catch (InterruptedException e) {
            logger.error("Exception from getConnection", e);
            throw new ConnectionPoolException("Exception from getConnection", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionPoolException {
        if (connection instanceof ProxyConnection) {
            givenConnection.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            logger.error(() -> "Unknown connection: " + connection);
            throw new ConnectionPoolException("Unknown connection: " + connection);
        }
    }

    public void destroyPool() throws ConnectionPoolException {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Exception from destroyPool method", e);
                throw new ConnectionPoolException("Exception from destroyPool", e);
            } catch (SQLException e) {
                logger.error("Exception from destroyPool method", e);
                throw new ConnectionPoolException("Exception from destroyPool", e);
            }
        }
        deregisterDrivers();
    }

    public boolean restoreConnectionsNumber() {  //TODO check condition when connection can't be created
        boolean isRestored = false;
        int connectionsNumber = freeConnections.size() + givenConnection.size();
        if(connectionsNumber < POOL_SIZE && connectionsNumber > 0) {
            while (connectionsNumber < POOL_SIZE) {
                Connection connection = ConnectionFactory.getInstance().getConnection();
                ProxyConnection proxyConnection = new ProxyConnection(connection);
                freeConnections.offer(proxyConnection);
                connectionsNumber = freeConnections.size() + givenConnection.size();
            }
            isRestored = true;
        }
        return isRestored;
    }

    private void deregisterDrivers() {
        DriverManager.getDrivers().asIterator().forEachRemaining(d -> {
            try {
                DriverManager.deregisterDriver(d);
            } catch (SQLException e) {
                logger.error("Exception from destroyedDrivers", e);
            }
        });
    }
}