package by.vchaikovski.coffeehouse.model.dao.mapper.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeehouse.model.entity.BankCard;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.vchaikovski.coffeehouse.model.dao.ColumnTable.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Bank card mapper.
 */

public class BankCardMapperImpl implements BaseMapper<BankCard> {
    private static final BankCardMapperImpl instance = new BankCardMapperImpl();

    private BankCardMapperImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
            bankCard = new BankCard(number, date, amount);
            bankCard.setId(id);
        } catch (SQLException e) {
            String message = "BankCard can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bankCard;
    }
}