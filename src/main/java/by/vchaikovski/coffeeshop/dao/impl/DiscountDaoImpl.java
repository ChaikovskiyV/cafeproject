package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.DiscountDao;
import by.vchaikovski.coffeeshop.dao.mapper.impl.DiscountMapperImpl;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscountDaoImpl implements DiscountDao {
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String UPDATE_MESSAGE = "The query \"update discount with id=";
    private static final String FIND_ALL_DISCOUNTS = "SELECT discount_id, discount_type, rate FROM discounts";
    private static final String FIND_DISCOUNT_BY_ID = " WHERE discount_id=";
    private static final String UPDATE_DISCOUNT_TYPE = "UPDATE discounts SET discount_type=? WHERE discount_id=?";
    private static final String UPDATE_DISCOUNT_RATE = "UPDATE discounts SET rate=? WHERE discount_id=?";
    private static final String CREATE_DISCOUNT = "INSERT INTO discounts(discount_type, rate) VALUES (?, ?)";
    private static final String DELETE_DISCOUNT_BY_ID = "DELETE FROM discounts WHERE id=";

    @Override
    public List<Discount> findAll() throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DISCOUNTS)) {
            while (resultSet.next()) {
                Discount discount = new DiscountMapperImpl().createEntity(resultSet);
                discounts.add(discount);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find all discounts\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return discounts;
    }

    @Override
    public Optional<Discount> findById(long id) throws DaoException {
        Discount discount = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DISCOUNTS + FIND_DISCOUNT_BY_ID + id)) {
            if (resultSet.next()) {
                discount = new DiscountMapperImpl().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find discount by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(discount);
    }


    @Override
    public boolean update(long id, Discount discount) {
        throw new UnsupportedOperationException("The update(long id, Discount discount) method is not supported");
    }

    @Override
    public boolean updateDiscountType(long id, Discount.DiscountType discountType) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DISCOUNT_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, discountType.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by type=" + discountType + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);

        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateDiscountRate(long id, int rate) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DISCOUNT_RATE)) {
            statement.setInt(FIRST_PARAMETER_INDEX, rate);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by rate=" + rate + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(Discount discount) throws DaoException {
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_DISCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, discount.getType().name());
            statement.setInt(SECOND_PARAMETER_INDEX, discount.getRate());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            long discountId = 0;
            if (resultSet.next()) {
                discountId = resultSet.getLong(FIRST_PARAMETER_INDEX);
            }
            return discountId;
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"create discount " + discount + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        } finally {
            close(resultSet);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_DISCOUNT_BY_ID + id);
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete discount with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}