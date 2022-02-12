package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.BankCard;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Bank card dao.
 */
public interface BankCardDao extends BaseDao<BankCard> {
    /**
     * Find by card number optional.
     *
     * @param cardNumber the card number
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<BankCard> findByCardNumber(String cardNumber) throws DaoException;

    /**
     * Find by card number and date optional.
     *
     * @param cardNumber     the card number
     * @param expirationDate the expiration date
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<BankCard> findByCardNumberAndDate(String cardNumber, LocalDate expirationDate) throws DaoException;

    /**
     * Increase bank card amount boolean.
     *
     * @param id      the id
     * @param decimal the decimal
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean increaseBankCardAmount(long id, BigDecimal decimal) throws DaoException;

    /**
     * Reduce bank card amount boolean.
     *
     * @param id      the id
     * @param decimal the decimal
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean reduceBankCardAmount(long id, BigDecimal decimal) throws DaoException;
}