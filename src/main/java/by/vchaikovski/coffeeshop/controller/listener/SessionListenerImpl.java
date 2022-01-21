package by.vchaikovski.coffeeshop.controller.listener;

import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.model.entity.User;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionIdListener;

import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.*;

@WebListener
public class SessionListenerImpl implements HttpSessionIdListener {
    private static final String DEFAULT_LOCALE = "locale_en";
    private static final String DEFAULT_LANGUAGE = "EN";

    @Override
    public void sessionIdChanged(HttpSessionEvent sessionEvent, String oldSessionId) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(CURRENT_PAGE, PagePath.MAIN_PAGE);
        session.setAttribute(USER_ROLE, User.Role.GUEST);
    }
}