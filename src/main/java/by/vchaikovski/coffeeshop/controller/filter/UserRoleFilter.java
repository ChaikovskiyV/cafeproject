package by.vchaikovski.coffeeshop.controller.filter;

import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.util.UserRoleChecker;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.COMMAND;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER_ROLE;

@WebFilter(urlPatterns = "/controller")
public class UserRoleFilter implements Filter {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        UserRoleChecker roleChecker = UserRoleChecker.getInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        String commandName = httpServletRequest.getParameter(COMMAND);
        User.Role userRole = (User.Role) session.getAttribute(USER_ROLE);
        if (!roleChecker.isAllowed(userRole, commandName)) {
            String notAvailableActionMessage = "The command " + commandName + " is not allowed for user role " + userRole;
            logger.warn(notAvailableActionMessage);
            httpServletResponse.sendRedirect(PagePath.NOT_AVAILABLE_ACTION_PAGE);
        }
    }
}