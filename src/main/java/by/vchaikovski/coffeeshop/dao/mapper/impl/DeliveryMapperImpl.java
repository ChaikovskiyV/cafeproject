package by.vchaikovski.coffeeshop.dao.mapper.impl;

import by.vchaikovski.coffeeshop.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.entity.Delivery;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static by.vchaikovski.coffeeshop.dao.ColumnTable.*;

public class DeliveryMapperImpl implements BaseMapper<Delivery> {
    @Override
    public Delivery createEntity(ResultSet resultSet) throws DaoException {
        Delivery delivery;
        try {
            long id = resultSet.getLong(DELIVERY_ID);
            Delivery.DeliveryType type = Delivery.DeliveryType.valueOf(resultSet.getString(DELIVERY_TYPE).toUpperCase());
            LocalDateTime deliveryTime = LocalDateTime.parse(resultSet.getString(DELIVERY_TIME));
            AddressDeliveryMapperImpl addressDeliveryMapper = new AddressDeliveryMapperImpl();
            AddressDelivery address = addressDeliveryMapper.createEntity(resultSet);
            Delivery.DeliveryBuilder deliveryBuilder = new Delivery.DeliveryBuilder();
            delivery = deliveryBuilder.setId(id)
                    .setDeliveryType(type)
                    .setDeliveryTime(deliveryTime)
                    .setAddress(address)
                    .build();
        } catch (SQLException e) {
            String message = "Delivery can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return delivery;
    }
}
