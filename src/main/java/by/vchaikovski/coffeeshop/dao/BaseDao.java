package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.AbstractEntity;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends AbstractEntity> {
    Logger logger = LogManager.getLogger();
    int FIRST_PARAMETER_INDEX = 1;
    int SECOND_PARAMETER_INDEX = 2;
    int THIRD_PARAMETER_INDEX = 3;
    int FOURTH_PARAMETER_INDEX = 4;
    int FIFTH_PARAMETER_INDEX = 5;
    int SIXTH_PARAMETER_INDEX = 6;
    int SEVENTH_PARAMETER_INDEX = 7;
    int EIGHTH_PARAMETER_INDEX = 8;
    int NINTH_PARAMETER_INDEX = 9;
    int TENTH_PARAMETER_INDEX = 10;
    int ELEVENTH_PARAMETER_INDEX = 11;
    int TWELFTH_PARAMETER_INDEX = 12;

    List<T> findAll() throws DaoException, ConnectionPoolException;

    Optional<T> findById(long id) throws ConnectionPoolException, DaoException;

    long create(T entity) throws ConnectionPoolException, DaoException;

    boolean update(long id, T entity) throws ConnectionPoolException, DaoException;

    boolean deleteById(long id) throws ConnectionPoolException, DaoException;

    default void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                logger.error(() -> "The statement " + statement + " can't be closed.", e);
            }
        }
    }

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