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
 * The type Go to find bank card command.
 */
public class GoToFindBankCardCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        request.setAttribute(RequestParameter.FIND_CARD, true);
        return new Router(PagePath.CARD_INFO_PAGE);
    }
}