package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.BankCard;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.math.BigDecimal;
import java.util.Optional;

public interface BankCardDao extends BaseDao<BankCard> {
    Optional<BankCard> findByCardNumber(String cardNumber) throws DaoException;

    boolean updateBankCardAmount(long id, BigDecimal decimal) throws DaoException;
}