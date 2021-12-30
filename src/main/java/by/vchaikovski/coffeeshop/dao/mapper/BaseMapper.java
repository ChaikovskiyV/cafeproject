package by.vchaikovski.coffeeshop.dao.mapper;

import by.vchaikovski.coffeeshop.entity.AbstractEntity;
import by.vchaikovski.coffeeshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;

public interface BaseMapper<T extends AbstractEntity> {
    Logger logger = LogManager.getLogger();

    T createEntity(ResultSet resultSet) throws DaoException;
}