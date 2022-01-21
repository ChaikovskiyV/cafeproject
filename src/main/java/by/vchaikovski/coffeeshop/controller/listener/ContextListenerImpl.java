package by.vchaikovski.coffeeshop.controller.listener;

import by.vchaikovski.coffeeshop.model.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContextListenerImpl implements ServletContextListener {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);
        logger.info("Context was created.");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
        logger.info("ConnectionPool was destroyed.");
    }
}