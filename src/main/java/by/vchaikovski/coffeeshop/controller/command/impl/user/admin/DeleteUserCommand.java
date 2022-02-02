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

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.IS_DELETED;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.USER_ID_REQ;

public class DeleteUserCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        UserService userService = ServiceProvider.getInstance().getUserService();
        String userId = request.getParameter(USER_ID_REQ);
        try {
            boolean result = userService.deleteUserById(Long.parseLong(userId));
            request.setAttribute(IS_DELETED, result);
            router = new Router(PagePath.USER_RESEARCH_PAGE);
            if (result) {
                router.setRouterType(Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            String message = "Delete user command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}
