package by.vchaikovski.coffeeshop.model.dao.impl;

import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.OrderDao;
import by.vchaikovski.coffeeshop.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeeshop.model.entity.FoodOrder;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.model.pool.ConnectionPool;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderDaoImpl implements OrderDao {
    private static final OrderDaoImpl instance = new OrderDaoImpl();
    private static final MapperProvider mapperProvider = MapperProvider.getInstance();
    private static final String UPDATE_MESSAGE = "The query \"update order with id=";
    private static final String FIND_ALL_ORDERS = "SELECT order_id, order_status, creation_date, comment, " +
            "fk_user_id, fk_bill_id, fk_delivery_id, evaluation FROM orders " +
            "JOIN users ON fk_user_id=user_id " +
            "JOIN discounts ON fk_discount_id = discount_id " +
            "JOIN bills ON fk_bill_id=bill_id " +
            "JOIN deliveries ON fk_delivery_id=delivery_id " +
            "JOIN address ON fk_address_id=address_id" +
            "JOIN order_cart ON fk_order_id=order_id " +
            "JOIN menu ON fk_menu_id=menu_id ";
    private static final String FIND_ORDER_BY_ID = " WHERE order_id=";
    private static final String FIND_ORDER_BY_STATUS = " WHERE order_status=?";
    private static final String FIND_ORDER_BY_CREATION_DATE = " WHERE creation_date=?";
    private static final String FIND_ORDER_BY_CREATION_PERIOD = " WHERE creation_date>=? AND creation_date<=?";
    private static final String FIND_ORDER_BY_EVALUATION = " WHERE evaluation=?";
    private static final String FIND_ORDER_BY_DELIVERY = " WHERE delivery_id=";
    private static final String FIND_ORDER_BY_BILL = " WHERE bill_id=";
    private static final String FIND_ORDER_BY_USER = " WHERE user_id=";

    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET order_status=? WHERE order_id=?";
    private static final String UPDATE_ORDER_GOODS_NUMBER_IN_CART = "UPDATE order_cart SET quantity=? " +
            "WHERE fk_order_id=? AND fk_menu_id=?";
    private static final String CREATE_ORDER = "INSERT INTO orders(order_status, creation_date, comment, evaluation, fk_user_id, fk_bill_id, fk_delivery_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String CREATE_ORDER_CART = "INSERT INTO order_cart(fk_order_id, quantity, fk_menu_id) VALUES (?, ?, ?)";
    private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id=";
    private static final String DELETE_ORDER_CART_BY_ORDER_ID_AND_MENU_ID = "DELETE FROM order_cart " +
            "WHERE fk_order_id=? AND fk_menu_id=?";
    private static final String DELETE_ORDER_CART_BY_ORDER_ID = "DELETE FROM order_cart " +
            "WHERE fk_order_id=";

    private OrderDaoImpl() {
    }

    public static OrderDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<FoodOrder> findAll() throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS)) {
            while (resultSet.next()) {
                FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                orders.add(order);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find all orders\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public Optional<FoodOrder> findById(long id) throws DaoException {
        FoodOrder order = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + FIND_ORDER_BY_ID + id)) {
            if (resultSet.next()) {
                order = mapperProvider.getFoodMapper().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find an order by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<FoodOrder> findByOrderStatus(FoodOrder.OrderStatus orderStatus) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + FIND_ORDER_BY_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, orderStatus.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find orders by status=" + orderStatus + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByCreationDate(LocalDateTime creationDate) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + FIND_ORDER_BY_CREATION_DATE)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(creationDate.format(timeFormatter)));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find orders by creationDate=" + creationDate.format(timeFormatter) + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByCreationDate(LocalDateTime startPeriod, LocalDateTime endPeriod) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + FIND_ORDER_BY_CREATION_PERIOD)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(startPeriod.format(timeFormatter)));
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(endPeriod.format(timeFormatter)));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find orders by paymentPeriod from " + startPeriod.format(timeFormatter) +
                    " to " + endPeriod.format(timeFormatter) + FAILED_MESSAGE;
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByEvaluation(FoodOrder.OrderEvaluation orderEvaluation) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + FIND_ORDER_BY_EVALUATION)) {
            statement.setString(FIRST_PARAMETER_INDEX, orderEvaluation.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    orders.add(order);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find orders by evaluation=" + orderEvaluation + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByDelivery(long deliveryId) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + FIND_ORDER_BY_DELIVERY + deliveryId)) {
            while (resultSet.next()) {
                FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                orders.add(order);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find orders by deliveryId=" + deliveryId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByBill(long billId) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + FIND_ORDER_BY_BILL + billId)) {
            while (resultSet.next()) {
                FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                orders.add(order);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find orders by billId=" + billId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByUser(long userId) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + FIND_ORDER_BY_USER + userId)) {
            while (resultSet.next()) {
                FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                orders.add(order);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find orders by userId=" + userId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public boolean update(long id, FoodOrder order) {
        throw new UnsupportedOperationException("The update(long id, FoodOrder order) method is not supported");
    }

    @Override
    public boolean updateOrderStatus(long id, FoodOrder.OrderStatus orderStatus) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, orderStatus.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by status=" + orderStatus.name() + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateGoodsNumberInCart(long orderId, long menuId, int goodsNumber) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_GOODS_NUMBER_IN_CART)) {
            statement.setInt(FIRST_PARAMETER_INDEX, goodsNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, orderId);
            statement.setLong(THIRD_PARAMETER_INDEX, menuId);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + menuId + " by goodsNumber in cart=" + goodsNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(FoodOrder order) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, order.getStatus().name());
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(order.getCreationDate().toLocalDate()));
            statement.setString(THIRD_PARAMETER_INDEX, order.getComment());
            statement.setString(FOURTH_PARAMETER_INDEX, order.getEvaluation().name());
            statement.setLong(FIFTH_PARAMETER_INDEX, order.getUserId());
            statement.setLong(SIXTH_PARAMETER_INDEX, order.getBillId());
            statement.setLong(SEVENTH_PARAMETER_INDEX, order.getDeliveryId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long orderId = 0;
                if (resultSet.next()) {
                    orderId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
                return orderId;
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"create foodOrder " + order + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_ORDER_BY_ID + id);
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete order with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public int createOrderCart(long orderId, Map<Menu, Integer> cart) throws DaoException { //TODO Change this method
        int rowNumber = 0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            for (Map.Entry<Menu, Integer> entry : cart.entrySet()) {
                statement = connection.prepareStatement(CREATE_ORDER_CART);
                statement.setLong(FIRST_PARAMETER_INDEX, orderId);
                statement.setInt(SECOND_PARAMETER_INDEX, entry.getValue());
                statement.setLong(THIRD_PARAMETER_INDEX, entry.getKey().getId());
                rowNumber = rowNumber + statement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                String message = "The rollback method wasn't completed";
                logger.error(message, ex);
                throw new DaoException(message, ex);
            }
            String message = "The query \"create foodOrder " + cart + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException ex) {
                String message = "Autocommit wasn't changed";
                logger.error(message, ex);
            }
        }
        return rowNumber;
    }

    @Override
    public boolean deleteOrderCartByOrderId(long orderId) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_ORDER_CART_BY_ORDER_ID + orderId);
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete orderCart by orderId=" + orderId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean deleteOrderCartByOrderIdAndMenuId(long orderId, long menuId) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_CART_BY_ORDER_ID_AND_MENU_ID)) {
            statement.setLong(FIRST_PARAMETER_INDEX, orderId);
            statement.setLong(SECOND_PARAMETER_INDEX, menuId);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete orderCart by orderId=" + orderId + " and menuId=" + menuId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}