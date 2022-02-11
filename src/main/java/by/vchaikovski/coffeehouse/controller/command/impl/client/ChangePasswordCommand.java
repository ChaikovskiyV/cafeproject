package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import by.vchaikovski.coffeehouse.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Change password command.
 */

public class ChangePasswordCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(RequestParameter.SHOW_PROFILE, true);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        UserService userService = ServiceProvider.getInstance().getUserService();
        Map<String, String> passwordData = new HashMap<>();
        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);
        String newPassword = request.getParameter(NEW_PASSWORD);
        passwordData.put(PASSWORD, password);
        passwordData.put(PASSWORD_REPEAT, passwordRepeat);
        passwordData.put(NEW_PASSWORD, newPassword);
        try {
            boolean isUpdated = userService.updateUserPassword(user.getId(), passwordData);
            request.setAttribute(IS_UPDATED_PASSWORD, isUpdated);
            if (!isUpdated) {
                request.setAttribute(SHOW_PROFILE, true);
                for (Map.Entry<String, String> entry : passwordData.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    if (value.equals(WRONG_MEANING)) {
                        switch (key) {
                            case PASSWORD -> request.setAttribute(PASSWORD_CHECK, value);
                            case NEW_PASSWORD -> request.setAttribute(NEW_PASSWORD_CHECK, value);
                            case PASSWORD_REPEAT -> request.setAttribute(PASSWORD_REPEAT_CHECK, value);
                            default -> logger.debug(() -> "Unknown parameter: " + key);
                        }
                    }
                }
            }
        } catch (ServiceException e) {
            String message = "Update user password command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        User.Role userRole = user.getRole();
        return switch (userRole) {
            case CLIENT -> new Router(PagePath.CLIENT_HOME_PAGE);
            case BARISTA -> new Router(PagePath.BARISTA_HOME_PAGE);
            case ADMIN -> new Router(PagePath.ADMIN_HOME_PAGE);
            default -> new Router(PagePath.MAIN_PAGE);
        };
    }
}