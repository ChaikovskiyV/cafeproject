package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to main command.
 */

public class GoToMainCommand implements BaseCommand {

    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.MAIN_PAGE);
    }
}