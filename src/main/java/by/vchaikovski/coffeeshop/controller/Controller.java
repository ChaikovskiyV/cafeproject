package by.vchaikovski.coffeeshop.controller;

import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.CommandProvider;
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
        String commandName = request.getParameter(COMMAND);
        BaseCommand command = CommandProvider.getInstance().getCommand(commandName);
        Router router = command.execute(request);
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