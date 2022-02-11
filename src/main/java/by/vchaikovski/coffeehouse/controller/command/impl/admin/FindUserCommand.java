package by.vchaikovski.coffeehouse.controller.command.impl.admin;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Discount;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.model.service.DiscountService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import by.vchaikovski.coffeehouse.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Find user command.
 */

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
            addDiscountToRequest(users, request);
            request.setAttribute(RESULT, !users.isEmpty());
            request.setAttribute(USER_LIST, users);
        } catch (ServiceException e) {
            String message = "Find user command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_RESEARCH_PAGE);
    }

    private void addDiscountToRequest(List<User> users, HttpServletRequest request) throws ServiceException {
        DiscountService discountService = ServiceProvider.getInstance().getDiscountService();
        Map<Long, Discount> discountMap = new HashMap<>();
        for (User user : users) {
            long userId = user.getId();
            Optional<Discount> optionalDiscount = discountService.findDiscountByUserId(userId);
            optionalDiscount.ifPresent(discount -> discountMap.put(userId, discount));
        }
        request.setAttribute(DISCOUNT_LIST, discountMap);
    }
}