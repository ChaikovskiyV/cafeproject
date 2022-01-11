package by.vchaikovski.coffeeshop.model.dao.mapper.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.model.entity.Bill;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static by.vchaikovski.coffeeshop.model.dao.ColumnTable.*;

public class BillMapperImpl implements BaseMapper<Bill> {
    private static final BillMapperImpl instance = new BillMapperImpl();

    private BillMapperImpl() {
    }

    public static BillMapperImpl getInstance() {
        return instance;
    }

    public Bill createEntity(ResultSet resultSet) throws DaoException {
        Bill bill;
        try {
            long id = resultSet.getLong(BILL_ID);
            BigDecimal totalPrice = resultSet.getBigDecimal(BILL_TOTAL_PRICE);
            Bill.BillStatus status = Bill.BillStatus.valueOf(resultSet.getString(BILL_STATUS).toUpperCase());
            LocalDateTime date = LocalDateTime.parse(resultSet.getDate(BILL_PAYMENT_DATE).toString());
            Bill.BillBuilder builder = new Bill.BillBuilder();
            bill = builder.setId(id)
                    .setTotalPrice(totalPrice)
                    .setStatus(status)
                    .setPaymentDate(date)
                    .build();
        } catch (SQLException e) {
            String message = "Bill can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bill;
    }
}