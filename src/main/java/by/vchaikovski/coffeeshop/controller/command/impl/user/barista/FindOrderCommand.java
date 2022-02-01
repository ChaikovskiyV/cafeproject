package by.vchaikovski.coffeeshop.controller.command.impl.user.barista;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.OrderService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.model.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER_ID;

public class FindOrderCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        Map<String, String> orderParameters = new HashMap<>();
        String billStatus = request.getParameter(BILL_STATUS);
        String paymentDate = request.getParameter(PAYMENT_DATE);
        String creationDate = request.getParameter(CREATION_DATE);
        String deliveryTime = request.getParameter(DELIVERY_TIME);
        String deliveryType = request.getParameter(DELIVERY_TYPE);
        String orderStatus = request.getParameter(ORDER_STATUS);
        String email = request.getParameter(EMAIL);
        String phoneNumber = request.getParameter(PHONE_NUMBER);
        orderParameters.put(BILL_STATUS, billStatus);
        orderParameters.put(PAYMENT_DATE, paymentDate);
        orderParameters.put(CREATION_DATE, creationDate);
        orderParameters.put(DELIVERY_TIME, deliveryTime);
        orderParameters.put(DELIVERY_TYPE, deliveryType);
        orderParameters.put(ORDER_STATUS, orderStatus);
        try {
            Optional<User> optionalUser = Optional.empty();
            UserService userService = ServiceProvider.getInstance().getUserService();
            if (email != null) {
                optionalUser = userService.findUserByEmail(email);
            } else if (phoneNumber != null) {
                optionalUser = userService.findUserByPhoneNumber(phoneNumber);
            }
            optionalUser.ifPresent(user -> orderParameters.put(USER_ID, String.valueOf(user.getId())));
            List<FoodOrder> orders = orderService.findOrderBySeveralParameters(orderParameters);
            request.setAttribute(ORDER_LIST, orders);
        } catch (ServiceException e) {
            String message = "Find order command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.ORDER_RESEARCH_PAGE);
    }
}