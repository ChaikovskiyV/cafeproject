package by.vchaikovski.coffeeshop.controller;

import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.CommandType;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.util.UserRoleChecker;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.vchaikovski.coffeeshop.controller.command.PagePath.ERROR_400_PAGE;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.COMMAND;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.ROLE;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@WebServlet(name = "controller", urlPatterns = {"/controller"})
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("txt/html");

        Router router = null;
        String commandName = request.getParameter(COMMAND);
        HttpSession session = request.getSession();
        User.Role userRole = (User.Role) session.getAttribute(ROLE);
        UserRoleChecker roleChecker = UserRoleChecker.getInstance();
        if (roleChecker.isAllowed(userRole, commandName)) {
            BaseCommand command = CommandType.valueOf(commandName.toUpperCase()).getCommand();
            try {
                router = command.execute(request);
            } catch (CommandException e) {
                String message = "An internal error has occurred";
                logger.error(message, e);
                response.sendError(SC_INTERNAL_SERVER_ERROR, e.getMessage());
            }
        } else {
            logger.error(() -> "The command " + commandName + " is not allowed for userRole " + userRole);
            router = new Router(ERROR_400_PAGE);
            router.setRouterType(Router.RouterType.REDIRECT);
        }
        if (router != null) {
            Router.RouterType routerType = router.getRouterType();
            switch (routerType) {
                case FORWARD -> request.getRequestDispatcher(router.getPagePath()).forward(request, response);
                case REDIRECT -> response.sendRedirect(router.getPagePath());
                default -> {
                    logger.error(() -> "Unknown router type: " + routerType);
                    response.sendRedirect(ERROR_400_PAGE);
                }
            }
        }
    }
}