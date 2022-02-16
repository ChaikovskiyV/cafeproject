package by.vchaikovski.coffeehouse.controller.filter;

import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static by.vchaikovski.coffeehouse.controller.command.PagePath.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Page filter.
 */

@WebFilter(urlPatterns = {"/jsp/*"})
public class PageFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();
    private Set<String> guestPages;
    private Set<String> adminPages;
    private Set<String> clientPages;
    private Set<String> baristaPages;
    private Set<String> allPages;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        guestPages = Set.of(
                SIGN_IN_PAGE,
                REGISTRATION_PAGE,
                MAIN_PAGE,
                ABOUT_PAGE,
                MENU_RESEARCH_PAGE);
        clientPages = Set.of(
                CLIENT_HOME_PAGE,
                ORDER_CREATION_PAGE,
                ORDER_INFO_PAGE,
                MAIN_PAGE,
                ABOUT_PAGE,
                MENU_RESEARCH_PAGE,
                CARD_INFO_PAGE);
        baristaPages = Set.of(
                BARISTA_HOME_PAGE,
                ORDER_INFO_PAGE,
                ORDER_RESEARCH_PAGE,
                MAIN_PAGE,
                ABOUT_PAGE,
                MENU_RESEARCH_PAGE,
                CARD_INFO_PAGE);
        adminPages = Set.of(ADMIN_HOME_PAGE,
                MENU_CREATION_PAGE,
                USER_RESEARCH_PAGE,
                USER_INFO_PAGE,
                ORDER_RESEARCH_PAGE,
                ORDER_INFO_PAGE,
                MENU_INFO_PAGE,
                MAIN_PAGE,
                ABOUT_PAGE,
                MENU_RESEARCH_PAGE,
                CARD_INFO_PAGE);
        allPages = new HashSet<>();
        allPages.addAll(guestPages);
        allPages.addAll(clientPages);
        allPages.addAll(baristaPages);
        allPages.addAll(adminPages);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String servletPath = httpServletRequest.getServletPath();
        boolean isPathExist = allPages.stream().anyMatch(servletPath::contains);

        if (isPathExist) {
            HttpSession session = ((HttpServletRequest) request).getSession();
            String userRole = (String) session.getAttribute(SessionParameter.USER_ROLE);
            DataValidator validator = DataValidatorImpl.getInstance();
            User.Role role = validator.isEnumContains(userRole, User.Role.class)
                    ? User.Role.valueOf(userRole.toUpperCase()) : User.Role.GUEST;
            boolean isAllowed = switch (role) {
                case ADMIN -> adminPages.stream().anyMatch(servletPath::contains);
                case BARISTA -> baristaPages.stream().anyMatch(servletPath::contains);
                case CLIENT -> clientPages.stream().anyMatch(servletPath::contains);
                default -> guestPages.stream().anyMatch(servletPath::contains);
            };
            if (!isAllowed) {
                String message = "ServletPath " + servletPath + " is not allowed for " + role;
                logger.debug(message);
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, message);
            }
        } else {
            String message = "ServletPath " + servletPath + " is not found";
            logger.debug(message);
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, message);
        }
        chain.doFilter(request, response);
    }
}