package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.service.OrderService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * The type Update order evaluation command.
 * @project Coffeehouse
 */

public class UpdateOrderEvaluationCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        String orderId = request.getParameter(ORDER_ID);
        String evaluation = request.getParameter(EVALUATION);
        boolean result;
        try {
            result = orderService.updateEvaluation(Long.parseLong(orderId), evaluation);
        } catch (ServiceException e) {
            String message = "Update order evaluation command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        request.setAttribute(EVALUATION_CHECK, result);

        return new Router(PagePath.ORDER_INFO_PAGE);
    }
}