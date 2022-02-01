package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.dao.OrderDao;
import by.vchaikovski.coffeeshop.model.entity.Bill;
import by.vchaikovski.coffeeshop.model.entity.Delivery;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.model.service.BillService;
import by.vchaikovski.coffeeshop.model.service.DeliveryService;
import by.vchaikovski.coffeeshop.model.service.OrderService;
import by.vchaikovski.coffeeshop.model.service.ServiceProvider;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.FormValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import by.vchaikovski.coffeeshop.util.validator.impl.FormValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeeshop.controller.command.SessionParameter.USER_ID;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static final String DATA_TIME_FORMAT = "yyyy-MM-dd HH:ss";
    private static OrderServiceImpl instance;
    private final OrderDao orderDao;

    private OrderServiceImpl() {
        orderDao = DaoProvider.getInstance().getOrderDao();
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public long createOrder(Map<String, String> orderParameters, Map<Menu, Integer> cart) throws ServiceException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        long orderId = 0;
        FormValidator validator = FormValidatorImpl.getInstance();
        if (validator.isOrderParameterValid(orderParameters)) {
            String userIdStr = orderParameters.get(USER_ID);
            String comment = orderParameters.get(COMMENT);
            long deliveryId = serviceProvider.getDeliveryService().createDelivery(orderParameters);
            long billId = serviceProvider.getBillService().createBill(orderParameters);
            FoodOrder order = new FoodOrder.FoodOrderBuilder()
                    .setUserId(Long.parseLong(userIdStr))
                    .setDeliveryId(deliveryId)
                    .setBillId(billId)
                    .setComment(comment)
                    .setCart(cart)
                    .build();
            try {
                orderId = orderDao.create(order);
                orderDao.createOrderCart(orderId, cart);
            } catch (DaoException e) {
                String message = "FoodOrder can't be inserted in data base";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return orderId;
    }

    @Override
    public boolean deleteOrderById(long id) throws ServiceException {
        boolean result;
        try {
            result = orderDao.deleteById(id);
        } catch (DaoException e) {
            String message = "FoodOrder can't be deleted by id=" + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    @Override
    public List<FoodOrder> findAllOrders() throws ServiceException {
        List<FoodOrder> orders;
        try {
            orders = orderDao.findAll();
        } catch (DaoException e) {
            String message = "FoodOrders can't be found";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return orders;
    }

    @Override
    public Optional<FoodOrder> findOrderById(long orderId) throws ServiceException {
        Optional<FoodOrder> optionalOrder;
        try {
            optionalOrder = orderDao.findById(orderId);
        } catch (DaoException e) {
            String message = "FoodOrder can't be found by id=" + orderId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalOrder;
    }

    @Override
    public List<FoodOrder> findOrderByStatus(String orderStatus) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<FoodOrder> orders = new ArrayList<>();
        if (validator.isEnumContains(orderStatus, FoodOrder.OrderStatus.class)) {
            FoodOrder.OrderStatus status = FoodOrder.OrderStatus.valueOf(orderStatus.toUpperCase());
            try {
                orders = orderDao.findByOrderStatus(status);
            } catch (DaoException e) {
                String message = "FoodOrder can't be found by order status " + orderStatus;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findOrderByCreationDate(String creationDate) throws ServiceException {
        List<FoodOrder> orders = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateTimeValid(creationDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_TIME_FORMAT);
            try {
                orders = orderDao.findByCreationDate(LocalDateTime.parse(creationDate, formatter));
            } catch (DaoException e) {
                String message = "FoodOrders can't be found by creation date=" + creationDate;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findOrderByCreationPeriod(String startPeriod, String endPeriod) throws ServiceException {
        List<FoodOrder> orders = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateTimeValid(startPeriod) && validator.isDateTimeValid(endPeriod)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_TIME_FORMAT);
            try {
                LocalDateTime start = LocalDateTime.parse(startPeriod, formatter);
                LocalDateTime finish = LocalDateTime.parse(endPeriod, formatter);
                orders = orderDao.findByCreationDate(start, finish);
            } catch (DaoException e) {
                String message = "FoodOrders can't be found by creation period=" + startPeriod + " - " + endPeriod;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findOrderByEvaluation(String orderEvaluation) throws ServiceException {
        List<FoodOrder> orders = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(orderEvaluation, FoodOrder.OrderEvaluation.class)) {
            try {
                orders = orderDao.findByEvaluation(FoodOrder.OrderEvaluation.valueOf(orderEvaluation.toUpperCase()));
            } catch (DaoException e) {
                String message = "FoodOrders can't be found by evaluation=" + orderEvaluation;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findOrderByDeliveryId(long deliveryId) throws ServiceException {
        List<FoodOrder> orders;
        try {
            orders = orderDao.findByDelivery(deliveryId);
        } catch (DaoException e) {
            String message = "FoodOrders can't be found by delivery id=" + deliveryId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return orders;
    }

    @Override
    public Optional<FoodOrder> findOrderByBillId(long billId) throws ServiceException {
        Optional<FoodOrder> orderOptional;
        try {
            orderOptional = orderDao.findByBill(billId);
        } catch (DaoException e) {
            String message = "FoodOrders can't be found by bill id=" + billId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return orderOptional;
    }

    @Override
    public List<FoodOrder> findOrderByUserId(long userId) throws ServiceException {
        List<FoodOrder> orders;
        try {
            orders = orderDao.findByUser(userId);
        } catch (DaoException e) {
            String message = "FoodOrders can't be found by user id=" + userId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findOrderBySeveralParameters(Map<String, String> orderParameters) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<FoodOrder> orders = findAllOrders();
        if (orderParameters != null && !orderParameters.isEmpty()) {
            String billStatus = orderParameters.get(BILL_STATUS);
            String paymentDate = orderParameters.get(PAYMENT_DATE);
            String creationDate = orderParameters.get(CREATION_DATE);
            String deliveryTime = orderParameters.get(DELIVERY_TIME);
            String deliveryType = orderParameters.get(DELIVERY_TYPE);
            String userId = orderParameters.get(USER_ID);
            String orderStatus = orderParameters.get(ORDER_STATUS);
            BillService billService = ServiceProvider.getInstance().getBillService();
            orders = filterOrdersByBill(billService.findBillByStatus(billStatus), orders);
            orders = filterOrdersByBill(billService.findBillByPaymentTime(paymentDate), orders);

            DeliveryService deliveryService = ServiceProvider.getInstance().getDeliveryService();
            orders = filterOrdersByDelivery(deliveryService.findDeliveryByType(deliveryType), orders);
            orders = filterOrdersByDelivery(deliveryService.findDeliveryByDate(deliveryTime), orders);

            if (validator.isDateTimeValid(creationDate)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATA_TIME_FORMAT);
                LocalDateTime dateTime = LocalDateTime.parse(creationDate, formatter);
                orders = orders.stream().filter(order -> order.getCreationDate().isEqual(dateTime)).toList();
            }
            if (validator.isEnumContains(orderStatus, FoodOrder.OrderStatus.class)) {
                FoodOrder.OrderStatus status = FoodOrder.OrderStatus.valueOf(orderStatus.toUpperCase());
                orders = orders.stream().filter(order -> order.getStatus() == status).toList();
            }
            if (validator.isNumberValid(userId)) {
                orders = orders.stream().filter(order -> order.getUserId() == Long.parseLong(userId)).toList();
            }
        }
        return orders;
    }

    @Override
    public boolean updateOrderStatus(long id, String orderStatus) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(orderStatus, FoodOrder.OrderStatus.class) && isOrderUncompleted(id)) {
            try {
                result = orderDao.updateOrderStatus(id, FoodOrder.OrderStatus.valueOf(orderStatus));
            } catch (DaoException e) {
                String message = "FoodOrders can't be updated by order status=" + orderStatus;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateGoodsNumberInCart(long orderId, long menuId, String goodsNumber) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(goodsNumber) && isOrderUncompleted(orderId)) {
            try {
                result = orderDao.updateGoodsNumberInCart(orderId, menuId, Integer.parseInt(goodsNumber));
            } catch (DaoException e) {
                String message = "Goods number in cart can't be updated";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public int createOrderCart(long orderId, Map<Menu, Integer> cart) throws ServiceException {
        int rowNumber = 0;
        if (isOrderUncompleted(orderId)) {
            try {
                rowNumber = orderDao.createOrderCart(orderId, cart);
            } catch (DaoException e) {
                String message = "Cart can't be created";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return rowNumber;
    }

    @Override
    public boolean deleteOrderCartByOrderId(long orderId) throws ServiceException {
        boolean result;
        try {
            result = orderDao.deleteOrderCartByOrderId(orderId);
        } catch (DaoException e) {
            String message = "Order cart can't be deleted by order id=" + orderId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    @Override
    public boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws ServiceException {
        boolean result;
        try {
            result = orderDao.deleteOrderCartByOrderIdAndMenuId(orderId, menuId);
        } catch (DaoException e) {
            String message = "Order cart can't be deleted by order id=" + orderId + " and menu id=" + menuId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    private boolean isOrderUncompleted(long orderId) throws ServiceException {
        boolean result = false;
        Optional<FoodOrder> optionalOrder;
        try {
            optionalOrder = orderDao.findById(orderId);
        } catch (DaoException e) {
            String message = "Order can't be found by id=" + orderId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        if (optionalOrder.isPresent()) {
            FoodOrder.OrderStatus status = optionalOrder.get().getStatus();
            result = status != FoodOrder.OrderStatus.COMPLETED && status != FoodOrder.OrderStatus.CANCELLED;
        }
        return result;
    }

    private List<FoodOrder> filterOrdersByBill(List<Bill> bills, List<FoodOrder> orders) {
        if (bills != null && !bills.isEmpty()) {
            List<Long> billsId = bills.stream()
                    .map(Bill::getId)
                    .toList();
            orders = orders.stream().filter(order -> billsId.contains(order.getBillId())).toList();
        }
        return orders;
    }

    private List<FoodOrder> filterOrdersByDelivery(List<Delivery> deliveries, List<FoodOrder> orders) {
        if (deliveries != null && !deliveries.isEmpty()) {
            List<Long> deliveriesId = deliveries.stream()
                    .map(Delivery::getId)
                    .toList();
            orders = orders.stream().filter(order -> deliveriesId.contains(order.getDeliveryId())).toList();
        }
        return orders;
    }
}