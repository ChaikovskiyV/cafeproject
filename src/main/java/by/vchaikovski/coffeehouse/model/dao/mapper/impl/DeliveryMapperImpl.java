package by.vchaikovski.coffeehouse.model.dao.mapper.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeehouse.model.entity.Delivery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.vchaikovski.coffeehouse.model.dao.ColumnTable.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Delivery mapper.
 */

public class DeliveryMapperImpl implements BaseMapper<Delivery> {
    private static final DeliveryMapperImpl instance = new DeliveryMapperImpl();

    private DeliveryMapperImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DeliveryMapperImpl getInstance() {
        return instance;
    }

    @Override
    public Delivery createEntity(ResultSet resultSet) throws DaoException {
        Delivery delivery;
        try {
            long id = resultSet.getLong(DELIVERY_ID);
            Delivery.DeliveryType type = Delivery.DeliveryType.valueOf(resultSet.getString(DELIVERY_TYPE).toUpperCase());
            LocalDate deliveryDate = LocalDate.parse(resultSet.getDate(DELIVERY_TIME).toString());
            long addressId = resultSet.getLong(ADDRESS_ID);
            delivery = new Delivery(type, deliveryDate, addressId);
            delivery.setId(id);
        } catch (SQLException e) {
            String message = "Delivery can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return delivery;
    }
}