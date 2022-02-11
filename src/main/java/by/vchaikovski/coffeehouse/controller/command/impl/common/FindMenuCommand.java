package by.vchaikovski.coffeehouse.controller.command.impl.common;

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
import java.util.List;
import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Find menu command.
 */

public class FindMenuCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        Map<String, String> menuParameters = new HashMap<>();
        String menuName = request.getParameter(MENU_NAME);
        String menuPrice = request.getParameter(MENU_PRICE);
        String menuType = request.getParameter(MENU_TYPE);
        String quantityInStock = request.getParameter(MENU_QUANTITY_IN_STOCK);
        menuParameters.put(MENU_NAME, menuName);
        menuParameters.put(MENU_PRICE, menuPrice);
        menuParameters.put(MENU_TYPE, menuType);
        menuParameters.put(MENU_QUANTITY_IN_STOCK, quantityInStock);
        try {
            List<Menu> menuList = menuService.findMenuBySeveralParameter(menuParameters);
            HttpSession session = request.getSession();
            session.setAttribute(MENU_LIST, menuList);
        } catch (ServiceException e) {
            String message = "Find menu command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.MENU_RESEARCH_PAGE);
    }
}