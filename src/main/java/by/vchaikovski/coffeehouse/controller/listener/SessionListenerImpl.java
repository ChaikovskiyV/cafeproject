package by.vchaikovski.coffeehouse.controller.listener;

import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.model.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionIdListener;

import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Session listener.
 */

@WebListener
public class SessionListenerImpl implements HttpSessionIdListener {
    private static final String DEFAULT_LOCALE = "en_EN";
    private static final String DEFAULT_LANGUAGE = "EN";

    @Override
    public void sessionIdChanged(HttpSessionEvent sessionEvent, String oldSessionId) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(CURRENT_PAGE, PagePath.INDEX_PAGE);
        session.setAttribute(USER_ROLE, User.Role.GUEST.name());
    }
}