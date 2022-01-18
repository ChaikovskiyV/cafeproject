package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.entity.BankCard;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

public interface BankCardDao extends BaseDao<BankCard> {
    Optional<BankCard> findByCardNumber(String cardNumber) throws DaoException;

    Optional<BankCard> findByCardNumberAndDate(String cardNumber, LocalDate expirationDate) throws DaoException;

    boolean updateBankCardAmount(long id, BigDecimal decimal) throws DaoException;
}