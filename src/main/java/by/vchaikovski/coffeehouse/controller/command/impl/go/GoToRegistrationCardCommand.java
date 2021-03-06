package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to registration card command.
 */

public class GoToRegistrationCardCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(SessionParameter.CARD_ID);
        request.setAttribute(RequestParameter.REGISTER_CARD, true);
        return new Router(PagePath.CARD_INFO_PAGE);
    }
}