package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Sign out command.
 */

public class SignOutCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        request.getSession().invalidate();
        logger.info("Session was ended.");
        Router router = new Router(PagePath.MAIN_PAGE);
        router.setRouterType(Router.RouterType.REDIRECT);
        return router;
    }
}