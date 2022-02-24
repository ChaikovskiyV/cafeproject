package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
import by.vchaikovski.coffeehouse.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to create order command.
 */
public class GoToCreateOrderCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionParameter.ORDER_IS_CREATED);
        return new Router(PagePath.ORDER_CREATION_PAGE);
    }
}