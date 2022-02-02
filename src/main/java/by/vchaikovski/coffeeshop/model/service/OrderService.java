package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {

    long createOrder(Map<String, String> orderParameters, Map<Menu, Integer> cart) throws ServiceException;

    boolean deleteOrderById(long id) throws ServiceException;

    List<FoodOrder> findAllOrders() throws ServiceException;

    Optional<FoodOrder> findOrderById(long orderId) throws ServiceException;

    List<FoodOrder> findOrderByStatus(String orderStatus) throws ServiceException;

    List<FoodOrder> findOrderByCreationDate(String creationDate) throws ServiceException;

    List<FoodOrder> findOrderByCreationPeriod(String startPeriod, String endPeriod) throws ServiceException;

    List<FoodOrder> findOrderByEvaluation(String orderEvaluation) throws ServiceException;

    List<FoodOrder> findOrderByDeliveryId(long deliveryId) throws ServiceException;

    Optional<FoodOrder> findOrderByBillId(long billId) throws ServiceException;

    List<FoodOrder> findOrderByUserId(long userId) throws ServiceException;

    List<FoodOrder> findOrderBySeveralParameters(Map<String, String> orderParameters) throws ServiceException;

    boolean updateOrderStatus(long id, String orderStatus) throws ServiceException;

    boolean updateGoodsNumberInCart(long orderId, long menuId, String goodsNumber) throws ServiceException;

    boolean updateComment(long orderId, String comment) throws ServiceException;

    boolean updateEvaluation(long orderId, String evaluation) throws ServiceException;

    int createOrderCart(long orderId, Map<Menu, Integer> cart) throws ServiceException;

    boolean deleteOrderCartByOrderId(long orderId) throws ServiceException;

    boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws ServiceException;
}