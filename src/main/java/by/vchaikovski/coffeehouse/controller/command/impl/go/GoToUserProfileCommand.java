package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to user profile command.
 */

public class GoToUserProfileCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(RequestParameter.SHOW_PROFILE, true);
        return new Router(PagePath.CLIENT_HOME_PAGE);
    }
}