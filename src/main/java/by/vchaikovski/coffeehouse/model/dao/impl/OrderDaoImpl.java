package by.vchaikovski.coffeehouse.model.dao.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.ColumnTable;
import by.vchaikovski.coffeehouse.model.dao.OrderDao;
import by.vchaikovski.coffeehouse.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeehouse.model.dao.mapper.impl.MenuMapperImpl;
import by.vchaikovski.coffeehouse.model.entity.Bill;
import by.vchaikovski.coffeehouse.model.entity.FoodOrder;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.pool.ConnectionPool;

import java.sql.Date;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Order dao.
 */
public class OrderDaoImpl implements OrderDao {
    private static final OrderDaoImpl instance = new OrderDaoImpl();
    private static final MapperProvider mapperProvider = MapperProvider.getInstance();
    private static final String UPDATE_MESSAGE = "The query \"update order with id=";
    private static final String FIND_ALL_ORDERS = "SELECT order_id, order_status, creation_date, comment, " +
            "fk_user_id, fk_bill_id, fk_delivery_id, evaluation FROM orders " +
            "JOIN users ON fk_user_id=user_id " +
            "JOIN discounts ON fk_discount_id=discount_id " +
            "JOIN bills ON fk_bill_id=bill_id " +
            "JOIN deliveries ON fk_delivery_id=delivery_id " +
            "JOIN address ON fk_address_id=address_id";
    private static final String FIND_MENU_FROM_CART_BY_ORDER_ID = "SELECT quantity, menu_id, name, product_type, description, price, " +
            " quantity_in_stock, image FROM order_cart JOIN menu ON fk_menu_id=menu_id WHERE fk_order_id=";
    private static final String BY_ID = " WHERE order_id=";
    private static final String BY_STATUS = " WHERE order_status=?";
    private static final String BY_CREATION_DATE = " WHERE creation_date=?";
    private static final String BY_CREATION_PERIOD = " WHERE creation_date>=? AND creation_date<=?";
    private static final String BY_EVALUATION = " WHERE evaluation=?";
    private static final String BY_DELIVERY = " WHERE delivery_id=";
    private static final String BY_BILL = " WHERE bill_id=";
    private static final String BY_USER = " WHERE user_id=";

