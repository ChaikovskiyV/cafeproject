package by.vchaikovski.coffeehouse.controller.command.impl.admin;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.service.MenuService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Update menu description command.
 */

public class UpdateMenuDescriptionCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        String menuId = request.getParameter(MENU_ID);
        String menuDescription = request.getParameter(MENU_DESCRIPTION);
        try {
            boolean isUpdated = menuService.updateMenuDescription(Long.parseLong(menuId), menuDescription);
            request.setAttribute(IS_UPDATED_DESCRIPTION, isUpdated);
            if (!isUpdated) {
                request.setAttribute(MENU_DESCRIPTION_CHECK, WRONG_MEANING);
            }
        } catch (ServiceException e) {
            String message = "Update menu description command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.MENU_INFO_PAGE);
    }
}