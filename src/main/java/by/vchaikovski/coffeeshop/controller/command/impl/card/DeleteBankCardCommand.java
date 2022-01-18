package by.vchaikovski.coffeeshop.controller.command.impl.card;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.service.BankCardService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.util.PageExtractor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeeshop.controller.command.PagePath.CARD_INFO_PAGE;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class DeleteBankCardCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        HttpSession session = request.getSession();
        session.removeAttribute(DELETE_RESULT);
        Long cardId = (Long)session.getAttribute(CARD_ID);
        try {
            boolean isDeleted = cardId != null && cardService.deleteBankCardById(cardId);
            session.setAttribute(DELETE_RESULT, isDeleted);
            if (isDeleted) {
                session.removeAttribute(CARD_PARAMETERS);
                session.removeAttribute(CARD_ID);
            }
        } catch (ServiceException e) {
            String message = "Deletion card can't be completed.";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        /*PageExtractor extractor = PageExtractor.getInstance();
        String currantPage = extractor.extractCurrentPage(request);*/
        session.setAttribute(CURRENT_PAGE, CARD_INFO_PAGE);
        Router router = new Router(CARD_INFO_PAGE);
        router.setRouterType(Router.RouterType.REDIRECT);

        return router;
    }
}