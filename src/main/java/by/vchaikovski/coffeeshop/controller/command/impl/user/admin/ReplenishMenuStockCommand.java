package by.vchaikovski.coffeeshop.controller.command.impl.user.admin;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.service.MenuService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class ReplenishMenuStockCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        String menuId = request.getParameter(MENU_ID);
        String quantity = request.getParameter(MENU_QUANTITY);
        try {
            boolean result = menuService.increaseQuantityInStock(Long.parseLong(menuId), quantity);
            if (result) {
                router = new Router(PagePath.MENU_INFO_PAGE);
                router.setRouterType(Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(MENU_QUANTITY_CHECK, WRONG_MEANING);
                router = new Router(PagePath.USER_INFO_PAGE);
            }
        } catch (ServiceException e) {
            String message = "Update quantity in stock command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}