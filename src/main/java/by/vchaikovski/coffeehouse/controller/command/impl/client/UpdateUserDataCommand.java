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
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER;


/**
 * @author VChaikovski
 * The type Update user data command.
 * @project Coffeehouse
 */

public class UpdateUserDataCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(RequestParameter.SHOW_PROFILE, true);
        UserService userService = ServiceProvider.getInstance().getUserService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        long userId = user.getId();
        String login = request.getParameter(LOGIN);
        String email = request.getParameter(EMAIL);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        Map<String, String> uniqueData = new HashMap<>();
        uniqueData.put(LOGIN, login);
        uniqueData.put(EMAIL, email);
        uniqueData.put(PHONE_NUMBER, phoneNumber);
        try {
            boolean isLoginUpdated = login != null && !login.equals(user.getLogin()) &&
                    userService.updateUserLogin(userId, uniqueData);
            boolean isEmailUpdated = email != null && !email.equals(user.getEmail()) &&
                    userService.updateUserEmail(userId, uniqueData);
            boolean isPhoneNumberUpdated = phoneNumber != null && !phoneNumber.equals(user.getPhoneNumber()) &&
                    userService.updateUserPhoneNumber(userId, uniqueData);
            boolean isFirstNameUpdated = firstName != null && !firstName.equals(user.getFirstName()) &&
                    userService.updateUserFirstName(userId, firstName);
            boolean isLastNameUpdated = lastName != null && !lastName.equals(user.getLastName()) &&
                    userService.updateUserLastName(userId, lastName);
            fillWrongAttr(uniqueData, request);
            if (!isFirstNameUpdated && firstName != null && !firstName.equals(user.getFirstName())) {
                request.setAttribute(FIRST_NAME_CHECK, WRONG_MEANING);
            }
            if (!isLastNameUpdated && lastName != null && !lastName.equals(user.getLastName())) {
                request.setAttribute(LAST_NAME_CHECK, WRONG_MEANING);
            }
            if (isEmailUpdated || isLoginUpdated || isPhoneNumberUpdated || isFirstNameUpdated || isLastNameUpdated) {
                Optional<User> optionalUser = userService.findUserById(userId);
                optionalUser.ifPresent(updatedUser -> session.setAttribute(USER, updatedUser));
            }
        } catch (ServiceException e) {
            String message = "Update user data command can't be completed";
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

    private void fillWrongAttr(Map<String, String> userParameters, HttpServletRequest request) {
        for (Map.Entry<String, String> userParam : userParameters.entrySet()) {
            String paramName = userParam.getKey();
            String paramMeaning = userParam.getValue();
            if (paramMeaning.equals(NOT_UNIQUE_MEANING) || paramMeaning.equals(WRONG_MEANING)) {
                switch (paramName) {
                    case LOGIN -> request.setAttribute(LOGIN_CHECK, paramMeaning);
                    case EMAIL -> request.setAttribute(EMAIL_CHECK, paramMeaning);
                    case PHONE_NUMBER -> request.setAttribute(PHONE_NUMBER_CHECK, paramMeaning);
                    default -> logger.debug(() -> "Uncheckable parameter: " + paramName);
                }
            }
        }
    }
}