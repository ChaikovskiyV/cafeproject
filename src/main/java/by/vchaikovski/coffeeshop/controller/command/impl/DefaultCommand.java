package by.vchaikovski.coffeeshop.controller.command.impl;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements BaseCommand {
    @Override
    public Router execute(HttpServletRequest request) {
        Router router = new Router(PagePath.ERROR_400_PAGE);
        router.setRouterType(Router.RouterType.REDIRECT);
        return router;
    }
}