package by.vchaikovski.coffeehouse.controller.command.impl.client;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Bill;
import by.vchaikovski.coffeehouse.model.entity.FoodOrder;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.model.service.BillService;
import by.vchaikovski.coffeehouse.model.service.OrderService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Create order command.
 */

public class CreateOrderCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();
    private static final String EMPTY_CART_MESS = "Your cart is empty";

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        HttpSession session = request.getSession();
        session.removeAttribute(ORDER_IS_CREATED);
        Map<Menu, Integer> cart = (Map<Menu, Integer>) session.getAttribute(CART);
        User user = (User) session.getAttribute(USER);
        if (cart == null || cart.isEmpty()) {
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
        Router router = new Router(PagePath.ORDER_CREATION_PAGE);
        try {
            orderId = orderService.createOrder(orderParameters, cart);
            session.setAttribute(ORDER_IS_CREATED, orderId > 0);
            if (orderId > 0) {
                BillService billService = ServiceProvider.getInstance().getBillService();
                Optional<FoodOrder> optionalFoodOrder = orderService.findOrderById(orderId);
                if (optionalFoodOrder.isPresent()) {
                    FoodOrder order = optionalFoodOrder.get();
                    session.setAttribute(CURRENT_ORDER, order);
                    Optional<Bill> optionalBill = billService.findBillById(order.getBillId());
                    optionalBill.ifPresent(bill -> session.setAttribute(BILL, bill));
                }
                session.setAttribute(ORDER_PARAMETERS, orderParameters);
                router.setRouterType(Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(ORDER_PARAMETERS, orderParameters);
                fillWrongParam(orderParameters, request);
            }
        } catch (ServiceException e) {
            String message = "Create order command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return router;
    }

    private void fillWrongParam(Map<String, String> orderParameters, HttpServletRequest request) {
        for (Map.Entry<String, String> entry : orderParameters.entrySet()) {
            String paramName = entry.getKey();
            String paramMeaning = entry.getValue();
            if (paramMeaning != null && paramMeaning.equals(WRONG_MEANING)) {
                switch (paramName) {
                    case DELIVERY_TYPE -> request.setAttribute(DELIVERY_TYPE_CHECK, paramMeaning);
                    case DELIVERY_TIME -> request.setAttribute(DELIVERY_TIME_CHECK, paramMeaning);
                    case STREET -> request.setAttribute(STREET_CHECK, paramMeaning);
                    case HOUSE_NUMBER -> request.setAttribute(HOUSE_NUMBER_CHECK, paramMeaning);
                    case BUILDING_NUMBER -> request.setAttribute(BUILDING_NUMBER_CHECK, paramMeaning);
                    case FLAT_NUMBER -> request.setAttribute(FLAT_NUMBER_CHECK, paramMeaning);
                    case COMMENT -> request.setAttribute(COMMENT_CHECK, paramMeaning);
                    default -> logger.debug(() -> "Uncheckable parameter: " + paramName);
                }
            }
        }
    }
}