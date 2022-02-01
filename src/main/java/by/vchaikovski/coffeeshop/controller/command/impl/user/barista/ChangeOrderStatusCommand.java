package by.vchaikovski.coffeeshop.controller.command.impl.user.barista;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.service.OrderService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.ORDER_ID;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.ORDER_STATUS;

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
        return new Router(PagePath.ORDERS_PROCESSING_PAGE);
    }
}