package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.BankCard;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BankCardService {
    List<BankCard> findAllCards() throws ServiceException;

    Optional<BankCard> findCardById(long id) throws ServiceException;

    Optional<BankCard> findCardByNumber(String cardNumber) throws ServiceException;

    Optional<BankCard> findCardByNumberAndExpirationDate(String number, String date) throws ServiceException;

    boolean createBankCard(Map<String, String> cardParameters) throws ServiceException;

    boolean deleteBankCardById(long id) throws ServiceException;

    boolean topUpCard(long id, String amount) throws ServiceException;

    boolean withdrawMoney(long id, String amount) throws ServiceException;
}