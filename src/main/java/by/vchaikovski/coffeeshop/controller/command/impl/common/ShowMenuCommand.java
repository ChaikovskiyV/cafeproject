package by.vchaikovski.coffeeshop.controller.command.impl.common;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.MenuService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.MENU_LIST;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER_ROLE;

public class ShowMenuCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        MenuService service = ServiceProvider.getInstance().getMenuService();
        HttpSession session = request.getSession();
        User.Role userRole = (User.Role) session.getAttribute(USER_ROLE);
        try {
            List<Menu> menuList = service.findAll();
            if (userRole == User.Role.ADMIN || userRole == User.Role.BARISTA) {
                request.setAttribute(MENU_LIST, menuList);
            } else {
                List<Menu> filteredList = menuList.stream()
                        .filter(m -> m.getQuantityInStock() > 0)
                        .collect(Collectors.toList());
                request.setAttribute(MENU_LIST, filteredList);
            }
        } catch (ServiceException e) {
            String message = "Show all menu command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.MENU_PAGE);
    }
}