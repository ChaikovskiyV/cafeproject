package by.vchaikovski.coffeehouse.model.dao.mapper.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeehouse.model.entity.Discount;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeehouse.model.dao.ColumnTable.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Discount mapper.
 */

public class DiscountMapperImpl implements BaseMapper<Discount> {
    private static final DiscountMapperImpl instance = new DiscountMapperImpl();

    private DiscountMapperImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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