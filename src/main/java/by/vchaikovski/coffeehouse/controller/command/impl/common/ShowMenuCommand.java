package by.vchaikovski.coffeehouse.controller.command.impl.common;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.model.service.MenuService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import by.vchaikovski.coffeehouse.util.PictureEncoder;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.MENU_LIST;
import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.RESULT;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.MENU_IMAGES;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER_ROLE;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Show menu command.
 */

public class ShowMenuCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService service = ServiceProvider.getInstance().getMenuService();
        HttpSession session = request.getSession();
        String role = (String) session.getAttribute(USER_ROLE);
        DataValidator validator = DataValidatorImpl.getInstance();
        User.Role userRole = validator.isEnumContains(role, User.Role.class)
                ? User.Role.valueOf(role.toUpperCase()) : User.Role.GUEST;
        try {
            List<Menu> menuList = service.findAll();
            if (userRole != User.Role.ADMIN && userRole != User.Role.BARISTA) {
                menuList = menuList.stream()
                        .filter(m -> m.getQuantityInStock() > 0)
                        .toList();
            }

            request.setAttribute(RESULT, !menuList.isEmpty());
            session.setAttribute(MENU_LIST, menuList);
            session.setAttribute(MENU_IMAGES, fillImageMap(menuList));
        } catch (ServiceException e) {
            String message = "Show all menu command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.MENU_RESEARCH_PAGE);
    }

    private Map<Long, String> fillImageMap(List<Menu> menuList) {
        Map<Long, String> imageMap = new HashMap<>();
        PictureEncoder encoder = PictureEncoder.getInstance();
        menuList.forEach(m -> imageMap.put(m.getId(), encoder.encodePicture(m.getImage())));
        return imageMap;
    }
}