package by.vchaikovski.coffeehouse.controller.command.impl.common;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Change locale command.
 */

public class ChangeLocaleCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String LANGUAGE_EN = "EN";
    private static final String LANGUAGE_RU = "RU";
    private static final String LOCALE_EN = "en_EN";
    private static final String LOCALE_RU = "ru_RU";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String language = request.getParameter(LANGUAGE);
        switch (language) {
            case LANGUAGE_EN -> session.setAttribute(LOCALE, LOCALE_EN);
            case LANGUAGE_RU -> session.setAttribute(LOCALE, LOCALE_RU);
            default -> logger.debug("Unknown language");
        }
        session.setAttribute(LANGUAGE, language);
        logger.debug("Locale was changed");
        Router router = new Router(currentPage);
        router.setRouterType(Router.RouterType.REDIRECT);

        return router;
    }
}