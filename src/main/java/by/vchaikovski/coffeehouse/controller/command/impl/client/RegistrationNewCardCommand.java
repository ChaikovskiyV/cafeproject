package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.service.BankCardService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.CARD_ID;

/**
 * @author VChaikovski
 * The type Registration new card command.
 * @project Coffeehouse
 */

public class RegistrationNewCardCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(RequestParameter.REGISTER_CARD, true);
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        String cardNumber = request.getParameter(CARD_NUMBER);
        String expirationDate = request.getParameter(CARD_EXPIRATION_DATE);
        String amount = request.getParameter(CARD_AMOUNT);
        Map<String, String> cardParameters = new HashMap<>();
        cardParameters.put(CARD_NUMBER, cardNumber);
        cardParameters.put(CARD_EXPIRATION_DATE, expirationDate);
        cardParameters.put(CARD_AMOUNT, amount);
        try {
            long cardId = cardService.createBankCard(cardParameters);
            if (cardId == 0) {
                logger.debug("Card wasn't registered.");
                request.setAttribute(CARD_PARAMETERS_REQ, cardParameters);
                for (Map.Entry<String, String> entry : cardParameters.entrySet()) {
                    String value = entry.getValue();
                    String key = entry.getKey();
                    switch (value) {
                        case NOT_UNIQUE_MEANING -> request.setAttribute(CARD_NUMBER_CHECK, value);
                        case DATE_EXPIRED -> request.setAttribute(CARD_DATE_CHECK, value);
                        case WRONG_MEANING -> setWrongAttribute(key, value, request);
                        default -> logger.debug(() -> key + " is correct");
                    }
                }
            } else {
                HttpSession session = request.getSession();
                session.setAttribute(CARD_ID, cardId);
            }
        } catch (ServiceException e) {
            String message = "Registration new bank card can't be executed.";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.CARD_INFO_PAGE);
    }

    private void setWrongAttribute(String key, String value, HttpServletRequest request) {
        switch (key) {
            case CARD_NUMBER -> request.setAttribute(CARD_NUMBER_CHECK, value);
            case CARD_EXPIRATION_DATE -> request.setAttribute(CARD_DATE_CHECK, value);
            case CARD_AMOUNT -> request.setAttribute(CARD_AMOUNT_CHECK, value);
            default -> logger.info(() -> "Unknown attribute: " + key);
        }
    }
}