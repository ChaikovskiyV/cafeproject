package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.model.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to card info command.
 */

public class GoToCardInfoCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(SHOW_CARD, true);
        request.removeAttribute(IS_FOUND);
        request.removeAttribute(REGISTER_CARD);
        request.removeAttribute(REGISTERED_CARD);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionParameter.USER);
        User.Role userRole = user != null ? user.getRole() : User.Role.GUEST;
        return switch (userRole) {
            case ADMIN -> new Router(PagePath.ADMIN_HOME_PAGE);
            case BARISTA -> new Router(PagePath.BARISTA_HOME_PAGE);
            case CLIENT -> new Router(PagePath.CLIENT_HOME_PAGE);
            default -> new Router(PagePath.MAIN_PAGE);
        };
    }
}