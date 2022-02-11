package by.vchaikovski.coffeehouse.controller.command.impl.admin;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.service.DiscountService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;


/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Create discount command.
 */

public class CreateDiscountCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        DiscountService discountService = ServiceProvider.getInstance().getDiscountService();
        String discountType = request.getParameter(DISCOUNT_TYPE);
        String discountRate = request.getParameter(DISCOUNT_RATE);
        Map<String, String> discountParameters = new HashMap<>();
        discountParameters.put(DISCOUNT_TYPE, discountType);
        discountParameters.put(DISCOUNT_RATE, discountRate);
        try {
            long discountId = discountService.createDiscount(discountParameters);
            request.setAttribute(DISCOUNT_ID, discountId);
        } catch (ServiceException e) {
            String message = "Create discount command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.USER_INFO_PAGE);
    }
}