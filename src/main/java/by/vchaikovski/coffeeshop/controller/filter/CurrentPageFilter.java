package by.vchaikovski.coffeeshop.controller.filter;

import by.vchaikovski.coffeeshop.controller.command.CommandType;
import by.vchaikovski.coffeeshop.controller.command.RequestParameter;
import by.vchaikovski.coffeeshop.controller.command.SessionParameter;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = "/controller")
public class CurrentPageFilter implements Filter {
    private static final String COMM_DELIM = "?";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DataValidator validator = DataValidatorImpl.getInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String command = httpServletRequest.getParameter(RequestParameter.COMMAND);
        if (validator.isEnumContains(command, CommandType.class) &&
                CommandType.CHANGE_LOCALE != CommandType.valueOf(command.toUpperCase())) {
            String currentPage = httpServletRequest.getServletPath() + COMM_DELIM + httpServletRequest.getQueryString();
            HttpSession session = httpServletRequest.getSession();
            session.setAttribute(SessionParameter.CURRENT_PAGE, currentPage);
        }
        chain.doFilter(request, response);
    }
}