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

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.PagePath.CARD_INFO_PAGE;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class TopUpCardBalanceCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        HttpSession session = request.getSession();
        long cardId = (long) session.getAttribute(CARD_ID);
        String amount = request.getParameter(CARD_AMOUNT);
        try {
            boolean isTopUp = cardService.topUpCard(cardId, amount);
            request.setAttribute(RESULT, isTopUp);
            if(isTopUp) {
                Optional<BankCard> optionalCard = cardService.findCardById(cardId);
                if(optionalCard.isPresent()) {
                    BigDecimal count = optionalCard.get().getAmount();
                    Map<String, String> cardParameters = (Map<String, String>) session.getAttribute(CARD_PARAMETERS);
                    cardParameters.replace(CARD_AMOUNT, count.toString());
                    session.setAttribute(CARD_PARAMETERS, cardParameters);
                }
            }
        } catch (ServiceException e) {
            String message = "Account refill can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        session.setAttribute(CURRENT_PAGE, CARD_INFO_PAGE);
        Router router = new Router(CARD_INFO_PAGE);
        //router.setRouterType(Router.RouterType.REDIRECT);

        return router;
    }
}
