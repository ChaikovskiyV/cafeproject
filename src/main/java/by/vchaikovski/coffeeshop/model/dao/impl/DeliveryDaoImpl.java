package by.vchaikovski.coffeeshop.model.dao.impl;

import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.DeliveryDao;
import by.vchaikovski.coffeeshop.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeeshop.model.entity.Delivery;
import by.vchaikovski.coffeeshop.model.pool.ConnectionPool;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeliveryDaoImpl implements DeliveryDao {
    private static final DeliveryDaoImpl instance = new DeliveryDaoImpl();
    private static final MapperProvider MAPPER_PROVIDER = MapperProvider.getInstance();
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String FIND_ALL_DELIVERIES = "SELECT delivery_id, delivery_type, delivery_time FROM deliveries" +
            "JOIN address ON fk_address_id=address_id";
    private static final String FIND_DELIVERY_BY_ID = " WHERE delivery_id=";
    private static final String FIND_DELIVERY_BY_TYPE = " WHERE delivery_type=";
    private static final String FIND_DELIVERY_BY_DATE = " WHERE delivery_date=";
    private static final String FIND_DELIVERY_BY_PERIOD = " WHERE delivery_date>=? AND delivery_date<=?";
    private static final String FIND_DELIVERY_BY_ADDRESS = " WHERE address_id=";
    private static final String UPDATE_DELIVERY_TYPE = "UPDATE deliveries SET delivery_type=? WHERE delivery_id=?";
    private static final String UPDATE_DELIVERY_DATE = "UPDATE deliveries SET delivery_date=? WHERE delivery_id=?";
    private static final String CREATE_DELIVERY = "INSERT INTO deliveries(delivery_type, delivery_time, fk_address_id) VALUES (?, ?, ?)";
    private static final String DELETE_DELIVERY_BY_ID = "DELETE FROM bills WHERE id=";

    private DeliveryDaoImpl() {
    }

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
                Delivery delivery = MAPPER_PROVIDER.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException | ConnectionPoolException e) {
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
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + FIND_DELIVERY_BY_ID + id)) {
            if (resultSet.next()) {
                delivery = MAPPER_PROVIDER.getDeliveryMapper().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
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
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + FIND_DELIVERY_BY_TYPE + deliveryType)) {
            while (resultSet.next()) {
                Delivery delivery = MAPPER_PROVIDER.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException | ConnectionPoolException e) {
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
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + FIND_DELIVERY_BY_ADDRESS + addressDeliveryId)) {
            while (resultSet.next()) {
                Delivery delivery = MAPPER_PROVIDER.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find deliveries by addressDeliveryId=" + addressDeliveryId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return deliveries;
    }

    @Override
    public List<Delivery> findByDateDelivery(LocalDateTime timeDelivery) throws DaoException {
        List<Delivery> deliveries = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DELIVERIES + FIND_DELIVERY_BY_DATE + timeDelivery.format(formatter))) {
            while (resultSet.next()) {
                Delivery delivery = MAPPER_PROVIDER.getDeliveryMapper().createEntity(resultSet);
                deliveries.add(delivery);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find deliveries by dateDelivery\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return deliveries;
    }

    @Override
    public List<Delivery> findByDateDelivery(LocalDateTime startPeriod, LocalDateTime endPeriod) throws DaoException {
        List<Delivery> deliveries = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DELIVERIES + FIND_DELIVERY_BY_PERIOD)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(startPeriod.format(formatter)));
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(endPeriod.format(formatter)));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Delivery delivery = MAPPER_PROVIDER.getDeliveryMapper().createEntity(resultSet);
                    deliveries.add(delivery);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find deliveries by delivery period from " + startPeriod.format(formatter) +
                    " to " + endPeriod.format(formatter) + FAILED_MESSAGE;
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
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"update delivery with id=" + id + " by delivery type=" + deliveryType +
                    FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateDeliveryDate(long id, LocalDateTime deliveryDate) throws DaoException {
        int rowsNumber;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DELIVERY_DATE)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(deliveryDate.format(formatter)));
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"update delivery with id=" + id + " by delivery date=" +
                    deliveryDate.format(formatter) + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(Delivery delivery) throws DaoException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_DELIVERY, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, delivery.getDeliveryType().name());
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(delivery.getDeliveryTime().format(formatter)));
            statement.setLong(THIRD_PARAMETER_INDEX, delivery.getAddressId());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long billId = 0;
                if (resultSet.next()) {
                    billId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
                return billId;
            }
        } catch (SQLException | ConnectionPoolException e) {
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
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete delivery with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}