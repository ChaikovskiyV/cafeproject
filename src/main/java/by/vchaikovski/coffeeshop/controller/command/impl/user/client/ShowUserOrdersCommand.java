package by.vchaikovski.coffeeshop.controller.command.impl.user.client;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.OrderService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.ORDER_LIST;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER;

public class ShowUserOrdersCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        if (user != null) {
            try {
                List<FoodOrder> orders = orderService.findOrderByUserId(user.getId());
                request.setAttribute(ORDER_LIST, orders);
            } catch (ServiceException e) {
                String message = "Show user orders command can't be completed";
                logger.error(message, e);
                throw new CommandException(message, e);
            }
        }
        return new Router(PagePath.ORDERS_HISTORY_PAGE);
    }
}