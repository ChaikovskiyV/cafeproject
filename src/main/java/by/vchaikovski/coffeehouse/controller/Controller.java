package by.vchaikovski.coffeehouse.controller;

import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.CommandType;
import by.vchaikovski.coffeehouse.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.COMMAND;
import static jakarta.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Controller.
 */

@WebServlet(name = "controller", urlPatterns = {"/controller"})
@MultipartConfig(fileSizeThreshold = 2048 * 2048, maxFileSize = 2048 * 2048, maxRequestSize = 2048 * 2048)
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
        BaseCommand command = CommandType.valueOf(commandName.toUpperCase()).getCommand();
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
                case REDIRECT -> response.sendRedirect(request.getContextPath() + router.getPagePath());
                default -> {
                    logger.error(() -> "Unknown router type: " + routerType);
                    response.sendError(SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }
}