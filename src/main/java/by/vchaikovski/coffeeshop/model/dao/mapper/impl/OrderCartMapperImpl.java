package by.vchaikovski.coffeeshop.model.dao.mapper.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.model.entity.OrderCart;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeeshop.model.dao.ColumnTable.CART_QUANTITY;

public class OrderCartMapperImpl implements BaseMapper<OrderCart> {
    private static final OrderCartMapperImpl instance = new OrderCartMapperImpl();

    private OrderCartMapperImpl() {
    }

    public static OrderCartMapperImpl getInstance() {
        return instance;
    }

    @Override
    public OrderCart createEntity(ResultSet resultSet) throws DaoException {
        OrderCart orderCart = new OrderCart();
        try {
            while (!resultSet.isAfterLast()) {
                Menu menu = MapperProvider.getInstance().getMenuMapper().createEntity(resultSet);
                int quantity = resultSet.getInt(CART_QUANTITY);
                orderCart.addProduct(menu, quantity);
            }
        } catch (SQLException e) {
            String message = "OrderCart can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orderCart;
    }
}