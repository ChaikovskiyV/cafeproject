package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to about command.
 */

public class GoToAboutCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(PagePath.ABOUT_PAGE);
    }
}