package by.vchaikovski.coffeeshop.dao.mapper;

import by.vchaikovski.coffeeshop.dao.BaseDao;
import by.vchaikovski.coffeeshop.entity.Bill;
import by.vchaikovski.coffeeshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class BillMapper {
    private static final Logger logger = LogManager.getLogger();

    private BillMapper() {
    }

    public static Bill createBill(ResultSet resultSet) throws DaoException {
        Bill bill;
        try {
            long id = resultSet.getLong(BaseDao.FIRST_PARAMETER_INDEX);
            BigDecimal totalPrice = resultSet.getBigDecimal(BaseDao.SECOND_PARAMETER_INDEX);
            Bill.BillStatus status = Bill.BillStatus.valueOf(resultSet.getString(BaseDao.THIRD_PARAMETER_INDEX).toUpperCase());
            LocalDateTime date = resultSet.getDate(BaseDao.FOURTH_PARAMETER_INDEX).toLocalDate().atStartOfDay();
            Bill.BillBuilder builder = new Bill.BillBuilder();
            bill = builder.setId(id)
                    .setTotalPrice(totalPrice)
                    .setStatus(status)
                    .setPaymentDate(date)
                    .build();
        } catch (SQLException e) {
            logger.error(() -> "Bill can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
            throw new DaoException("Bill can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
        }
        return bill;
    }
}