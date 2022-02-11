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
 * The type Go to cart command.
 */
public class GoToCartCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(RequestParameter.SHOW_CART, true);
        return new Router(PagePath.CLIENT_HOME_PAGE);
    }
}