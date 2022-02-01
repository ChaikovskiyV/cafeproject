package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.entity.Bill;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.Menu;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderDao extends BaseDao<FoodOrder> {
    List<FoodOrder> findByOrderStatus(FoodOrder.OrderStatus orderStatus) throws DaoException;

    List<FoodOrder> findByCreationDate(LocalDateTime creationDate) throws DaoException;

    List<FoodOrder> findByCreationDate(LocalDateTime startPeriod, LocalDateTime endPeriod) throws DaoException;

    List<FoodOrder> findByEvaluation(FoodOrder.OrderEvaluation orderEvaluation) throws DaoException;

    List<FoodOrder> findByDelivery(long deliveryId) throws DaoException;

    Optional<FoodOrder> findByBill(long billId) throws DaoException;

    List<FoodOrder> findOrderByBills(List<Bill> bills) throws DaoException;

    List<FoodOrder> findByUser(long userId) throws DaoException;

    boolean updateOrderStatus(long id, FoodOrder.OrderStatus orderStatus) throws DaoException;

    boolean updateGoodsNumberInCart(long orderId, long menuId, int goodsNumber) throws DaoException;

    int createOrderCart(long orderId, Map<Menu, Integer> cart) throws DaoException;

    boolean deleteOrderCartByOrderId(long orderId) throws DaoException;

    boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws DaoException;
}