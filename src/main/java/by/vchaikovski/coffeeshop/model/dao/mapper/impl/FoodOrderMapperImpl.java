package by.vchaikovski.coffeeshop.model.dao.mapper.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeeshop.model.dao.ColumnTable.*;

public class FoodOrderMapperImpl implements BaseMapper<FoodOrder> {
    private static final FoodOrderMapperImpl instance = new FoodOrderMapperImpl();

    private FoodOrderMapperImpl() {
    }

    public static FoodOrderMapperImpl getInstance() {
        return instance;
    }

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
            long userId = resultSet.getLong(USER_ID);
            long billId = resultSet.getLong(BILL_ID);
            long deliveryId = resultSet.getLong(DELIVERY_ID);
            Map<Menu, Integer> cart = createCart(resultSet);
            FoodOrder.FoodOrderBuilder orderBuilder = new FoodOrder.FoodOrderBuilder();
            order = orderBuilder.setId(id)
                    .setStatus(status)
                    .setCreationDate(creationDate)
                    .setComment(comment)
                    .setUserId(userId)
                    .setBillId(billId)
                    .setDeliveryId(deliveryId)
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

    private Map<Menu, Integer> createCart(ResultSet resultSet) throws SQLException, DaoException {
        Map<Menu, Integer> cart = new HashMap<>();
        while (!resultSet.isAfterLast()) {
            Menu menu = MapperProvider.getInstance().getMenuMapper().createEntity(resultSet);
            int quantity = resultSet.getInt(CART_QUANTITY);
            cart.put(menu, quantity);
        }
        return cart;
    }
}