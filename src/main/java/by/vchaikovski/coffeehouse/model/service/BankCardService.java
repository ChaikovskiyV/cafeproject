package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.BankCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Bank card service.
 */
public interface BankCardService {
    /**
     * Find all cards list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<BankCard> findAllCards() throws ServiceException;

    /**
     * Find card by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<BankCard> findCardById(long id) throws ServiceException;

    /**
     * Find card by number optional.
     *
     * @param cardNumber the card number
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<BankCard> findCardByNumber(String cardNumber) throws ServiceException;

    /**
     * Find card by number and date optional.
     *
     * @param number the number
     * @param date   the date
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<BankCard> findCardByNumberAndDate(String number, String date) throws ServiceException;

    /**
     * Create bank card long.
     *
     * @param cardParameters the card parameters
     * @return the long
     * @throws ServiceException the service exception
     */
    long createBankCard(Map<String, String> cardParameters) throws ServiceException;

    /**
     * Delete bank card by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteBankCardById(long id) throws ServiceException;

    /**
     * Top up card boolean.
     *
     * @param id     the id
     * @param amount the amount
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean topUpCard(long id, String amount) throws ServiceException;

    /**
     * Withdraw money card boolean.
     *
     * @param id     the id
     * @param amount the amount
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean withdrawMoneyCard(long id, String amount) throws ServiceException;
}