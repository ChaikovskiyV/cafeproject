package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.User;
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
        request.setAttribute(SHOW_CARD, true);
        BankCardService cardService = ServiceProvider.getInstance().getBankCardService();
        String cardId = request.getParameter(CARD_ID);
        try {
            boolean isDeleted = cardService.deleteBankCardById(Long.parseLong(cardId));
            request.setAttribute(DELETE_RESULT, isDeleted);
            if (isDeleted) {
                request.removeAttribute(CARD);
            }
        } catch (ServiceException e) {
            String message = "Deletion card can't be completed.";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER);
        User.Role userRole = user != null ? user.getRole() : User.Role.GUEST;
        return switch (userRole) {
            case ADMIN -> new Router(PagePath.ADMIN_HOME_PAGE);
            case BARISTA -> new Router(PagePath.BARISTA_HOME_PAGE);
            case CLIENT -> new Router(PagePath.CLIENT_HOME_PAGE);
            default -> new Router(PagePath.MAIN_PAGE);
        };
    }
}