package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.FoodOrder;
import by.vchaikovski.coffeehouse.model.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Order service.
 */
public interface OrderService {

    /**
     * Create order long.
     *
     * @param orderParameters the order parameters
     * @param cart            the cart
     * @return the long
     * @throws ServiceException the service exception
     */
    long createOrder(Map<String, String> orderParameters, Map<Menu, Integer> cart) throws ServiceException;

    /**
     * Delete order by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteOrderById(long id) throws ServiceException;

    /**
     * Find all orders list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findAllOrders() throws ServiceException;

    /**
     * Find order by id optional.
     *
     * @param orderId the order id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<FoodOrder> findOrderById(long orderId) throws ServiceException;

    /**
     * Find order by status list.
     *
     * @param orderStatus the order status
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findOrderByStatus(String orderStatus) throws ServiceException;

    /**
     * Find order by creation date list.
     *
     * @param creationDate the creation date
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findOrderByCreationDate(String creationDate) throws ServiceException;

    /**
     * Find order by creation period list.
     *
     * @param startPeriod the start period
     * @param endPeriod   the end period
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findOrderByCreationPeriod(String startPeriod, String endPeriod) throws ServiceException;

    /**
     * Find order by evaluation list.
     *
     * @param orderEvaluation the order evaluation
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findOrderByEvaluation(String orderEvaluation) throws ServiceException;

    /**
     * Find order by delivery id list.
     *
     * @param deliveryId the delivery id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findOrderByDeliveryId(long deliveryId) throws ServiceException;

    /**
     * Find order by bill id optional.
     *
     * @param billId the bill id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<FoodOrder> findOrderByBillId(long billId) throws ServiceException;

    /**
     * Find order by user id list.
     *
     * @param userId the user id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findOrderByUserId(long userId) throws ServiceException;

    /**
     * Find order by several parameters list.
     *
     * @param orderParameters the order parameters
     * @return the list
     * @throws ServiceException the service exception
     */
    List<FoodOrder> findOrderBySeveralParameters(Map<String, String> orderParameters) throws ServiceException;

    /**
     * Update order status boolean.
     *
     * @param id          the id
     * @param orderStatus the order status
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateOrderStatus(long id, String orderStatus) throws ServiceException;

    /**
     * Update goods number in cart boolean.
     *
     * @param orderId     the order id
     * @param menuId      the menu id
     * @param goodsNumber the goods number
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateGoodsNumberInCart(long orderId, long menuId, String goodsNumber) throws ServiceException;

    /**
     * Update comment boolean.
     *
     * @param orderId the order id
     * @param comment the comment
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateComment(long orderId, String comment) throws ServiceException;

    /**
     * Update evaluation boolean.
     *
     * @param orderId    the order id
     * @param evaluation the evaluation
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateEvaluation(long orderId, String evaluation) throws ServiceException;

    /**
     * Create order cart int.
     *
     * @param orderId the order id
     * @param cart    the cart
     * @return the int
     * @throws ServiceException the service exception
     */
    int createOrderCart(long orderId, Map<Menu, Integer> cart) throws ServiceException;

    /**
     * Delete order cart by order id boolean.
     *
     * @param orderId the order id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteOrderCartByOrderId(long orderId) throws ServiceException;

    /**
     * Delete order cart by order id and menu id boolean.
     *
     * @param orderId the order id
     * @param menuId  the menu id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws ServiceException;
}