package by.vchaikovski.coffeeshop.controller.command.impl.card;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.BankCard;
import by.vchaikovski.coffeeshop.model.service.BankCardService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.PagePath.CARD_INFO_PAGE;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.*;

public class FindBankCardCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        HttpSession session = request.getSession();
        String cardNumber = request.getParameter(CARD_NUMBER);
        String expirationDate = request.getParameter(CARD_EXPIRATION_DATE);
        try {
            Optional<BankCard> optionalCard = cardService.findCardByNumberAndDate(cardNumber, expirationDate);
            request.setAttribute(IS_FOUND, optionalCard.isPresent());
            optionalCard.ifPresent(bankCard -> {
                session.setAttribute(CARD_ID, bankCard.getId());
                Map<String, String> cardParameters = new HashMap<>();
                cardParameters.put(CARD_NUMBER, bankCard.getCardNumber());
                cardParameters.put(CARD_EXPIRATION_DATE, bankCard.getExpirationDate().toString());
                cardParameters.put(CARD_AMOUNT, bankCard.getAmount().toString());
                session.setAttribute(CARD_PARAMETERS, cardParameters);
            });
        } catch (ServiceException e) {
            String message = "Card search can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(CARD_INFO_PAGE);
    }
}