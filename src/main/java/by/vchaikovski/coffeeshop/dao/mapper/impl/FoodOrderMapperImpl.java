package by.vchaikovski.coffeeshop.dao.mapper.impl;

import by.vchaikovski.coffeeshop.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.entity.*;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static by.vchaikovski.coffeeshop.dao.ColumnTable.*;

public class FoodOrderMapperImpl implements BaseMapper<FoodOrder> {
    @Override
    public FoodOrder createEntity(ResultSet resultSet) throws DaoException {
        FoodOrder order;
        try {
            long id = resultSet.getLong(ORDER_ID);
            FoodOrder.OrderStatus status = FoodOrder.OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase());
            LocalDateTime creationDate = LocalDateTime.parse(resultSet.getDate(ORDER_CREATION_DATE).toString());
            String comment = resultSet.getString(ORDER_COMMENT);
            FoodOrder.OrderEvaluation evaluation = FoodOrder.OrderEvaluation
                    .valueOf(resultSet.getString(ORDER_EVALUATION).toUpperCase());
            UserMapperImpl userMapper = new UserMapperImpl();
            BillMapperImpl billMapper = new BillMapperImpl();
            DeliveryMapperImpl deliveryMapper = new DeliveryMapperImpl();
            OrderCartMapperImpl cartMapper = new OrderCartMapperImpl();
            User user = userMapper.createEntity(resultSet);
            Bill bill = billMapper.createEntity(resultSet);
            Delivery delivery = deliveryMapper.createEntity(resultSet);
            OrderCart cart = cartMapper.createEntity(resultSet);
            FoodOrder.FoodOrderBuilder orderBuilder = new FoodOrder.FoodOrderBuilder();
            order = orderBuilder.setId(id)
                    .setStatus(status)
                    .setCreationDate(creationDate)
                    .setComment(comment)
                    .setUser(user)
                    .setBill(bill)
                    .setDelivery(delivery)
                    .setEvaluation(evaluation)
                    .setCart(cart)
                    .build();
        } catch (SQLException e) {
            String message = "Bill can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return order;
    }
}