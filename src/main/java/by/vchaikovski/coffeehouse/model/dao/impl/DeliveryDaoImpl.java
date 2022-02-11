package by.vchaikovski.coffeehouse.model.dao.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.DeliveryDao;
import by.vchaikovski.coffeehouse.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeehouse.model.entity.Delivery;
import by.vchaikovski.coffeehouse.model.pool.ConnectionPool;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Delivery dao.
 */

public class DeliveryDaoImpl implements DeliveryDao {
    private static final DeliveryDaoImpl instance = new DeliveryDaoImpl();
    private static final MapperProvider mapperProvider = MapperProvider.getInstance();
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String FIND_ALL_DELIVERIES = "SELECT delivery_id, delivery_type, delivery_time, address_id FROM deliveries " +
            "JOIN address ON fk_address_id=address_id";
    private static final String BY_ID = " WHERE delivery_id=";
    private static final String BY_TYPE = " WHERE delivery_type=";
    private static final String BY_DATE = " WHERE delivery_date=";
    private static final String BY_PERIOD = " WHERE delivery_date>=? AND delivery_date<=?";
    private static final String BY_ADDRESS = " WHERE address_id=";
    private static final String UPDATE_DELIVERY_TYPE = "UPDATE deliveries SET delivery_type=? WHERE delivery_id=?";
    private static final String UPDATE_DELIVERY_DATE = "UPDATE deliveries SET delivery_date=? WHERE delivery_id=?";
    private static final String CREATE_DELIVERY = "INSERT INTO deliveries(delivery_type, delivery_time, fk_address_id) VALUES (?, ?, ?)";
    private static final String DELETE_DELIVERY_BY_ID = "DELETE FROM bills WHERE id=";

    private DeliveryDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DeliveryDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Delivery> findAll() throws DaoException {
        List<Delivery> deliveries = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES)) {
            while (resultSet.next()) {
                Delivery delivery = mapperProvider.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            String message = "The query \"find all deliveries\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return deliveries;
    }

    @Override
    public Optional<Delivery> findById(long id) throws DaoException {
        Delivery delivery = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + BY_ID + id)) {
            if (resultSet.next()) {
                delivery = mapperProvider.getDeliveryMapper().createEntity(resultSet);
            }
        } catch (SQLException e) {
            String message = "The query \"find all deliveries\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(delivery);
    }

    @Override
    public List<Delivery> findByDeliveryType(Delivery.DeliveryType deliveryType) throws DaoException {
        List<Delivery> deliveries = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + BY_TYPE + deliveryType)) {
            while (resultSet.next()) {
                Delivery delivery = mapperProvider.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            String message = "The query \"find deliveries by deliveryType\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return deliveries;
    }

    @Override
    public List<Delivery> findByAddressDelivery(long addressDeliveryId) throws DaoException {
        List<Delivery> deliveries = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + BY_ADDRESS + addressDeliveryId)) {
            while (resultSet.next()) {
                Delivery delivery = mapperProvider.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            String message = "The query \"find deliveries by addressDeliveryId=" + addressDeliveryId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return deliveries;
    }

    @Override
    public List<Delivery> findByDateDelivery(LocalDate timeDelivery) throws DaoException {
        List<Delivery> deliveries = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + BY_DATE + timeDelivery)) {
            while (resultSet.next()) {
                Delivery delivery = mapperProvider.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException e) {
            String message = "The query \"find deliveries by dateDelivery\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return deliveries;
    }

    @Override
    public List<Delivery> findByDateDelivery(LocalDate startPeriod, LocalDate endPeriod) throws DaoException {
        List<Delivery> deliveries = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DELIVERIES + BY_PERIOD)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(startPeriod));
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(endPeriod));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Delivery delivery = mapperProvider.getDeliveryMapper().createEntity(resultSet);
                    deliveries.add(delivery);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find deliveries by delivery period from " + startPeriod +
                    " to " + endPeriod + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return deliveries;
    }

    @Override
    public boolean update(long id, Delivery delivery) {
        throw new UnsupportedOperationException("The update(long id, Delivery delivery) method is unsupported.");
    }

    @Override
    public boolean updateDeliveryType(long id, Delivery.DeliveryType deliveryType) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DELIVERY_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, deliveryType.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            String message = "The query \"update delivery with id=" + id + " by delivery type=" + deliveryType +
                    FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateDeliveryDate(long id, LocalDate deliveryDate) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DELIVERY_DATE)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(deliveryDate));
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            String message = "The query \"update delivery with id=" + id + " by delivery date=" +
                    deliveryDate + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(Delivery delivery) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_DELIVERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, delivery.getDeliveryType().name());
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(delivery.getDeliveryTime()));
            statement.setLong(THIRD_PARAMETER_INDEX, delivery.getAddressId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long billId = 0;
                if (resultSet.next()) {
                    billId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
                return billId;
            }
        } catch (SQLException e) {
            String message = "The query \"insert delivery=" + delivery + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_DELIVERY_BY_ID + id);
        } catch (SQLException e) {
            String message = "The query \"delete delivery with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}