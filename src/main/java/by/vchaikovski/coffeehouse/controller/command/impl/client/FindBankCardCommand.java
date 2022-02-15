package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.BankCard;
import by.vchaikovski.coffeehouse.model.service.BankCardService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * The type Find bank card command.
 * @project Coffeehouse
 */

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
            session.setAttribute(IS_FOUND, optionalCard.isPresent());
            if (optionalCard.isPresent()) {
                session.setAttribute(CARD, optionalCard.get());
            } else {
                request.setAttribute(FIND_CARD, true);
            }
        } catch (ServiceException e) {
            String message = "Card search can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.CARD_INFO_PAGE);
    }
}