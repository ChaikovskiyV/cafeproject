package by.vchaikovski.coffeehouse.controller.command.impl.guest;

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

import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.SIGN_UP_RESULT;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER_PARAMETERS;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Sign up new user command.
 */

public class SignUpNewUserCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        UserService userService = ServiceProvider.getInstance().getUserService();
        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);
        Map<String, String> userParameters = buildUserParametersMap(request);
        if (password.equals(passwordRepeat)) {
            try {
                long userId = userService.createUser(userParameters);
                request.setAttribute(SIGN_UP_RESULT, userId > 0);
                if (userId > 0) {
                    router = new Router(PagePath.SIGN_IN_PAGE);
                } else {
                    request.setAttribute(USER_PARAMETERS, userParameters);
                    fillResultCheckAttribute(userParameters, request);
                    router = new Router(PagePath.REGISTRATION_PAGE);
                }
            } catch (ServiceException e) {
                String message = "Registration new user can't be executed.";
                logger.error(message, e);
                throw new CommandException(message, e);
            }
        } else {
            request.setAttribute(PASSWORD_REPEAT_CHECK, WRONG_MEANING);
            request.setAttribute(USER_PARAMETERS, userParameters);
            router = new Router(PagePath.REGISTRATION_PAGE);
        }
        return router;
    }

    private Map<String, String> buildUserParametersMap(HttpServletRequest request) {
        Map<String, String> userParameters = new HashMap<>();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        userParameters.put(LOGIN, login);
        userParameters.put(PASSWORD, password);
        userParameters.put(FIRST_NAME, firstName);
        userParameters.put(LAST_NAME, lastName);
        userParameters.put(EMAIL, email);
        userParameters.put(PHONE_NUMBER, phoneNumber);

        return userParameters;
    }

    private void fillResultCheckAttribute(Map<String, String> userParameters, HttpServletRequest request) {
        for (Map.Entry<String, String> param : userParameters.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            if (value.equals(NOT_UNIQUE_MEANING) || value.equals(WRONG_MEANING)) {
                fillWrongAttr(key, value, request);
            } else {
                logger.debug(() -> key + " is correct");
            }
        }
    }

    private void fillWrongAttr(String attrName, String attrMeaning, HttpServletRequest request) {
        switch (attrName) {
            case LOGIN -> request.setAttribute(LOGIN_CHECK, attrMeaning);
            case EMAIL -> request.setAttribute(EMAIL_CHECK, attrMeaning);
            case PHONE_NUMBER -> request.setAttribute(PHONE_NUMBER_CHECK, attrMeaning);
            case FIRST_NAME -> request.setAttribute(FIRST_NAME_CHECK, attrMeaning);
            case LAST_NAME -> request.setAttribute(LAST_NAME_CHECK, attrMeaning);
            case PASSWORD -> request.setAttribute(PASSWORD_CHECK, attrMeaning);
            default -> logger.debug(() -> "Uncheckable parameter: " + attrName);
        }
    }
}