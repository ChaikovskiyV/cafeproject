package by.vchaikovski.coffeehouse.controller.command.impl.admin;

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

import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Replenish menu stock command.
 */

public class ReplenishMenuStockCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        String menuId = request.getParameter(MENU_ID);
        String quantity = request.getParameter(MENU_QUANTITY);
        router = new Router(PagePath.MENU_INFO_PAGE);
        try {
            long id = Long.parseLong(menuId);
            boolean isUpdated = menuService.increaseQuantityInStock(id, quantity);
            request.setAttribute(IS_UPDATED_QUANTITY, isUpdated);
            if (isUpdated) {
                HttpSession session = request.getSession();
                Optional<Menu> optionalMenu = menuService.findById(id);
                optionalMenu.ifPresent(menu -> session.setAttribute(MENU, menu));
                router.setRouterType(Router.RouterType.REDIRECT);
            }
        } catch (ServiceException e) {
            String message = "Update quantity in stock command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}