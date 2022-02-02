package by.vchaikovski.coffeeshop.controller.command.impl.user.client;

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

import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER;

public class ChangePasswordCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
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
                for (Map.Entry<String, String> entry : passwordData.entrySet()) {
                    String value = entry.getValue();
                    if (value.equals(WRONG_MEANING)) {
                        request.setAttribute(entry.getKey(), value);
                    }
                }
            }
        } catch (ServiceException e) {
            String message = "Update user password command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_INFO_PAGE);
    }
}