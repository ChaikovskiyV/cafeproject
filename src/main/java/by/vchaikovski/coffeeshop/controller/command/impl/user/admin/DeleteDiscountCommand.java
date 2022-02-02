package by.vchaikovski.coffeeshop.controller.command.impl.user.admin;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.service.DiscountService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.DISCOUNT_ID;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.IS_DELETED;

public class DeleteDiscountCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        DiscountService discountService = ServiceProvider.getInstance().getDiscountService();
        String discountId = request.getParameter(DISCOUNT_ID);
        try {
            boolean result = discountService.deleteDiscountById(Long.parseLong(discountId));
            request.setAttribute(IS_DELETED, result);
            router = new Router(PagePath.DISCOUNT_RESEARCH_PAGE);
            if (result) {
                router.setRouterType(Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            String message = "Delete discount command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}