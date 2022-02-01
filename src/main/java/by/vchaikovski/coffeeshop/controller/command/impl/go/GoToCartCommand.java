package by.vchaikovski.coffeeshop.controller.command.impl.go;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class GoToCartCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(PagePath.CART_PAGE);
    }
}
