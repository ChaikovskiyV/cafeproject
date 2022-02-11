package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.service.MenuService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.CURRENT_MENU_ID;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.CART;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Delete from cart command.
 */

public class DeleteFromCartCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        HttpSession session = request.getSession();
        String currentMenuId = request.getParameter(CURRENT_MENU_ID);
        long menuId = Long.parseLong(currentMenuId);
        Map<Menu, Integer> cart = (Map<Menu, Integer>) session.getAttribute(CART);
        Optional<Menu> menu = cart.keySet().stream()
                .filter(m -> m.getId() == menuId)
                .findFirst();
        if (menu.isPresent()) {
            Menu currentMenu = menu.get();
            int quantityInCart = cart.get(currentMenu);
            try {
                Optional<Menu> optionalMenu = menuService.findById(menuId);
                if (optionalMenu.isPresent()) {
                    menuService.increaseQuantityInStock(menuId, String.valueOf(quantityInCart));
                    cart.remove(currentMenu);
                    session.setAttribute(CART, cart);
                    request.setAttribute(RequestParameter.SHOW_CART, true);
                }
            } catch (ServiceException e) {
                String message = "Delete from card command can't be completed";
                logger.error(message, e);
                throw new CommandException(message, e);
            }
        }
        return new Router(PagePath.CLIENT_HOME_PAGE);
    }
}