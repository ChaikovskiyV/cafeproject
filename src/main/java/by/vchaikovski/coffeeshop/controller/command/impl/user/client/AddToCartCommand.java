package by.vchaikovski.coffeeshop.controller.command.impl.user.client;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
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
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.CURRENT_MENU_ID;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.PRODUCT_END_MESSAGE;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.CART;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.CURRENT_PAGE;

public class AddToCartCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String PRODUCT_END_MESS = "This product ends";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String currentMenuId = request.getParameter(CURRENT_MENU_ID);
        Map<Menu, Integer> cart = (Map<Menu, Integer>) session.getAttribute(CART);
        try {
            long menuId = Long.parseLong(currentMenuId);
            Optional<Menu> optionalMenu = menuService.findById(menuId);
            if (optionalMenu.isPresent()) {
                Menu menu = optionalMenu.get();
                int quantityInStock = menu.getQuantityInStock();
                if (quantityInStock > 0) {
                    if (cart.containsKey(menu)) {
                        int currentNumber = cart.get(menu);
                        cart.replace(menu, currentNumber + 1);
                    } else {
                        cart.put(menu, 1);
                    }
                    menuService.updateMenuQuantityInStock(menuId, String.valueOf(quantityInStock - 1));
                    session.setAttribute(CART, cart);
                } else {
                    request.setAttribute(PRODUCT_END_MESSAGE, PRODUCT_END_MESS);
                }
            } else {
                request.setAttribute(PRODUCT_END_MESSAGE, PRODUCT_END_MESS);
            }
        } catch (ServiceException e) {
            String message = "Add to card command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        Router router = new Router(currentPage);
        router.setRouterType(Router.RouterType.REDIRECT);
        return router;
    }
}