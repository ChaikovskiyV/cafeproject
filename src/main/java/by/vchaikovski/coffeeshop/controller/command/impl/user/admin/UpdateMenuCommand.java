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

import java.util.HashMap;
import java.util.Map;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class UpdateMenuCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        String menuId = request.getParameter(MENU_ID);
        String menuName = request.getParameter(MENU_NAME);
        String menuType = request.getParameter(MENU_TYPE);
        String description = request.getParameter(MENU_DESCRIPTION);
        String price = request.getParameter(MENU_PRICE);
        String quantityInStock = request.getParameter(MENU_QUANTITY_IN_STOCK);
        String imagePath = request.getParameter(MENU_IMAGE);

        Map<String, String> menuParameters = new HashMap<>();
        menuParameters.put(MENU_NAME, menuName);
        menuParameters.put(MENU_TYPE, menuType);
        menuParameters.put(MENU_DESCRIPTION, description);
        menuParameters.put(MENU_PRICE, price);
        menuParameters.put(MENU_QUANTITY_IN_STOCK, quantityInStock);
        menuParameters.put(MENU_IMAGE, imagePath);
        try {
            boolean isUpdated = menuService.updateMenu(Long.parseLong(menuId), menuParameters);
            request.setAttribute(IS_UPDATED_MENU, isUpdated);
            if (!isUpdated) {
                fillWrongAttr(menuParameters, request);
            }
        } catch (ServiceException e) {
            String message = "Update user discount command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.MENU_INFO_PAGE);
    }

    private void fillWrongAttr(Map<String, String> menuParameters, HttpServletRequest request) {
        for (Map.Entry<String, String> entry : menuParameters.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value.equals(WRONG_MEANING)) {
                switch (key) {
                    case MENU_NAME -> request.setAttribute(MENU_NAME_CHECK, value);
                    case MENU_TYPE -> request.setAttribute(MENU_TYPE_CHECK, value);
                    case MENU_DESCRIPTION -> request.setAttribute(MENU_DESCRIPTION_CHECK, value);
                    case MENU_PRICE -> request.setAttribute(MENU_PRICE_CHECK, value);
                    case MENU_QUANTITY_IN_STOCK -> request.setAttribute(MENU_QUANTITY_IN_STOCK_CHECK, value);
                    case MENU_IMAGE -> request.setAttribute(MENU_IMAGE_CHECK, value);
                    default -> logger.debug(() -> "Unknown parameter: " + key);
                }
            }
        }
    }
}