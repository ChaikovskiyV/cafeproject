package by.vchaikovski.coffeeshop.controller.command.impl.user.client;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.model.service.MenuService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.CURRENT_MENU_ID;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.PRODUCT_END_MESSAGE;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.CART;

public class IncreaseQuantityInCartCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String PRODUCT_END_MESS = "This product ends";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        HttpSession session = request.getSession();
        String currentMenuId = request.getParameter(CURRENT_MENU_ID);
        Map<Menu, Integer> cart = (Map<Menu, Integer>) session.getAttribute(CART);
        try {
            long menuId = Long.parseLong(currentMenuId);
            for (Map.Entry<Menu, Integer> entry : cart.entrySet()) {
                Menu menu = entry.getKey();
                int quantityInCart = entry.getValue();
                if (menu.getId() == menuId) {
                    int stepIncrease = 1;
                    boolean isPresent = menuService.reduceQuantityInStock(menuId, String.valueOf(stepIncrease));
                    if (isPresent) {
                        cart.replace(menu, quantityInCart + stepIncrease);
                    } else {
                        request.setAttribute(PRODUCT_END_MESSAGE, PRODUCT_END_MESS);
                    }
                }
            }
            session.setAttribute(CART, cart);
        } catch (ServiceException e) {
            String message = "Add to card command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        Router router = new Router(PagePath.CART_PAGE);
        router.setRouterType(Router.RouterType.REDIRECT);
        return router;
    }
}