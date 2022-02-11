package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.Bill;
import by.vchaikovski.coffeehouse.model.entity.FoodOrder;
import by.vchaikovski.coffeehouse.model.entity.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Order dao.
 */
public interface OrderDao extends BaseDao<FoodOrder> {
    /**
     * Find by order status list.
     *
     * @param orderStatus the order status
     * @return the list
     * @throws DaoException the dao exception
     */
    List<FoodOrder> findByOrderStatus(FoodOrder.OrderStatus orderStatus) throws DaoException;

    /**
     * Find by creation date list.
     *
     * @param creationDate the creation date
     * @return the list
     * @throws DaoException the dao exception
     */
    List<FoodOrder> findByCreationDate(LocalDate creationDate) throws DaoException;

    /**
     * Find by creation date list.
     *
     * @param startPeriod the start period
     * @param endPeriod   the end period
     * @return the list
     * @throws DaoException the dao exception
     */
    List<FoodOrder> findByCreationDate(LocalDate startPeriod, LocalDate endPeriod) throws DaoException;

    /**
     * Find by evaluation list.
     *
     * @param orderEvaluation the order evaluation
     * @return the list
     * @throws DaoException the dao exception
     */
    List<FoodOrder> findByEvaluation(FoodOrder.OrderEvaluation orderEvaluation) throws DaoException;

    /**
     * Find by delivery list.
     *
     * @param deliveryId the delivery id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<FoodOrder> findByDelivery(long deliveryId) throws DaoException;

    /**
     * Find by bill optional.
     *
     * @param billId the bill id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<FoodOrder> findByBill(long billId) throws DaoException;

    /**
     * Find order by bills list.
     *
     * @param bills the bills
     * @return the list
     * @throws DaoException the dao exception
     */
    List<FoodOrder> findOrderByBills(List<Bill> bills) throws DaoException;

    /**
     * Find by user list.
     *
     * @param userId the user id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<FoodOrder> findByUser(long userId) throws DaoException;

    /**
     * Update order status boolean.
     *
     * @param id          the id
     * @param orderStatus the order status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateOrderStatus(long id, FoodOrder.OrderStatus orderStatus) throws DaoException;

    /**
     * Update goods number in cart boolean.
     *
     * @param orderId     the order id
     * @param menuId      the menu id
     * @param goodsNumber the goods number
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateGoodsNumberInCart(long orderId, long menuId, int goodsNumber) throws DaoException;

    /**
     * Create order cart int.
     *
     * @param orderId the order id
     * @param cart    the cart
     * @return the int
     * @throws DaoException the dao exception
     */
    int createOrderCart(long orderId, Map<Menu, Integer> cart) throws DaoException;

    /**
     * Delete order cart by order id boolean.
     *
     * @param orderId the order id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean deleteOrderCartByOrderId(long orderId) throws DaoException;

    /**
     * Delete order cart by order id and menu id boolean.
     *
     * @param orderId the order id
     * @param menuId  the menu id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws DaoException;

    /**
     * Update comment boolean.
     *
     * @param orderId the order id
     * @param comment the comment
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateComment(long orderId, String comment) throws DaoException;

    /**
     * Update evaluation boolean.
     *
     * @param orderId    the order id
     * @param evaluation the evaluation
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateEvaluation(long orderId, FoodOrder.OrderEvaluation evaluation) throws DaoException;
}