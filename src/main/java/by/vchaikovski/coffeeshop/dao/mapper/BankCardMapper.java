package by.vchaikovski.coffeeshop.dao.mapper;

import by.vchaikovski.coffeeshop.dao.BaseDao;
import by.vchaikovski.coffeeshop.entity.BankCard;
import by.vchaikovski.coffeeshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class BankCardMapper {
    private static final Logger logger = LogManager.getLogger();

    private BankCardMapper() {
    }

    public static BankCard createCard(ResultSet resultSet) throws DaoException {
        BankCard bankCard;
        try {
            long id = resultSet.getLong(BaseDao.FIRST_PARAMETER_INDEX);
            String number = resultSet.getString(BaseDao.SECOND_PARAMETER_INDEX);
            LocalDate date = resultSet.getDate(BaseDao.THIRD_PARAMETER_INDEX).toLocalDate();
            BigDecimal amount = resultSet.getBigDecimal(BaseDao.FOURTH_PARAMETER_INDEX);
            BankCard.BankCardBuilder builder = new BankCard.BankCardBuilder();
            bankCard = builder.setId(id)
                    .setCardNumber(number)
                    .setExpirationDate(date)
                    .setAmount(amount)
                    .build();
        } catch (SQLException e) {
            logger.error(() -> "BankCard can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
            throw new DaoException("BankCard can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
        }
        return bankCard;
    }
}