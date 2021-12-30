package by.vchaikovski.coffeeshop.dao.mapper.impl;

import by.vchaikovski.coffeeshop.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.entity.Menu;
import by.vchaikovski.coffeeshop.entity.OrderCart;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeeshop.dao.ColumnTable.CART_QUANTITY;

public class OrderCartMapperImpl implements BaseMapper<OrderCart> {
    @Override
    public OrderCart createEntity(ResultSet resultSet) throws DaoException {
        OrderCart orderCart = new OrderCart();
        try {
            while (!resultSet.isAfterLast()) {
                Menu menu = new MenuMapperImpl().createEntity(resultSet);
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