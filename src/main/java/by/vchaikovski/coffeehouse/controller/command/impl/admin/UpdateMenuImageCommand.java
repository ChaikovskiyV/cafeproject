package by.vchaikovski.coffeehouse.controller.command.impl.admin;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.service.MenuService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import by.vchaikovski.coffeehouse.util.PictureEncoder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Update menu image command.
 */

public class UpdateMenuImageCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        Router router;
        MenuService menuService = ServiceProvider.getInstance().getMenuService();
        String menuId = request.getParameter(MENU_ID);
        String imagePath = request.getParameter(MENU_IMAGE);
        router = new Router(PagePath.MENU_INFO_PAGE);
        try {
            long id = Long.parseLong(menuId);
            boolean isUpdated = menuService.updateMenuImage(id, imagePath);
            request.setAttribute(IS_UPDATED_IMAGE, isUpdated);
            if (isUpdated) {
                HttpSession session = request.getSession();
                Optional<Menu> optionalMenu = menuService.findById(id);
                if (optionalMenu.isPresent()) {
                    Menu menu = optionalMenu.get();
                    Map<Long, String> menuImages = (Map<Long, String>) session.getAttribute(SessionParameter.MENU_IMAGES);
                    PictureEncoder encoder = PictureEncoder.getInstance();
                    menuImages.replace(menu.getId(), encoder.encodePicture(menu.getImage()));
                    session.setAttribute(SessionParameter.MENU_IMAGES, menuImages);
                    session.setAttribute(MENU, menu);
                }
            }
        } catch (ServiceException e) {
            String message = "Update menu image command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }
}