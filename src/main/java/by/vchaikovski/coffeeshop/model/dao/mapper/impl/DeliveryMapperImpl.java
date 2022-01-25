package by.vchaikovski.coffeeshop.model.dao.mapper.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.model.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static by.vchaikovski.coffeeshop.model.dao.ColumnTable.*;

public class DeliveryMapperImpl implements BaseMapper<Delivery> {
    private static final DeliveryMapperImpl instance = new DeliveryMapperImpl();

    private DeliveryMapperImpl() {
    }

    public static DeliveryMapperImpl getInstance() {
        return instance;
    }

    @Override
    public Delivery createEntity(ResultSet resultSet) throws DaoException {
        Delivery delivery;
        try {
            long id = resultSet.getLong(DELIVERY_ID);
            Delivery.DeliveryType type = Delivery.DeliveryType.valueOf(resultSet.getString(DELIVERY_TYPE).toUpperCase());
            LocalDateTime deliveryTime = LocalDateTime.parse(resultSet.getTimestamp(DELIVERY_TIME).toString());
            long addressId = resultSet.getLong(ADDRESS_ID);
            delivery = new Delivery(type, deliveryTime, addressId);
            delivery.setId(id);
        } catch (SQLException e) {
            String message = "Delivery can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return delivery;
    }
}