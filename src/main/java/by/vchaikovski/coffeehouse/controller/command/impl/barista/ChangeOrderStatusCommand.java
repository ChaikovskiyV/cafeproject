package by.vchaikovski.coffeehouse.controller.command.impl.barista;

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

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.ORDER_ID;
import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.ORDER_STATUS;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Change order status command.
 */

public class ChangeOrderStatusCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        String orderStatus = request.getParameter(ORDER_STATUS);
        String orderId = request.getParameter(ORDER_ID);
        try {
            orderService.updateOrderStatus(Long.parseLong(orderId), orderStatus);
        } catch (ServiceException e) {
            String message = "Change order status command can't be executed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.ORDER_INFO_PAGE);
    }
}