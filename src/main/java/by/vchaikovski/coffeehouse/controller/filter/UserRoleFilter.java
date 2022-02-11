package by.vchaikovski.coffeehouse.controller.filter;

import by.vchaikovski.coffeehouse.controller.command.CommandType;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
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
import java.util.List;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.COMMAND;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER_ROLE;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type User role filter.
 */

@WebFilter(urlPatterns = "/controller")
public class UserRoleFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        DataValidator validator = DataValidatorImpl.getInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String commandName = httpServletRequest.getParameter(COMMAND);
        String userRole = (String) session.getAttribute(USER_ROLE);

        User.Role role = validator.isEnumContains(userRole, User.Role.class)
                ? User.Role.valueOf(userRole.toUpperCase()) : User.Role.GUEST;

        if (validator.isEnumContains(commandName, CommandType.class)) {
            List<User.Role> allRoles = CommandType.valueOf(commandName.toUpperCase()).getUsersRole();
            if (allRoles.contains(role)) {
                chain.doFilter(request, response);
            } else {
                String message = "The command " + commandName + " is not allowed for " + role;
                logger.debug(message);
                httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, message);
            }
        } else if (commandName != null) {
            String message = "Unknown command: " + commandName;
            logger.debug(message);
            httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
        } else {
            httpServletResponse.sendRedirect(PagePath.INDEX_PAGE);
        }
    }
}