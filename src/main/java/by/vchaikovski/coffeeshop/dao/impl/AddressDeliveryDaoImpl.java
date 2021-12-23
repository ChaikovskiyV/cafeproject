package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.AddressDeliverDao;
import by.vchaikovski.coffeeshop.dao.mapper.AddressDeliveryMapper;
import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDeliveryDaoImpl implements AddressDeliverDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIND_ALL = "SELECT address_id, street_name, house_number, building_number, " +
            "flat_number FROM address";
    private static final String FIND_ADDRESS_BY_ID = " WHERE address_id=";
    private static final String FIND_ADDRESS_BY_STREET_NAME = " WHERE street_name=";
    private static final String UPDATE_ADDRESS = "UPDATE address SET street_name=?, house_number=?, building_number=?, " +
            "flat_number=? WHERE address_id=?";
    private static final String UPDATE_ADDRESS_STREET_NAME = "UPDATE address SET street_name=? WHERE address_id=?";
    private static final String UPDATE_ADDRESS_HOUSE_NUMBER = "UPDATE address SET house_number=? WHERE address_id=?";
    private static final String UPDATE_ADDRESS_BUILDING_NUMBER = "UPDATE address SET building_number=? WHERE address_id=?";
    private static final String UPDATE_ADDRESS_FLAT_NUMBER = "UPDATE address SET flat_number=? WHERE address_id=?";
    private static final String CREATE_ADDRESS = "INSERT INTO address(street_name, house_number, building_number, flat_number)" +
            "VALUES (?, ?, ?, ?)";
    private static final String DELETE_ADDRESS_BY_ID = "DELETE FROM address WHERE id=";


    @Override
    public List<AddressDelivery> findAll() throws DaoException, ConnectionPoolException {
        List<AddressDelivery> addressDeliveries = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL);
            while (resultSet.next()) {
                AddressDelivery addressDelivery = AddressDeliveryMapper.createAddressDelivery(resultSet);
                addressDeliveries.add(addressDelivery);
            }
        } catch (SQLException e) {
            logger.error("Exception from findAll method. DataBase connection error.", e);
            throw new DaoException("Exception from findAll method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return addressDeliveries;
    }

    @Override
    public Optional<AddressDelivery> findById(long id) throws ConnectionPoolException, DaoException {
        AddressDelivery addressDelivery = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ADDRESS_BY_ID + id);
            if (resultSet.next()) {
                addressDelivery = AddressDeliveryMapper.createAddressDelivery(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findById method. DataBase connection error.", e);
            throw new DaoException("Exception from findById method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return addressDelivery != null ? Optional.of(addressDelivery) : Optional.empty();
    }

    @Override
    public List<AddressDelivery> findByStreetName(String streetName) throws ConnectionPoolException, DaoException {
        List<AddressDelivery> addressDeliveries = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL + FIND_ADDRESS_BY_STREET_NAME + streetName);
            while (resultSet.next()) {
                AddressDelivery addressDelivery = AddressDeliveryMapper.createAddressDelivery(resultSet);
                addressDeliveries.add(addressDelivery);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByStreet method. DataBase connection error.", e);
            throw new DaoException("Exception from findByStreet method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return addressDeliveries;
    }

    @Override
    public boolean update(long id, AddressDelivery addressDelivery) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS)) {
            statement.setString(FIRST_PARAMETER_INDEX, addressDelivery.getStreetName());
            statement.setString(SECOND_PARAMETER_INDEX, addressDelivery.getHouseNumber());
            statement.setInt(THIRD_PARAMETER_INDEX, addressDelivery.getBuildingNumber());
            statement.setInt(FOURTH_PARAMETER_INDEX, addressDelivery.getFlatNumber());
            statement.setLong(FIFTH_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating addressDelivery with id " + id + " can't be executed.", e);
            throw new DaoException("Updating addressDelivery with id " + id + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateAddressDeliveryStreetName(long id, String streetName) throws DaoException, ConnectionPoolException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_STREET_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, streetName);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating addressDelivery with id " + id + " by streetName " + streetName + " can't be executed.", e);
            throw new DaoException("Updating addressDelivery with id " + id + " by streetName " + streetName + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateAddressDeliveryHouseNumber(long id, String houseNumber) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_HOUSE_NUMBER)) {
            statement.setString(FIRST_PARAMETER_INDEX, houseNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating addressDelivery with id " + id + " by houseNumber " + houseNumber + " can't be executed.", e);
            throw new DaoException("Updating addressDelivery with id " + id + " by houseNumber " + houseNumber + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateAddressDeliveryBuildingNumber(long id, int buildingNumber) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_BUILDING_NUMBER)) {
            statement.setInt(FIRST_PARAMETER_INDEX, buildingNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating addressDelivery with id " + id + " by buildingNumber " + buildingNumber + " can't be executed.", e);
            throw new DaoException("Updating addressDelivery with id " + id + " by buildingNumber " + buildingNumber + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateAddressDeliveryFlatNumber(long id, int flatNumber) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_ADDRESS_FLAT_NUMBER)) {
            statement.setInt(FIRST_PARAMETER_INDEX, flatNumber);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating addressDelivery with id " + id + " by flatNumber " + flatNumber + " can't be executed.", e);
            throw new DaoException("Updating addressDelivery with id " + id + " by flatNumber " + flatNumber + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(AddressDelivery addressDelivery) throws ConnectionPoolException, DaoException {
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_ADDRESS, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, addressDelivery.getStreetName());
            statement.setString(SECOND_PARAMETER_INDEX, addressDelivery.getHouseNumber());
            statement.setInt(THIRD_PARAMETER_INDEX, addressDelivery.getBuildingNumber());
            statement.setInt(FOURTH_PARAMETER_INDEX, addressDelivery.getFlatNumber());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            long address_id = 0;
            if (resultSet.next()) {
                address_id = resultSet.getLong(FIRST_PARAMETER_INDEX);
            }
            return address_id;
        } catch (SQLException e) {
            logger.error(() -> "AddressDelivery " + addressDelivery + " can't be added in dataBase. DataBase connection error.", e);
            throw new DaoException("AddressDelivery " + addressDelivery + " can't be added in dataBase. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
    }

    @Override
    public boolean deleteById(long id) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_ADDRESS_BY_ID + id);
        } catch (SQLException e) {
            logger.error("Exception from deleteById method. DataBase connection error.", e);
            throw new DaoException("Exception from deleteById method. DataBase connection error.", e);
        }
        return rowsNumber != 0;
    }
}