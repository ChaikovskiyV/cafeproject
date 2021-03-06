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
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.CARD_ID;

/**
 * @author VChaikovski
 * The type Top up card balance command.
 * @project Coffeehouse
 */

public class TopUpCardBalanceCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router = new Router(PagePath.CARD_INFO_PAGE);
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        HttpSession session = request.getSession();
        String cardId = request.getParameter(CARD_ID);
        String amount = request.getParameter(CARD_AMOUNT);
        try {
            long id = Long.parseLong(cardId);
            boolean isReplenished = cardService.topUpCard(id, amount);
            request.setAttribute(RESULT, isReplenished);
            if (isReplenished) {
                Optional<BankCard> optionalCard = cardService.findCardById(id);
                optionalCard.ifPresent(bankCard -> session.setAttribute(CARD, bankCard));
                router.setRouterType(Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            String message = "Account refill can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}