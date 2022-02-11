package by.vchaikovski.coffeehouse.model.dao.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.DiscountDao;
import by.vchaikovski.coffeehouse.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeehouse.model.entity.Discount;
import by.vchaikovski.coffeehouse.model.pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Discount dao.
 */

public class DiscountDaoImpl implements DiscountDao {
    private static final DiscountDaoImpl instance = new DiscountDaoImpl();
    private static final MapperProvider mapperProvider = MapperProvider.getInstance();
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String UPDATE_MESSAGE = "The query \"update discount with id=";
    private static final String FIND_ALL_DISCOUNTS = "SELECT discount_id, type, rate FROM discounts";
    private static final String BY_ID = " WHERE discount_id=";
    private static final String BY_TYPE_AND_RATE = " WHERE type=? AND rate=?";
    private static final String BY_TYPE = " WHERE type=?";
    private static final String BY_RATE = " WHERE rate=";
    private static final String FIND_DISCOUNT_BY_USER_ID = "SELECT user_id FROM users" +
            "JOIN discounts ON fk_discount_id=discount_id WHERE user_id=";
    private static final String UPDATE_DISCOUNT_TYPE = "UPDATE discounts SET type=? WHERE discount_id=?";
    private static final String UPDATE_DISCOUNT_RATE = "UPDATE discounts SET rate=? WHERE discount_id=?";
    private static final String CREATE_DISCOUNT = "INSERT INTO discounts(type, rate) VALUES (?, ?)";
    private static final String DELETE_DISCOUNT_BY_ID = "DELETE FROM discounts WHERE id=";

    private DiscountDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DiscountDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Discount> findAll() throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DISCOUNTS)) {
            while (resultSet.next()) {
                Discount discount = mapperProvider.getDiscountMapper().createEntity(resultSet);
                discounts.add(discount);
            }
        } catch (SQLException e) {
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
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DISCOUNTS + BY_ID + id)) {
            if (resultSet.next()) {
                discount = mapperProvider.getDiscountMapper().createEntity(resultSet);
            }
        } catch (SQLException e) {
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
    public Optional<Discount> findByTypeAndRate(Discount.DiscountType discountType, int rate) throws DaoException {
        Discount discount = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DISCOUNTS + BY_TYPE_AND_RATE)) {
            statement.setString(FIRST_PARAMETER_INDEX, discountType.name());
            statement.setInt(SECOND_PARAMETER_INDEX, rate);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    discount = mapperProvider.getDiscountMapper().createEntity(resultSet);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find discount by discount type=" + discountType +
                    " and rate=" + rate + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(discount);
    }

    @Override
    public List<Discount> findByType(Discount.DiscountType discountType) throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_DISCOUNTS + BY_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, discountType.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Discount discount = mapperProvider.getDiscountMapper().createEntity(resultSet);
                    discounts.add(discount);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find discounts\" by discount type=" + discountType + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return discounts;
    }

    @Override
    public List<Discount> findByRate(int rate) throws DaoException {
        List<Discount> discounts = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_DISCOUNTS + BY_RATE + rate)) {
            while (resultSet.next()) {
                Discount discount = mapperProvider.getDiscountMapper().createEntity(resultSet);
                discounts.add(discount);
            }
        } catch (SQLException e) {
            String message = "The query \"find discounts\" by discount rate=" + rate + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return discounts;
    }

    @Override
    public Optional<Discount> findByUserId(long userId) throws DaoException {
        Discount discount = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_DISCOUNT_BY_USER_ID + userId)) {
            if (resultSet.next()) {
                discount = mapperProvider.getDiscountMapper().createEntity(resultSet);
            }
        } catch (SQLException e) {
            String message = "The query \"find discount by userId=" + userId + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(discount);
    }

    @Override
    public boolean updateDiscountType(long id, Discount.DiscountType discountType) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_DISCOUNT_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, discountType.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
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
        } catch (SQLException e) {
            String message = UPDATE_MESSAGE + id + " by rate=" + rate + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(Discount discount) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_DISCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, discount.getType().name());
            statement.setInt(SECOND_PARAMETER_INDEX, discount.getRate());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long discountId = 0;
                if (resultSet.next()) {
                    discountId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
                return discountId;
            }
        } catch (SQLException e) {
            String message = "The query \"create discount " + discount + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_DISCOUNT_BY_ID + id);
        } catch (SQLException e) {
            String message = "The query \"delete discount with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}