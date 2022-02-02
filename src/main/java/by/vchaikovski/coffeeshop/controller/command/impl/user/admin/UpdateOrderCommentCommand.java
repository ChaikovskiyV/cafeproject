package by.vchaikovski.coffeeshop.controller.command.impl.user.admin;

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

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class UpdateOrderCommentCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();


    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        String orderId = request.getParameter(ORDER_ID);
        String comment = request.getParameter(COMMENT);
        boolean result;
        try {
            result = orderService.updateComment(Long.parseLong(orderId), comment);
        } catch (ServiceException e) {
            String message = "Update order comment command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        request.setAttribute(COMMENT_CHECK, result);

        return new Router(PagePath.ORDER_INFO_PAGE);
    }
}