package by.vchaikovski.coffeehouse.model.dao.mapper.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeehouse.model.entity.Bill;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.vchaikovski.coffeehouse.model.dao.ColumnTable.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Bill mapper.
 */

public class BillMapperImpl implements BaseMapper<Bill> {
    private static final BillMapperImpl instance = new BillMapperImpl();

    private BillMapperImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BillMapperImpl getInstance() {
        return instance;
    }

    public Bill createEntity(ResultSet resultSet) throws DaoException {
        Bill bill;
        try {
            long id = resultSet.getLong(BILL_ID);
            BigDecimal totalPrice = resultSet.getBigDecimal(BILL_TOTAL_PRICE);
            Bill.BillStatus status = Bill.BillStatus.valueOf(resultSet.getString(BILL_STATUS).toUpperCase());
            Date date = resultSet.getDate(BILL_PAYMENT_DATE);
            LocalDate paymentDate = date != null ? LocalDate.parse(date.toString()) : null;
            bill = new Bill(status, paymentDate, totalPrice);
            bill.setId(id);
        } catch (SQLException e) {
            String message = "Bill can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bill;
    }
}