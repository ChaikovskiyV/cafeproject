package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.service.BankCardService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.CARD_ID;

/**
 * @author VChaikovski
 * The type Delete bank card command.
 * @project Coffeehouse
 */

public class DeleteBankCardCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        String cardId = request.getParameter(CARD_ID);
        try {
            boolean isDeleted = cardService.deleteBankCardById(Long.parseLong(cardId));
            request.setAttribute(IS_DELETED, isDeleted);
            if (isDeleted) {
                HttpSession session = request.getSession();
                session.removeAttribute(IS_FOUND);
                session.removeAttribute(CARD);
            }
        } catch (ServiceException e) {
            String message = "Deletion card can't be completed.";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.CARD_INFO_PAGE);
    }
}