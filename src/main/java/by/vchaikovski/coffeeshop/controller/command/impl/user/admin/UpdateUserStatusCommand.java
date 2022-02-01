package by.vchaikovski.coffeeshop.controller.command.impl.user.admin;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class UpdateUserStatusCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = ServiceProvider.getInstance().getUserService();
        String userStatus = request.getParameter(USER_STATUS);
        String userId = request.getParameter(USER_ID_REQ);
        try {
            boolean result = userService.updateUserStatus(Long.parseLong(userId), userStatus);
            request.setAttribute(IS_UPDATED_USER_STATUS, result);
        } catch (ServiceException e) {
            String message = "Update user status command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_RESEARCH_PAGE);
    }
}