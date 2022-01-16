package by.vchaikovski.coffeeshop.controller.command.impl.go;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class GoToRegistrationCardCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.REGISTRATION_CARD_PAGE);
    }
}