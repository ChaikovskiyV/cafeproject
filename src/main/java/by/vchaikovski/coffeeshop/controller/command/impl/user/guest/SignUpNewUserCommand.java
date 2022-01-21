package by.vchaikovski.coffeeshop.controller.command.impl.user.guest;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.SIGN_UP_RESULT;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER_PARAMETERS;

public class SignUpNewUserCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String SIGN_UP_FALL = "sign_up.fall";
    private static final String SIGN_UP_SUCCESS = "sing_up.success";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        UserService userService = ServiceProvider.getInstance().getUserService();
        HttpSession session = request.getSession();
        String password = request.getParameter(PASSWORD);
        String passwordRepeat = request.getParameter(PASSWORD_REPEAT);
        Map<String, String> userParameters = buildUserParametersMap(request);
        if (password.equals(passwordRepeat)) {
            try {
                long userId = userService.createUser(userParameters);
                if (userId > 0) {
                    session.setAttribute(SIGN_UP_RESULT, SIGN_UP_SUCCESS);
                    router = new Router(PagePath.MAIN_PAGE);
                    router.setRouterType(Router.RouterType.REDIRECT);
                } else {
                    session.setAttribute(SIGN_UP_RESULT, SIGN_UP_FALL);
                    session.setAttribute(USER_PARAMETERS, userParameters);
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
            switch (value) {
                case NOT_UNIQUE_MEANING -> {
                    switch (key) {
                        case LOGIN -> request.setAttribute(LOGIN_CHECK, value);
                        case EMAIL -> request.setAttribute(EMAIL_CHECK, value);
                        case PHONE_NUMBER -> request.setAttribute(PHONE_NUMBER_CHECK, value);
                    }
                }
                case WRONG_MEANING -> {
                    switch (key) {
                        case LOGIN -> request.setAttribute(LOGIN_CHECK, value);
                        case EMAIL -> request.setAttribute(EMAIL_CHECK, value);
                        case PHONE_NUMBER -> request.setAttribute(PHONE_NUMBER_CHECK, value);
                        case FIRST_NAME -> request.setAttribute(FIRST_NAME_CHECK, value);
                        case LAST_NAME -> request.setAttribute(LAST_NAME_CHECK, value);
                        case PASSWORD -> request.setAttribute(PASSWORD_CHECK, value);
                    }
                }
                default -> logger.debug(() -> key + " is correct");
            }
        }
    }
}