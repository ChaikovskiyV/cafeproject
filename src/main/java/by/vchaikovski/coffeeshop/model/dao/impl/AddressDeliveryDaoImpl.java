package by.vchaikovski.coffeeshop.model.dao.impl;

import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.AddressDeliveryDao;
import by.vchaikovski.coffeeshop.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeeshop.model.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.model.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDeliveryDaoImpl implements AddressDeliveryDao {
    private static final AddressDeliveryDaoImpl instance = new AddressDeliveryDaoImpl();
    private static final MapperProvider mapperProvider = MapperProvider.getInstance();
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String UPDATE_MESSAGE = "The query \"update addressDelivery with id=";
    private static final String FIND_ALL = "SELECT address_id, street_name, house_number, building_number, " +
            "flat_number FROM address";
    private static final String FIND_ADDRESS_BY_ID = " WHERE address_id=";
    private static final String FIND_ADDRESS_BY_STREET_NAME = " WHERE street_name=?";
    private static final String UPDATE_ADDRESS_STREET_NAME = "UPDATE address SET street_name=? WHERE address_id=?";
    private static final String UPDATE_ADDRESS_HOUSE_NUMBER = "UPDATE address SET house_number=? WHERE address_id=?";
    private static final String UPDATE_ADDRESS_BUILDING_NUMBER = "UPDATE address SET building_number=? WHERE address_id=?";
    private static final String UPDATE_ADDRESS_FLAT_NUMBER = "UPDATE address SET flat_number=? WHERE address_id=?";
    private static final String CREATE_ADDRESS = "INSERT INTO address(street_name, house_number, building_number, flat_number)" +
            "VALUES (?, ?, ?, ?)";
    private static final String DELETE_ADDRESS_BY_ID = "DELETE FROM address WHERE id=";

    private AddressDeliveryDaoImpl() {
    }

    public static AddressDeliveryDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<AddressDelivery> findAll() throws DaoException {
        List<AddressDelivery> addressDeliveries = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL)) {
            while (resultSet.next()) {
                AddressDelivery addressDelivery = mapperProvider.getAddressDeliveryMapper().createEntity(resultSet);
                addressDeliveries.add(addressDelivery);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find all addressDelivery\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return addressDeliveries;
    }

    @Override
    public Optional<AddressDelivery> findById(long id) throws DaoException {
        AddressDelivery addressDelivery = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL + FIND_ADDRESS_BY_ID + id)) {

            if (resultSet.next()) {
                addressDelivery = mapperProvider.getAddressDeliveryMapper().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find delivery by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(addressDelivery);
    }

    @Override
    public List<AddressDelivery> findByStreetName(String streetName) throws DaoException {
        List<AddressDelivery> addressDeliveries = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL + FIND_ADDRESS_BY_STREET_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, streetName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    AddressDelivery addressDelivery = mapperProvider.getAddressDeliveryMapper().createEntity(resultSet);
                    addressDeliveries.add(addressDelivery);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find delivery by streetName=" + streetName + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return addressDeliveries;
    }

    @Override
    public boolean update(long id, AddressDelivery addressDelivery) {
        throw new UnsupportedOperationException(" The update(long id, AddressDelivery addressDelivery) method is unsupported.");
    }

    @Override
    public boolean updateAddressDeliveryStreetName(long id, String streetName) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_STREET_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, streetName);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by streetName=" + streetName + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateAddressDeliveryHouseNumber(long id, String houseNumber) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_HOUSE_NUMBER)) {
            statement.setString(FIRST_PARAMETER_INDEX, houseNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by houseNumber=" + houseNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateAddressDeliveryBuildingNumber(long id, int buildingNumber) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_BUILDING_NUMBER)) {
            statement.setInt(FIRST_PARAMETER_INDEX, buildingNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by buildingNumber=" + buildingNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateAddressDeliveryFlatNumber(long id, int flatNumber) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_FLAT_NUMBER)) {
            statement.setInt(FIRST_PARAMETER_INDEX, flatNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by flatNumber=" + flatNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(AddressDelivery addressDelivery) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, addressDelivery.getStreetName());
            statement.setString(SECOND_PARAMETER_INDEX, addressDelivery.getHouseNumber());
            statement.setInt(THIRD_PARAMETER_INDEX, addressDelivery.getBuildingNumber());
            statement.setInt(FOURTH_PARAMETER_INDEX, addressDelivery.getFlatNumber());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long addressId = 0;
                if (resultSet.next()) {
                    addressId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
                return addressId;
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"create addressDelivery " + addressDelivery + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_ADDRESS_BY_ID + id);
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete addressDelivery with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}