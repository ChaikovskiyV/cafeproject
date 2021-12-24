package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.DiscountDao;
import by.vchaikovski.coffeeshop.dao.mapper.DiscountMapper;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiscountDaoImpl implements DiscountDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIND_ALL_DISCOUNTS = "SELECT discount_id, discount_type, rate FROM discounts";
    private static final String FIND_DISCOUNT_BY_ID = " WHERE discount_id=";
    private static final String UPDATE_DISCOUNT_TYPE = "UPDATE discounts SET discount_type=? WHERE discount_id=?";
    private static final String UPDATE_DISCOUNT_RATE = "UPDATE discounts SET rate=? WHERE discount_id=?";
    private static final String CREATE_DISCOUNT = "INSERT INTO discounts(discount_type, rate) VALUES (?, ?)";
    private static final String DELETE_DISCOUNT_BY_ID = "DELETE FROM discounts WHERE id=";

    @Override
    public List<Discount> findAll() throws DaoException, ConnectionPoolException {
        List<Discount> discounts = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL_DISCOUNTS);
            while (resultSet.next()) {
                Discount discount = DiscountMapper.createDiscount(resultSet);
                discounts.add(discount);
            }
        } catch (SQLException e) {
            logger.error("Exception from findAll method. DataBase connection error.", e);
            throw new DaoException("Exception from findAll method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return discounts;
    }

    @Override
    public Optional<Discount> findById(long id) throws ConnectionPoolException, DaoException {
        Discount discount = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL_DISCOUNTS + FIND_DISCOUNT_BY_ID + id);
            if (resultSet.next()) {
                discount = DiscountMapper.createDiscount(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findById method. DataBase connection error.", e);
            throw new DaoException("Exception from findById method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return discount != null ? Optional.of(discount) : Optional.empty();
    }

    @Override
    public long create(Discount discount) throws ConnectionPoolException, DaoException {
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
        } catch (SQLException e) {
            logger.error(() -> "Discount " + discount + " can't be added in dataBase. DataBase connection error.", e);
            throw new DaoException("Discount " + discount + " can't be added in dataBase. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
    }

    @Override
    public boolean update(long id, Discount discount) {
        throw new UnsupportedOperationException("update(long id, Discount discount) method is not supported");
    }

    @Override
    public boolean updateDiscountType(long id, Discount.DiscountType discountType) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DISCOUNT_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, discountType.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating discount with id " + id + " by type " + discountType + " can't be executed.", e);
            throw new DaoException("Updating discount with id " + id + " by type " + discountType + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateDiscountRate(long id, int rate) throws DaoException, ConnectionPoolException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DISCOUNT_RATE)) {
            statement.setInt(FIRST_PARAMETER_INDEX, rate);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating discount with id " + id + " by rate " + rate + " can't be executed.", e);
            throw new DaoException("Updating discount with id " + id + " by rate " + rate + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean deleteById(long id) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_DISCOUNT_BY_ID + id);
        } catch (SQLException e) {
            logger.error("Exception from deleteById method. DataBase connection error.", e);
            throw new DaoException("Exception from deleteById method. DataBase connection error.", e);
        }
        return rowsNumber != 0;
    }
}