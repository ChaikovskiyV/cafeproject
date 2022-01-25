package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.OrderCart;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    long createOrder(Map<String, String> orderParameters);

    boolean deleteOrderById(long id) throws ServiceException;

    List<FoodOrder> findAllOrders() throws ServiceException;

    Optional<FoodOrder> findOrderById(long orderId) throws ServiceException;

    List<FoodOrder> findOrderByStatus(String orderStatus) throws ServiceException;

    List<FoodOrder> findOrderByCreationDate(String creationDate) throws ServiceException;

    List<FoodOrder> findOrderByCreationPeriod(String startPeriod, String endPeriod) throws ServiceException;

    List<FoodOrder> findOrderByEvaluation(String orderEvaluation) throws ServiceException;

    List<FoodOrder> findOrderByDeliveryId(long deliveryId) throws ServiceException;

    List<FoodOrder> findOrderByBillId(long billId) throws ServiceException;

    List<FoodOrder> findOrderByUserId(long userId) throws ServiceException;

    boolean updateOrder(long id, FoodOrder order);

    boolean updateOrderStatus(long id, String orderStatus) throws ServiceException;

    boolean updateGoodsNumberInCart(long id, String goodsNumber) throws ServiceException;

    int createOrderCart(OrderCart orderCart, long orderId);

    boolean deleteOrderCartByOrderId(long orderId) throws ServiceException;

    boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws ServiceException;
}