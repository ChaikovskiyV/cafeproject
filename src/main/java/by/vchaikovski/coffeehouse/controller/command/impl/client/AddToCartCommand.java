package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.service.MenuService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.CURRENT_MENU_ID;
import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.PRODUCT_END_MESSAGE;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.CART;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Add to cart command.
 */

public class AddToCartCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String PRODUCT_END_MESS = "This product ends";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        HttpSession session = request.getSession();
        String currentMenuId = request.getParameter(CURRENT_MENU_ID);
        Map<Menu, Integer> cart = (Map<Menu, Integer>) session.getAttribute(CART);
        if (cart == null) {
            cart = new HashMap<>();
        }
        try {
            long menuId = Long.parseLong(currentMenuId);
            Optional<Menu> optionalMenu = menuService.findById(menuId);
            if (optionalMenu.isPresent()) {
                Menu menu = optionalMenu.get();
                int quantityInStock = menu.getQuantityInStock();
                if (quantityInStock > 0) {
                    Optional<Menu> currentMenuOp = cart.keySet().stream().filter(m -> m.getId() == menuId).findFirst();
                    if (currentMenuOp.isPresent()) {
                        Menu currentMenu = currentMenuOp.get();
                        int currentNumber = cart.get(currentMenu);
                        cart.replace(currentMenu, currentNumber + 1);
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
        Router router = new Router(PagePath.MENU_RESEARCH_PAGE);
        router.setRouterType(Router.RouterType.REDIRECT);
        return router;
    }
}