package by.vchaikovski.coffeeshop.pool;

import by.vchaikovski.coffeeshop.exception.CoffeeHouseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class CoffeeHouseConnectionPool {
    private static final Logger logger = LogManager.getLogger();
    private static final int POOL_SIZE = 16;
    private static final ReentrantLock singletonLock = new ReentrantLock();
    private static final AtomicBoolean isCreated = new AtomicBoolean(false);
    private static CoffeeHouseConnectionPool instance;
    private BlockingQueue<ProxyConnection> freeConnections;
    private Queue<ProxyConnection> givenConnection;

    private CoffeeHouseConnectionPool() {
        freeConnections = new LinkedBlockingQueue<>(POOL_SIZE);
        givenConnection = new ArrayDeque<>();
        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = ConnectionFactory.getInstance().getConnection();
            ProxyConnection proxyConnection = new ProxyConnection(connection);
            freeConnections.offer(proxyConnection);
        }
    }

    public static CoffeeHouseConnectionPool getInstance() {
        if(!isCreated.get()) {
            singletonLock.lock();
            try {
                if(instance == null) {
                    instance = new CoffeeHouseConnectionPool();
                    isCreated.set(true);
                }
            } finally {
                singletonLock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() throws CoffeeHouseException {
        Connection connection = null;
        try {
            connection = freeConnections.take();
            givenConnection.offer((ProxyConnection) connection);
        } catch (InterruptedException e) {
            logger.error("Exception from getConnection", e);
            throw new CoffeeHouseException("Exception from getConnection", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws CoffeeHouseException {
        if (connection instanceof ProxyConnection) {
            givenConnection.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            logger.error(() -> "Unknown connection: " + connection);
            throw new CoffeeHouseException("Unknown connection: " + connection);
        }
    }

    public void destroyPool() throws CoffeeHouseException {
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (InterruptedException e) {
                logger.error("Exception from destroyPool", e);
                throw new CoffeeHouseException("Exception from destroyPool", e);
            }
        }
        deregisterDrivers();
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