    private static final String UPDATE_ORDER_STATUS = "UPDATE orders SET order_status=? WHERE order_id=?";
    private static final String UPDATE_ORDER_GOODS_NUMBER_IN_CART = "UPDATE order_cart SET quantity=? " +
            "WHERE fk_order_id=? AND fk_menu_id=?";
    private static final String UPDATE_ORDER_COMMENT = "UPDATE orders SET comment=? WHERE order_id=?";
    private static final String UPDATE_ORDER_EVALUATION = "UPDATE orders SET evaluation=? WHERE order_id=?";
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
                createCartAndSet(order);
                orders.add(order);
            }
        } catch (SQLException e) {
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
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + BY_ID + id)) {
            if (resultSet.next()) {
                order = mapperProvider.getFoodMapper().createEntity(resultSet);
                createCartAndSet(order);
            }
        } catch (SQLException e) {
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
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + BY_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, orderStatus.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    createCartAndSet(order);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find orders by status=" + orderStatus + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByCreationDate(LocalDate creationDate) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + BY_CREATION_DATE)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(creationDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    createCartAndSet(order);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find orders by creationDate=" + creationDate + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByCreationDate(LocalDate startPeriod, LocalDate endPeriod) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + BY_CREATION_PERIOD)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(startPeriod));
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(endPeriod));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    createCartAndSet(order);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find orders by paymentPeriod from " + startPeriod +
                    " to " + endPeriod + FAILED_MESSAGE;
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public List<FoodOrder> findByEvaluation(FoodOrder.OrderEvaluation orderEvaluation) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_ORDERS + BY_EVALUATION)) {
            statement.setString(FIRST_PARAMETER_INDEX, orderEvaluation.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                    createCartAndSet(order);
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
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
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + BY_DELIVERY + deliveryId)) {
            while (resultSet.next()) {
                FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                createCartAndSet(order);
                orders.add(order);
            }
        } catch (SQLException e) {
            String message = "The query \"find orders by deliveryId=" + deliveryId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return orders;
    }

    @Override
    public Optional<FoodOrder> findByBill(long billId) throws DaoException {
        FoodOrder order = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + BY_BILL + billId)) {
            if (resultSet.next()) {
                order = mapperProvider.getFoodMapper().createEntity(resultSet);
                createCartAndSet(order);
            }
        } catch (SQLException e) {
            String message = "The query \"find orders by billId=" + billId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(order);
    }

    @Override
    public List<FoodOrder> findOrderByBills(List<Bill> bills) throws DaoException {
        List<FoodOrder> orders = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            for (Bill bill : bills) {
                long billId = bill.getId();
                try (ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + BY_BILL + billId)) {
                    if (resultSet.next()) {
                        FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                        createCartAndSet(order);
                        orders.add(order);
                    }
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find orders by bills list=" + bills + FAILED_MESSAGE;
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
             ResultSet resultSet = statement.executeQuery(FIND_ALL_ORDERS + BY_USER + userId)) {
            while (resultSet.next()) {
                FoodOrder order = mapperProvider.getFoodMapper().createEntity(resultSet);
                createCartAndSet(order);
                orders.add(order);
            }
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
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
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(order.getCreationDate()));
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
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            String message = "The query \"delete order with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public int createOrderCart(long orderId, Map<Menu, Integer> cart) throws DaoException {
        int cartRows;
        Connection connection = null;
        try {
            connection = ConnectionPool.getInstance().getConnection();
            connection.setAutoCommit(false);
            try (PreparedStatement statement = connection.prepareStatement(CREATE_ORDER_CART)) {
                for (Map.Entry<Menu, Integer> entry : cart.entrySet()) {
                    statement.setLong(FIRST_PARAMETER_INDEX, orderId);
                    statement.setInt(SECOND_PARAMETER_INDEX, entry.getValue());
                    statement.setLong(THIRD_PARAMETER_INDEX, entry.getKey().getId());
                    statement.addBatch();
                }
                cartRows = statement.executeBatch().length;
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
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
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException ex) {
                String message = "Autocommit wasn't changed";
                logger.error(message, ex);
            }
        }
        return cartRows;
    }

    @Override
    public boolean deleteOrderCartByOrderId(long orderId) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_ORDER_CART_BY_ORDER_ID + orderId);
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            String message = "The query \"delete orderCart by orderId=" + orderId + " and menuId=" + menuId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateComment(long orderId, String comment) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_COMMENT)) {
            statement.setString(FIRST_PARAMETER_INDEX, comment);
            statement.setLong(SECOND_PARAMETER_INDEX, orderId);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            String message = UPDATE_MESSAGE + orderId + " by comment=" + comment + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateEvaluation(long orderId, FoodOrder.OrderEvaluation evaluation) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ORDER_EVALUATION)) {
            statement.setString(FIRST_PARAMETER_INDEX, evaluation.name());
            statement.setLong(SECOND_PARAMETER_INDEX, orderId);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            String message = UPDATE_MESSAGE + orderId + " by status=" + evaluation.name() + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    private void createCartAndSet(FoodOrder order) throws SQLException, DaoException {
        Map<Menu, Integer> cart = new HashMap<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_MENU_FROM_CART_BY_ORDER_ID + order.getId())) {
            MenuMapperImpl menuMapper = mapperProvider.getMenuMapper();
            while (resultSet.next()) {
                int quantity = resultSet.getInt(ColumnTable.CART_QUANTITY);
                Menu menu = menuMapper.createEntity(resultSet);
                cart.put(menu, quantity);
            }
        }
        order.setCart(cart);
    }
}