package by.vchaikovski.coffeeshop.controller.command.impl.common;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.LOGIN;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.PASSWORD;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER_ROLE;

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
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                HttpSession session = request.getSession();
                session.setAttribute(USER_ROLE, user.getRole());
                session.setAttribute(USER, user);
                router = new Router(PagePath.MAIN_PAGE);
            } else {
                logger.debug("User is not found");
                router = new Router(PagePath.LOG_IN_PAGE);
            }
        } catch (ServiceException e) {
            String message = "Sing in command can't be executed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}