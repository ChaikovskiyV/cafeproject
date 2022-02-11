package by.vchaikovski.coffeehouse.model.dao.mapper;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

/**
 * @param <T> the type parameter
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Base mapper.
 */
public interface BaseMapper<T extends AbstractEntity> {
    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger();

    /**
     * Create entity t.
     *
     * @param resultSet the result set
     * @return the t
     * @throws DaoException the dao exception
     */
    T createEntity(ResultSet resultSet) throws DaoException;
}