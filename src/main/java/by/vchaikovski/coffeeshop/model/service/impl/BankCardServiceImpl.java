package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.dao.impl.BankCardDaoImpl;
import by.vchaikovski.coffeeshop.model.entity.BankCard;
import by.vchaikovski.coffeeshop.model.service.BankCardService;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class BankCardServiceImpl implements BankCardService {
    private static final Logger logger = LogManager.getLogger();
    private static final BankCardDaoImpl BANK_CARD_DAO = DaoProvider.getInstance().getBankCardDao();
    private static final DataValidator VALIDATOR = DataValidatorImpl.getInstance();
    private static BankCardServiceImpl instance = new BankCardServiceImpl();

    private BankCardServiceImpl() {
    }

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
            bankCards = BANK_CARD_DAO.findAll();
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
            optionalCard = BANK_CARD_DAO.findById(id);
        } catch (DaoException e) {
            String message = "Bank card can't be found by id " + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalCard;
    }

    @Override
    public Optional<BankCard> findCardByNumber(String cardNumber) throws ServiceException {
        Optional<BankCard> optionalCard;
        try {
            optionalCard = VALIDATOR.isCardNumberValid(cardNumber)
                    ? BANK_CARD_DAO.findByCardNumber(cardNumber)
                    : Optional.empty();
        } catch (DaoException e) {
            String message = "Bank card can't be found by cardNumber " + cardNumber;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalCard;
    }

    @Override
    public Optional<BankCard> findCardByNumberAndExpirationDate(String number, String date) throws ServiceException {
        Optional<BankCard> optionalCard = Optional.empty();
        if (VALIDATOR.isCardNumberValid(number) && VALIDATOR.isDateValid(date)) {
            String format = "yyyy-MM";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalDate expirationDate = LocalDate.parse(date, formatter);
            try {
                optionalCard = BANK_CARD_DAO.findByCardNumberAndExpirationDate(number, expirationDate);
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
    public boolean createBankCard(Map<String, String> cardParameters) throws ServiceException {
        String cardNumber = cardParameters.get(CARD_NUMBER);
        String expirationDate = cardParameters.get(CARD_EXPIRATION_DATE);
        String cardAmount = cardParameters.get(CARD_AMOUNT);
        long cardId = 0;
        if (VALIDATOR.isDateValid(expirationDate) && VALIDATOR.isCardNumberValid(cardNumber) &&
                VALIDATOR.isNumberValid(cardAmount)) {
            try {
                Optional<BankCard> optionalCard = BANK_CARD_DAO.findByCardNumber(cardNumber);
                if (optionalCard.isPresent() && cardParameters.remove(CARD_NUMBER, cardNumber)) {
                    return false;
                }
                LocalDate date = LocalDate.parse(expirationDate);
                BigDecimal amount = new BigDecimal(cardAmount);
                BankCard card = new BankCard.BankCardBuilder()
                        .setCardNumber(cardNumber)
                        .setExpirationDate(date)
                        .setAmount(amount)
                        .build();
                cardId = BANK_CARD_DAO.create(card);
            } catch (DaoException e) {
                String message = "Bank card can't be added.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return cardId > 0;
    }

    @Override
    public boolean deleteBankCardById(long id) throws ServiceException {
        try {
            return BANK_CARD_DAO.deleteById(id);
        } catch (DaoException e) {
            String message = "Bank card can't be deleted.";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
    }

    @Override
    public boolean topUpCard(long id, String amountStr) throws ServiceException {
        if (VALIDATOR.isNumberValid(amountStr)) {
            try {
                Optional<BankCard> optionalCard = BANK_CARD_DAO.findById(id);
                if (optionalCard.isEmpty()) {
                    return false;
                }
                BigDecimal amount = new BigDecimal(amountStr);
                BigDecimal oldAmount = optionalCard.get().getAmount();
                return BANK_CARD_DAO.updateBankCardAmount(id, oldAmount.add(amount));
            } catch (DaoException e) {
                String message = "Bank card can't be top up.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return false;
    }

    @Override
    public boolean withdrawMoney(long id, String amountStr) throws ServiceException {
        if (VALIDATOR.isNumberValid(amountStr)) {
            try {
                Optional<BankCard> optionalCard = BANK_CARD_DAO.findById(id);
                if (optionalCard.isEmpty()) {
                    return false;
                }
                BigDecimal amount = new BigDecimal(amountStr);
                BigDecimal oldAmount = optionalCard.get().getAmount();
                return oldAmount.compareTo(amount) >= 0 &&
                        BANK_CARD_DAO.updateBankCardAmount(id, oldAmount.subtract(amount));
            } catch (DaoException e) {
                String message = "Impossible to withdraw money from bank card.";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return false;
    }
}