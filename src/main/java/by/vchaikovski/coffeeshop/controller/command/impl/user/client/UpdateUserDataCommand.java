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
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER;


public class UpdateUserDataCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
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
        return new Router(PagePath.USER_INFO_PAGE);
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