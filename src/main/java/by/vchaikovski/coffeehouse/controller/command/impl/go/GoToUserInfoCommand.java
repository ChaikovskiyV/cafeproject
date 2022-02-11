package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Discount;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.model.service.DiscountService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import by.vchaikovski.coffeehouse.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to user info command.
 */

public class GoToUserInfoCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String userId = request.getParameter(RequestParameter.CURRENT_USER_ID);
        HttpSession session = request.getSession();
        UserService userService = ServiceProvider.getInstance().getUserService();
        DiscountService discountService = ServiceProvider.getInstance().getDiscountService();
        try {
            Optional<User> optionalUser = userService.findUserById(Long.parseLong(userId));
            optionalUser.ifPresent(user -> session.setAttribute(RequestParameter.CURRENT_USER, user));
            Optional<Discount> optionalDiscount = discountService.findDiscountByUserId(Long.parseLong(userId));
            optionalDiscount.ifPresent(discount -> session.setAttribute(RequestParameter.DISCOUNT, discount));
        } catch (ServiceException e) {
            String message = "Go to user info command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_INFO_PAGE);
    }
}
