package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.AbstractEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * @param <T> the type parameter
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Base dao.
 */
public interface BaseDao<T extends AbstractEntity> {
    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger();
    /**
     * The constant FAILED_MESSAGE.
     */
    String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    /**
     * The constant FIRST_PARAMETER_INDEX.
     */
    int FIRST_PARAMETER_INDEX = 1;
    /**
     * The constant SECOND_PARAMETER_INDEX.
     */
    int SECOND_PARAMETER_INDEX = 2;
    /**
     * The constant THIRD_PARAMETER_INDEX.
     */
    int THIRD_PARAMETER_INDEX = 3;
    /**
     * The constant FOURTH_PARAMETER_INDEX.
     */
    int FOURTH_PARAMETER_INDEX = 4;
    /**
     * The constant FIFTH_PARAMETER_INDEX.
     */
    int FIFTH_PARAMETER_INDEX = 5;
    /**
     * The constant SIXTH_PARAMETER_INDEX.
     */
    int SIXTH_PARAMETER_INDEX = 6;
    /**
     * The constant SEVENTH_PARAMETER_INDEX.
     */
    int SEVENTH_PARAMETER_INDEX = 7;
    /**
     * The constant EIGHTH_PARAMETER_INDEX.
     */
    int EIGHTH_PARAMETER_INDEX = 8;
    /**
     * The constant NINTH_PARAMETER_INDEX.
     */
    int NINTH_PARAMETER_INDEX = 9;

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<T> findAll() throws DaoException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<T> findById(long id) throws DaoException;

    /**
     * Create long.
     *
     * @param entity the entity
     * @return the long
     * @throws DaoException the dao exception
     */
    long create(T entity) throws DaoException;

    /**
     * Update boolean.
     *
     * @param id     the id
     * @param entity the entity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(long id, T entity) throws DaoException;

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean deleteById(long id) throws DaoException;

    /**
     * Close.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(() -> "The statement " + statement + " can't be closed.", e);
            }
        }
    }

    /**
     * Close.
     *
     * @param resultSet the result set
     */
    default void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                logger.error(() -> "The resultSet " + resultSet + " can't be closed.", e);
            }
        }
    }
}