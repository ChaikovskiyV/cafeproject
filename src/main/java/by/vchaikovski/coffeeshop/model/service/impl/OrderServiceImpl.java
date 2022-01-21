package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.dao.OrderDao;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.service.OrderService;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static final Logger logger = LogManager.getLogger();
    private static OrderServiceImpl instance;

    private OrderServiceImpl() {
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<FoodOrder> findOrderByStatus(String orderStatus) throws ServiceException {
        OrderDao orderDao = DaoProvider.getInstance().getOrderDao();
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
}