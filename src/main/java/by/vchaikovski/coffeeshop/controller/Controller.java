package by.vchaikovski.coffeeshop.controller;

import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.CommandType;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.vchaikovski.coffeeshop.controller.command.PagePath.ERROR_400_PAGE;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.COMMAND;
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

        String commandName = request.getParameter(COMMAND);
        DataValidator validator = DataValidatorImpl.getInstance();
        if (!validator.isEnumContains(commandName, CommandType.class)) {
            logger.error(() -> "Unknown command: " + commandName);
            response.sendRedirect(ERROR_400_PAGE);
            return;
        }
        BaseCommand command = CommandType.valueOf(commandName.toUpperCase()).getCommand();
        Router router = null;
        try {
            router = command.execute(request);
        } catch (CommandException e) {
            String message = "An internal error has occurred";
            logger.error(message, e);
            response.sendError(SC_INTERNAL_SERVER_ERROR, e.getMessage());
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