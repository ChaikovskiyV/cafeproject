package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to home command.
 */

public class GoToHomeCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        DataValidator validator = DataValidatorImpl.getInstance();
        HttpSession session = request.getSession();
        String userRole = (String) session.getAttribute(RequestParameter.USER_ROLE);
        User.Role role = validator.isEnumContains(userRole, User.Role.class)
                ? User.Role.valueOf(userRole.toUpperCase()) : User.Role.GUEST;
        return switch (role) {
            case ADMIN -> new Router(PagePath.ADMIN_HOME_PAGE);
            case BARISTA -> new Router(PagePath.BARISTA_HOME_PAGE);
            case CLIENT -> new Router(PagePath.CLIENT_HOME_PAGE);
            default -> new Router(PagePath.MAIN_PAGE);
        };
    }
}