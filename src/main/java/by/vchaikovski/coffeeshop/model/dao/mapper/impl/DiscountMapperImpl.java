package by.vchaikovski.coffeeshop.model.dao.mapper.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.model.entity.Discount;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeeshop.model.dao.ColumnTable.*;

public class DiscountMapperImpl implements BaseMapper<Discount> {
    private static final DiscountMapperImpl instance = new DiscountMapperImpl();

    private DiscountMapperImpl() {
    }

    public static DiscountMapperImpl getInstance() {
        return instance;
    }

    public Discount createEntity(ResultSet resultSet) throws DaoException {
        Discount discount;
        try {
            long id = resultSet.getLong(DISCOUNT_ID);
            Discount.DiscountType type = Discount.DiscountType.valueOf(resultSet.getString(DISCOUNT_TYPE).toUpperCase());
            int rate = resultSet.getInt(DISCOUNT_RATE);
            discount = new Discount(type, rate);
            discount.setId(id);
        } catch (SQLException e) {
            String message = "Discount can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return discount;
    }
}