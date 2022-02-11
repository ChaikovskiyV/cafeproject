package by.vchaikovski.coffeehouse.controller.filter;

import by.vchaikovski.coffeehouse.controller.command.CommandType;
import by.vchaikovski.coffeehouse.controller.command.RequestParameter;
import by.vchaikovski.coffeehouse.controller.command.SessionParameter;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Current page filter.
 */

@WebFilter(urlPatterns = {"/controller", "/jsp/*"})
public class CurrentPageFilter implements Filter {
    private static final String COMM_DELIM = "?";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DataValidator validator = DataValidatorImpl.getInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpSession session = httpServletRequest.getSession();
        String command = httpServletRequest.getParameter(RequestParameter.COMMAND);
        if (validator.isEnumContains(command, CommandType.class) &&
                CommandType.CHANGE_LOCALE != CommandType.valueOf(command.toUpperCase())) {
            String currentPage = httpServletRequest.getServletPath() + COMM_DELIM + httpServletRequest.getQueryString();
            session.setAttribute(SessionParameter.CURRENT_PAGE, currentPage);
        } else {
            session.setAttribute(SessionParameter.CURRENT_PAGE, httpServletRequest.getServletPath());
        }
        chain.doFilter(request, response);
    }
}