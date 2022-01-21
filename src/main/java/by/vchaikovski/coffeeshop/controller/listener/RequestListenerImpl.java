package by.vchaikovski.coffeeshop.controller.listener;

import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;
import java.util.Set;

import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.*;

@WebListener
public class RequestListenerImpl implements ServletRequestListener {
    private static final Set<String> attributes =
            Set.of(USER_PARAMETERS, CARD_PARAMETERS, CARD_ID, IS_PAYED);

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        HttpSession session = request.getSession();
        Enumeration<String> attributesName = session.getAttributeNames();
        attributesName.asIterator().forEachRemaining(a -> {
            if (attributes.contains(a)) {
                session.removeAttribute(a);
            }
        });
    }
}