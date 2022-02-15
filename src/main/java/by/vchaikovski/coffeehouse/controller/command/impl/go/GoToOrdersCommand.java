package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.*;
import by.vchaikovski.coffeehouse.model.service.BillService;
import by.vchaikovski.coffeehouse.model.service.DeliveryService;
import by.vchaikovski.coffeehouse.model.service.OrderService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to orders command.
 */

public class GoToOrdersCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        try {
            List<FoodOrder> orderList = orderService.findOrderByUserId(user.getId());
            request.setAttribute(ORDER_LIST, orderList);
            request.setAttribute(RESULT, !orderList.isEmpty());
            if (!orderList.isEmpty()) {
                addMapsToRequest(orderList, request);
            }
        } catch (ServiceException e) {
            String message = "Go to orders command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.CLIENT_HOME_PAGE);
    }

    private void addMapsToRequest(List<FoodOrder> orders, HttpServletRequest request) throws ServiceException {
        BillService billService = ServiceProvider.getInstance().getBillService();
        DeliveryService deliveryService = ServiceProvider.getInstance().getDeliveryService();
        Map<Long, Bill> bills = new HashMap<>();
        Map<Long, Delivery> deliveries = new HashMap<>();
        Map<Long, AddressDelivery> addressMap = new HashMap<>();
        for (FoodOrder order : orders) {
            long orderId = order.getId();
            Optional<Bill> optionalBill = billService.findBillById(order.getBillId());
            optionalBill.ifPresent(bill -> bills.put(orderId, bill));
            Optional<Delivery> optionalDelivery = deliveryService.findDeliveryById(order.getDeliveryId());
            if (optionalDelivery.isPresent()) {
                Delivery delivery = optionalDelivery.get();
                deliveries.put(orderId, delivery);
                Optional<AddressDelivery> optionalAddress = deliveryService.findAddressById(delivery.getAddressId());
                optionalAddress.ifPresent(address -> addressMap.put(orderId, address));
            }
        }
        request.setAttribute(BILL_LIST, bills);
        request.setAttribute(DELIVERY_LIST, deliveries);
        request.setAttribute(ADDRESS_LIST, addressMap);
    }
}