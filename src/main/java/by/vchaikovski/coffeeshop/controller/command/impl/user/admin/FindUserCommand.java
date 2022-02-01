package by.vchaikovski.coffeeshop.controller.command.impl.user.admin;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class FindUserCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        UserService userService = ServiceProvider.getInstance().getUserService();
        List<User> users = new ArrayList<>();
        String email = request.getParameter(EMAIL);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        String login = request.getParameter(LOGIN);
        try {
            if (login != null) {
                Optional<User> optionalUser = userService.findUserByLogin(login);
                optionalUser.ifPresent(users::add);
            } else if (email != null) {
                Optional<User> optionalUser = userService.findUserByEmail(email);
                optionalUser.ifPresent(users::add);
            } else if (phoneNumber != null) {
                Optional<User> optionalUser = userService.findUserByPhoneNumber(phoneNumber);
                optionalUser.ifPresent(users::add);
            } else {
                Map<String, String> userParameters = new HashMap<>();
                String firstName = request.getParameter(FIRST_NAME);
                String lastName = request.getParameter(LAST_NAME);
                String discountRate = request.getParameter(DISCOUNT_RATE);
                String discountType = request.getParameter(DISCOUNT_TYPE);
                String userStatus = request.getParameter(USER_STATUS);
                String userRole = request.getParameter(USER_ROLE);
                userParameters.put(FIRST_NAME, firstName);
                userParameters.put(LAST_NAME, lastName);
                userParameters.put(DISCOUNT_RATE, discountRate);
                userParameters.put(DISCOUNT_TYPE, discountType);
                userParameters.put(USER_STATUS, userStatus);
                userParameters.put(USER_ROLE, userRole);
                users = userService.findUsersBySeveralParameters(userParameters);
            }
            request.setAttribute(USER_LIST, users);
        } catch (ServiceException e) {
            String message = "Find user command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_RESEARCH_PAGE);
    }
}