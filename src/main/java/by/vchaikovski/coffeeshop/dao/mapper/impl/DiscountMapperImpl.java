package by.vchaikovski.coffeeshop.dao.mapper.impl;

import by.vchaikovski.coffeeshop.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeeshop.dao.ColumnTable.*;

public class DiscountMapperImpl implements BaseMapper<Discount> {

    public Discount createEntity(ResultSet resultSet) throws DaoException {
        Discount discount;
        try {
            long id = resultSet.getLong(DISCOUNT_ID);
            Discount.DiscountType type = Discount.DiscountType.valueOf(resultSet.getString(DISCOUNT_TYPE).toUpperCase());
            int rate = resultSet.getInt(DISCOUNT_RATE);
            Discount.DiscountBuilder builder = new Discount.DiscountBuilder();
            discount = builder.setId(id)
                    .setType(type)
                    .setRate(rate)
                    .build();
        } catch (SQLException e) {
            String message = "Discount can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return discount;
    }
}