package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.CART;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Clear cart command.
 */

public class ClearCartCommand implements BaseCommand {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Map<Menu, Integer> cart = (Map<Menu, Integer>) session.getAttribute(CART);
        if (cart != null && !cart.isEmpty()) {
            cart.clear();
            session.setAttribute(CART, cart);
        }
        return new Router(PagePath.CLIENT_HOME_PAGE);
    }
}