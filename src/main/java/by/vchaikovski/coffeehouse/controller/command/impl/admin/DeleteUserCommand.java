package by.vchaikovski.coffeehouse.controller.command.impl.admin;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import by.vchaikovski.coffeehouse.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.IS_DELETED;
import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.USER_ID_REQ;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Delete user command.
 */
public class DeleteUserCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = ServiceProvider.getInstance().getUserService();
        String userId = request.getParameter(USER_ID_REQ);
        try {
            boolean result = userService.deleteUserById(Long.parseLong(userId));
            request.setAttribute(IS_DELETED, result);
        } catch (ServiceException e) {
            String message = "Delete user command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_RESEARCH_PAGE);
    }
}