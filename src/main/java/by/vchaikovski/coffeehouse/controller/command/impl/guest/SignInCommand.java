package by.vchaikovski.coffeehouse.controller.command.impl.guest;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import by.vchaikovski.coffeehouse.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER_ROLE;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Sign in command.
 */

public class SignInCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = ServiceProvider.getInstance().getUserService();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        Router router;
        try {
            Optional<User> optionalUser = userService.findUserByLoginAndPassword(login, password);
            request.setAttribute(RESULT, optionalUser.isPresent());
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                HttpSession session = request.getSession();
                User.Role role = user.getRole();
                session.setAttribute(USER_ROLE, user.getRole().name());
                session.setAttribute(USER, user);
                router = switch (role) {
                    case CLIENT -> new Router(PagePath.CLIENT_HOME_PAGE);
                    case BARISTA -> new Router(PagePath.BARISTA_HOME_PAGE);
                    case ADMIN -> new Router(PagePath.ADMIN_HOME_PAGE);
                    default -> new Router(PagePath.SIGN_IN_PAGE);
                };
            } else {
                logger.debug("User is not found");
                router = new Router(PagePath.SIGN_IN_PAGE);
            }
        } catch (ServiceException e) {
            String message = "Sing in command can't be executed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}