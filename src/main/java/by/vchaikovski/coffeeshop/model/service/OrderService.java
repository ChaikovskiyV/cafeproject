package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;

import java.util.List;

public interface OrderService {
    List<FoodOrder> findOrderByStatus(String orderStatus) throws ServiceException;

}
