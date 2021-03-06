package by.vchaikovski.coffeehouse.model.dao.mapper.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeehouse.model.entity.FoodOrder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.vchaikovski.coffeehouse.model.dao.ColumnTable.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Food order mapper.
 */

public class FoodOrderMapperImpl implements BaseMapper<FoodOrder> {
    private static final FoodOrderMapperImpl instance = new FoodOrderMapperImpl();

    private FoodOrderMapperImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FoodOrderMapperImpl getInstance() {
        return instance;
    }

    @Override
    public FoodOrder createEntity(ResultSet resultSet) throws DaoException {
        FoodOrder order;
        try {
            long id = resultSet.getLong(ORDER_ID);
            FoodOrder.OrderStatus status = FoodOrder.OrderStatus.valueOf(resultSet.getString(ORDER_STATUS).toUpperCase());
            LocalDate creationDate = LocalDate.parse(resultSet.getDate(ORDER_CREATION_DATE).toString());
            String comment = resultSet.getString(ORDER_COMMENT);
            FoodOrder.OrderEvaluation evaluation = FoodOrder.OrderEvaluation
                    .valueOf(resultSet.getString(ORDER_EVALUATION).toUpperCase());
            long userId = resultSet.getLong(ORDER_USER_ID);
            long billId = resultSet.getLong(ORDER_BILL_ID);
            long deliveryId = resultSet.getLong(ORDER_DELIVERY_ID);
            FoodOrder.FoodOrderBuilder orderBuilder = new FoodOrder.FoodOrderBuilder();
            order = orderBuilder.setId(id)
                    .setStatus(status)
                    .setCreationDate(creationDate)
                    .setComment(comment)
                    .setUserId(userId)
                    .setBillId(billId)
                    .setDeliveryId(deliveryId)
                    .setEvaluation(evaluation)
                    .build();
        } catch (SQLException e) {
            String message = "Order can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return order;
    }
}