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
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Update user discount command.
 */

public class UpdateUserDiscountCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        boolean result = false;
        UserService userService = ServiceProvider.getInstance().getUserService();
        String userId = request.getParameter(USER_ID_REQ);
        String discountType = request.getParameter(DISCOUNT_TYPE);
        String discountRate = request.getParameter(DISCOUNT_RATE);
        try {
            Optional<User> optionalUser = userService.findUserById(Long.parseLong(userId));
            if (optionalUser.isPresent()) {
                DiscountService discountService = ServiceProvider.getInstance().getDiscountService();
                long discountId = optionalUser.get().getDiscountId();
                if (discountType != null && discountRate != null) {
                    result = discountService.updateDiscountType(discountId, discountType) &&
                            discountService.updateDiscountRate(discountId, discountRate);
                } else if (discountType != null) {
                    result = discountService.updateDiscountType(discountId, discountType);
                } else if (discountRate != null) {
                    result = discountService.updateDiscountRate(discountId, discountRate);
                }
                if(result) {
                    HttpSession session = request.getSession();
                    Optional<Discount> optionalDiscount = discountService.findDiscountsById(discountId);
                    optionalDiscount.ifPresent(discount -> session.setAttribute(DISCOUNT, discount));
                }
            }
            request.setAttribute(IS_UPDATED_DISCOUNT, result);
        } catch (ServiceException e) {
            String message = "Update user discount command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_INFO_PAGE);
    }
}