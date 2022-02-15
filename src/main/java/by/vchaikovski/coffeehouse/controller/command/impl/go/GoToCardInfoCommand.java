package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
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

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.CARD;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to card info command.
 */

public class GoToCardInfoCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        BankCard bankCard = (BankCard) session.getAttribute(CARD);
        Long cardId = (Long) session.getAttribute(SessionParameter.CARD_ID);
        if (bankCard == null && cardId != null) {
            BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
            try {
                Optional<BankCard> optionalCard = cardService.findCardById(cardId);
                optionalCard.ifPresent(card -> session.setAttribute(CARD, card));
            } catch (ServiceException e) {
                String message = "Go to card info command can't be completed";
                logger.error(message, e);
                throw new CommandException(message, e);
            }
        }
        return new Router(PagePath.CARD_INFO_PAGE);
    }
}