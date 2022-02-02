package by.vchaikovski.coffeeshop.controller.command.impl.user.admin;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.DiscountService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

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
            }
        } catch (ServiceException e) {
            String message = "Update user discount command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        request.setAttribute(IS_UPDATED_DISCOUNT, result);

        return new Router(PagePath.USER_INFO_PAGE);
    }
}