package by.vchaikovski.coffeehouse.controller.command.impl.barista;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.*;
import by.vchaikovski.coffeehouse.model.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER_ID;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Find order command.
 */

public class FindOrderCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        UserService userService = ServiceProvider.getInstance().getUserService();
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
            if (email != null) {
                optionalUser = userService.findUserByEmail(email);
            } else if (phoneNumber != null) {
                optionalUser = userService.findUserByPhoneNumber(phoneNumber);
            }
            optionalUser.ifPresent(user -> orderParameters.put(USER_ID, String.valueOf(user.getId())));
            List<FoodOrder> orders = orderService.findOrderBySeveralParameters(orderParameters);
            request.setAttribute(RESULT, !orders.isEmpty());
            request.setAttribute(ORDER_LIST, orders);
            if (!orders.isEmpty()) {
                addMapsToRequest(orders, request);
            }
        } catch (ServiceException e) {
            String message = "Find order command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.ORDER_RESEARCH_PAGE);
    }

    private void addMapsToRequest(List<FoodOrder> orders, HttpServletRequest request) throws ServiceException {
        BillService billService = ServiceProvider.getInstance().getBillService();
        DeliveryService deliveryService = ServiceProvider.getInstance().getDeliveryService();
        UserService userService = ServiceProvider.getInstance().getUserService();
        Map<Long, User> users = new HashMap<>();
        Map<Long, Bill> bills = new HashMap<>();
        Map<Long, Delivery> deliveries = new HashMap<>();
        Map<Long, AddressDelivery> addressMap = new HashMap<>();
        for (FoodOrder order : orders) {
            long userId = order.getUserId();
            Optional<User> optionalUs = userService.findUserById(userId);
            Optional<Bill> optionalBill = billService.findBillById(order.getBillId());
            Optional<Delivery> optionalDelivery = deliveryService.findDeliveryById(order.getDeliveryId());
            Optional<AddressDelivery> optionalAddress = Optional.empty();
            if (optionalDelivery.isPresent()) {
                deliveries.put(userId, optionalDelivery.get());
                optionalAddress = deliveryService.findAddressById(optionalDelivery.get().getAddressId());
            }
            optionalBill.ifPresent(bill -> bills.put(userId, bill));
            optionalUs.ifPresent(user -> users.put(userId, optionalUs.get()));
            optionalAddress.ifPresent(address -> addressMap.put(userId, address));
        }
        request.setAttribute(USER_LIST, users);
        request.setAttribute(BILL_LIST, bills);
        request.setAttribute(DELIVERY_LIST, deliveries);
        request.setAttribute(ADDRESS_LIST, addressMap);
    }
}