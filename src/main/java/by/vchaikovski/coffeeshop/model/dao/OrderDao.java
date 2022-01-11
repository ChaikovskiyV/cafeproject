package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.OrderCart;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends BaseDao<FoodOrder> {
    List<FoodOrder> findByOrderStatus(FoodOrder.OrderStatus orderStatus) throws DaoException;

    List<FoodOrder> findByCreationDate(LocalDateTime creationDate) throws DaoException;

    List<FoodOrder> findByCreationDate(LocalDateTime startPeriod, LocalDateTime endPeriod) throws DaoException;

    List<FoodOrder> findByEvaluation(FoodOrder.OrderEvaluation orderEvaluation) throws DaoException;

    List<FoodOrder> findByDelivery(long deliveryId) throws DaoException;

    List<FoodOrder> findByBill(long billId) throws DaoException;

    List<FoodOrder> findByUser(long userId) throws DaoException;

    boolean updateOrderStatus(long id, FoodOrder.OrderStatus orderStatus) throws DaoException;

    boolean updateGoodsNumberInCart(long id, int goodsNumber) throws DaoException;

    int createOrderCart(OrderCart orderCart, long orderId) throws DaoException;

    boolean deleteOrderCartByOrderId(long orderId) throws DaoException;

    boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws DaoException;
}