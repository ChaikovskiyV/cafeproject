package by.vchaikovski.coffeehouse.model.service.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.dao.BankCardDao;
import by.vchaikovski.coffeehouse.model.dao.DaoProvider;
import by.vchaikovski.coffeehouse.model.entity.BankCard;
import by.vchaikovski.coffeehouse.model.service.BankCardService;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.FormValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import by.vchaikovski.coffeehouse.util.validator.impl.FormValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Bank card service.
 */
public class BankCardServiceImpl implements BankCardService {
    private static final Logger logger = LogManager.getLogger();
    private static BankCardServiceImpl instance = new BankCardServiceImpl();
    private final BankCardDao cardDao;

    private BankCardServiceImpl() {
        cardDao = DaoProvider.getInstance().getBankCardDao();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static BankCardServiceImpl getInstance() {
        if (instance == null) {
            instance = new BankCardServiceImpl();
        }
        return instance;
    }

    @Override
    public List<BankCard> findAllCards() throws ServiceException {
        List<BankCard> bankCards;
        try {
            bankCards = cardDao.findAll();
        } catch (DaoException e) {
            String message = "Impossible find all bank cards.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return bankCards;
    }

    @Override
    public Optional<BankCard> findCardById(long id) throws ServiceException {
        Optional<BankCard> optionalCard;
        try {
            optionalCard = cardDao.findById(id);
        } catch (DaoException e) {
            String message = "Bank card can't be found by id " + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalCard;
    }

    @Override
    public Optional<BankCard> findCardByNumber(String cardNumber) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        Optional<BankCard> optionalCard;
        try {
            optionalCard = validator.isCardNumberValid(cardNumber)
                    ? cardDao.findByCardNumber(cardNumber)
                    : Optional.empty();
        } catch (DaoException e) {
            String message = "Bank card can't be found by cardNumber " + cardNumber;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalCard;
    }

    @Override
    public Optional<BankCard> findCardByNumberAndDate(String number, String date) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        Optional<BankCard> optionalCard = Optional.empty();
        if (validator.isCardNumberValid(number) && validator.isDateValid(date)) {
            LocalDate expirationDate = LocalDate.parse(date);
            try {
                optionalCard = cardDao.findByCardNumberAndDate(number, expirationDate);
            } catch (DaoException e) {
                String message = "Bank card can't be found by expiration date=" + expirationDate +
                        " and card number=" + number;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return optionalCard;
    }

    @Override
    public long createBankCard(Map<String, String> cardParameters) throws ServiceException {
        long cardId = 0;
        FormValidator validator = FormValidatorImpl.getInstance();
        if (validator.isCardParametersValid(cardParameters)) {
            String cardNumber = cardParameters.get(CARD_NUMBER);
            String expirationDate = cardParameters.get(CARD_EXPIRATION_DATE);
            String cardAmount = cardParameters.get(CARD_AMOUNT);
            try {
                Optional<BankCard> optionalCard = cardDao.findByCardNumber(cardNumber);
                if (optionalCard.isPresent()) {
                    cardParameters.replace(CARD_NUMBER, NOT_UNIQUE_MEANING);
                    return cardId;
                }
                LocalDate date = LocalDate.parse(expirationDate);
                if (date.isBefore(LocalDate.now())) {
                    cardParameters.replace(CARD_EXPIRATION_DATE, DATE_EXPIRED);
                    return cardId;
                }
                BigDecimal amount = new BigDecimal(cardAmount);
                BankCard card = new BankCard(cardNumber, date, amount);
                cardId = cardDao.create(card);
            } catch (DaoException e) {
                String message = "Bank card can't be added.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return cardId;
    }

    @Override
    public boolean deleteBankCardById(long id) throws ServiceException {
        try {
            return cardDao.deleteById(id);
        } catch (DaoException e) {
            String message = "Bank card can't be deleted.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean topUpCard(long id, String amountStr) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(amountStr)) {
            try {
                Optional<BankCard> optionalCard = cardDao.findById(id);
                if (optionalCard.isEmpty()) {
                    return false;
                }
                BigDecimal amount = new BigDecimal(amountStr);
                BankCard card = optionalCard.get();
                LocalDate expirationDate = card.getExpirationDate();
                return validator.isDateLaterCurrently(expirationDate) && cardDao.increaseBankCardAmount(id, amount);
            } catch (DaoException e) {
                String message = "Bank card can't be top up.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return false;
    }

    @Override
    public boolean withdrawMoneyCard(long id, String amountStr) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(amountStr)) {
            try {
                Optional<BankCard> optionalCard = cardDao.findById(id);
                if (optionalCard.isEmpty()) {
                    return false;
                }
                BigDecimal amount = new BigDecimal(amountStr);
                BankCard card = optionalCard.get();
                LocalDate expirationDate = card.getExpirationDate();

                return validator.isDateLaterCurrently(expirationDate) && cardDao.reduceBankCardAmount(id, amount);
            } catch (DaoException e) {
                String message = "Impossible to withdraw money from bank card.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return false;
    }
}