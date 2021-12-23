package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.Bill;
import by.vchaikovski.coffeeshop.entity.Delivery;
import by.vchaikovski.coffeeshop.entity.FoodOrder;
import by.vchaikovski.coffeeshop.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderDao extends BaseDao<FoodOrder> {
    List<FoodOrder> findByOrderStatus(FoodOrder.OrderStatus orderStatus);

    List<FoodOrder> findByCreationDate(LocalDateTime creationDate);

    List<FoodOrder> findByCreationDate(LocalDateTime startPeriod, LocalDateTime endPeriod);

    List<FoodOrder> findByGoodsNumberInCart(int goodsNumber);

    List<FoodOrder> findByGoodsNumberInCart(int minNumber, int maxNumber);

    List<FoodOrder> findWithComment();

    List<FoodOrder> findByEvaluation(FoodOrder.OrderEvaluation orderEvaluation);

    List<FoodOrder> findByDelivery(Delivery delivery);

    List<FoodOrder> findByBill(Bill bill);

    List<FoodOrder> findByUser(User user);
}
