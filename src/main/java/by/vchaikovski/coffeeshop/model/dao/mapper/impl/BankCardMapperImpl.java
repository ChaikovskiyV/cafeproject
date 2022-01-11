package by.vchaikovski.coffeeshop.model.dao.mapper.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.model.entity.BankCard;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.vchaikovski.coffeeshop.model.dao.ColumnTable.*;

public class BankCardMapperImpl implements BaseMapper<BankCard> {
    private static final BankCardMapperImpl instance = new BankCardMapperImpl();

    private BankCardMapperImpl() {
    }

    public static BankCardMapperImpl getInstance() {
        return instance;
    }

    @Override
    public BankCard createEntity(ResultSet resultSet) throws DaoException {
        BankCard bankCard;
        try {
            long id = resultSet.getLong(CARD_ID);
            String number = resultSet.getString(CARD_NUMBER);
            LocalDate date = resultSet.getDate(CARD_EXPIRATION_DATE).toLocalDate();
            BigDecimal amount = resultSet.getBigDecimal(CARD_AMOUNT);
            BankCard.BankCardBuilder builder = new BankCard.BankCardBuilder();
            bankCard = builder.setId(id)
                    .setCardNumber(number)
                    .setExpirationDate(date)
                    .setAmount(amount)
                    .build();
        } catch (SQLException e) {
            String message = "BankCard can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bankCard;
    }
}