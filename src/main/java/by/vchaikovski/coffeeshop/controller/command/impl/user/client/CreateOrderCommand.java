package by.vchaikovski.coffeeshop.controller.command.impl.user.client;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.controller.command.BaseCommand;
import by.vchaikovski.coffeeshop.controller.command.PagePath;
import by.vchaikovski.coffeeshop.exception.CommandException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.Bill;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.model.service.OrderService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.*;

public class CreateOrderCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_CART_MESS = "Your cart is empty";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        HttpSession session = request.getSession();
        Map<Menu, Integer> cart = (Map<Menu, Integer>) session.getAttribute(CART);
        User user = (User) session.getAttribute(USER);
        if (cart.isEmpty()) {
            request.setAttribute(EMPTY_CART_MESSAGE, EMPTY_CART_MESS);
            return new Router(PagePath.ORDER_CREATION_PAGE);
        }
        String deliveryType = request.getParameter(DELIVERY_TYPE);
        String deliveryTime = request.getParameter(DELIVERY_TIME);
        String streetName = request.getParameter(STREET);
        String houseNumber = request.getParameter(HOUSE_NUMBER);
        String buildingNumber = request.getParameter(BUILDING_NUMBER);
        String flatNumber = request.getParameter(FLAT_NUMBER);
        String comment = request.getParameter(COMMENT);

        Map<String, String> orderParameters = new HashMap<>();
        orderParameters.put(BILL_STATUS, Bill.BillStatus.NOT_PAID.name());
        orderParameters.put(DISCOUNT_ID, String.valueOf(user.getDiscountId()));
        orderParameters.put(DELIVERY_TYPE, deliveryType);
        orderParameters.put(DELIVERY_TIME, deliveryTime);
        orderParameters.put(STREET, streetName);
        orderParameters.put(HOUSE_NUMBER, houseNumber);
        orderParameters.put(BUILDING_NUMBER, buildingNumber);
        orderParameters.put(FLAT_NUMBER, flatNumber);
        orderParameters.put(COMMENT, comment);
        orderParameters.put(ORDER_STATUS, FoodOrder.OrderStatus.WAITING.name());
        orderParameters.put(USER_ID, String.valueOf(user.getId()));
        long orderId;
        try {
            orderId = orderService.createOrder(orderParameters, cart);
            if (orderId > 0) {
                Optional<FoodOrder> optionalFoodOrder = orderService.findOrderById(orderId);
                optionalFoodOrder.ifPresent(order -> session.setAttribute(ORDER, order));
                Router router = new Router(PagePath.ORDER_INFO_PAGE);
                router.setRouterType(Router.RouterType.REDIRECT);
                return router;
            } else {
                request.setAttribute(ORDER_PARAMETERS, orderParameters);
                fillWrongParam(orderParameters, request);
            }
        } catch (ServiceException e) {
            String message = "Create order command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.ORDER_CREATION_PAGE);
    }

    private void fillWrongParam(Map<String, String> orderParameters, HttpServletRequest request) {
        for (Map.Entry<String, String> entry : orderParameters.entrySet()) {
            String paramName = entry.getKey();
            String paramMeaning = entry.getValue();
            if (paramMeaning.equals(WRONG_MEANING)) {
                switch (paramName) {
                    case DELIVERY_TYPE -> request.setAttribute(DELIVERY_TYPE_CHECK, paramMeaning);
                    case DELIVERY_TIME -> request.setAttribute(DELIVERY_TIME_CHECK, paramMeaning);
                    case STREET -> request.setAttribute(STREET_CHECK, paramMeaning);
                    case HOUSE_NUMBER -> request.setAttribute(HOUSE_NUMBER_CHECK, paramMeaning);
                    case BUILDING_NUMBER -> request.setAttribute(BUILDING_NUMBER_CHECK, paramMeaning);
                    case FLAT_NUMBER -> request.setAttribute(FIRST_NAME_CHECK, paramMeaning);
                    case COMMENT -> request.setAttribute(COMMENT_CHECK, paramMeaning);
                    default -> logger.debug(() -> "Uncheckable parameter: " + paramName);
                }
            }
        }
    }
}