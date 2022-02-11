package by.vchaikovski.coffeehouse.controller.command.impl.go;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.controller.command.BaseCommand;
import by.vchaikovski.coffeehouse.controller.command.PagePath;
import by.vchaikovski.coffeehouse.exception.CommandException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.*;
import by.vchaikovski.coffeehouse.model.service.OrderService;
import by.vchaikovski.coffeehouse.model.service.ServiceProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Go to order info command.
 */

public class GoToOrderInfoCommand implements BaseCommand {
    private static final Logger logger = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String orderId = request.getParameter(CURRENT_ORDER_ID);
        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        try {
            Optional<FoodOrder> optionalOrder = orderService.findOrderById(Long.parseLong(orderId));
            if (optionalOrder.isPresent()) {
                fillAttribute(optionalOrder.get(), request);
            }
        } catch (ServiceException e) {
            String message = "Go to order info command can't be completed";
            logger.error(message, e);
            throw new CommandException(message, e);
        }
        return new Router(PagePath.ORDER_INFO_PAGE);
    }

    private void fillAttribute(FoodOrder order, HttpServletRequest request) throws ServiceException {
        HttpSession session = request.getSession();
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        session.setAttribute(CURRENT_ORDER, order);
        Optional<Bill> optionalBill = serviceProvider.getBillService().findBillById(order.getBillId());
        optionalBill.ifPresent(bill -> session.setAttribute(CURRENT_BILL, bill));
        Optional<User> optionalUser = serviceProvider.getUserService().findUserById(order.getUserId());
        optionalUser.ifPresent(user -> session.setAttribute(CURRENT_USER, user));
        Optional<Delivery> optionalDelivery = serviceProvider.getDeliveryService().findDeliveryById(order.getDeliveryId());
        if (optionalDelivery.isPresent()) {
            Delivery delivery = optionalDelivery.get();
            Optional<AddressDelivery> optionalAddress = serviceProvider.getDeliveryService().findAddressById(delivery.getAddressId());
            session.setAttribute(CURRENT_DELIVERY, delivery);
            optionalAddress.ifPresent(address -> session.setAttribute(CURRENT_ADDRESS, address));
        }
    }
}