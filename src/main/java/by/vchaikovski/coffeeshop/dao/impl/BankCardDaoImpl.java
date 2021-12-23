package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.BankCardDao;
import by.vchaikovski.coffeeshop.dao.mapper.BankCardMapper;
import by.vchaikovski.coffeeshop.entity.BankCard;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankCardDaoImpl implements BankCardDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIND_ALL_CARDS = "SELECT card_id, number, validity_period, amount FROM cards";
    private static final String FIND_CARD_BY_ID = " WHERE id=";
    private static final String FIND_CARD_BY_NUMBER = " WHERE number=";
    private static final String CREATE_CARD = "INSERT INTO cards(number, expiration_date, amount) VALUES (?, ?, ?)";
    private static final String UPDATE_CARD_AMOUNT = "UPDATE cards SET amount=? WHERE card_id=?";
    private static final String DELETE_CARD_BY_ID = "DELETE FROM cards WHERE id=";


    @Override
    public List<BankCard> findAll() throws DaoException, ConnectionPoolException {
        List<BankCard> cards = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL_CARDS);
            while (resultSet.next()) {
                BankCard card = BankCardMapper.createCard(resultSet);
                cards.add(card);
            }
        } catch (SQLException e) {
            logger.error("Exception from findAll method. DataBase connection error.", e);
            throw new DaoException("Exception from findAll method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return cards;
    }

    @Override
    public Optional<BankCard> findById(long id) throws ConnectionPoolException, DaoException {
        BankCard card = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_CARD_BY_ID + id);
            if (resultSet.next()) {
                card = BankCardMapper.createCard(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findById method. DataBase connection error.", e);
            throw new DaoException("Exception from findById method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return card != null ? Optional.of(card) : Optional.empty();
    }

    @Override
    public Optional<BankCard> findByCardNumber(String cardNumber) throws DaoException, ConnectionPoolException {
        BankCard card = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_CARD_BY_NUMBER + cardNumber);
            if (resultSet.next()) {
                card = BankCardMapper.createCard(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findById method. DataBase connection error.", e);
            throw new DaoException("Exception from findById method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return card != null ? Optional.of(card) : Optional.empty();
    }

    @Override
    public long create(BankCard bankCard) throws ConnectionPoolException, DaoException {
        long cardId = 0;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_CARD, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, bankCard.getCardNumber());
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(bankCard.getExpirationDate()));
            statement.setBigDecimal(THIRD_PARAMETER_INDEX, bankCard.getAmount());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                cardId = resultSet.getLong(FIRST_PARAMETER_INDEX);
            }
            return cardId;
        } catch (SQLException e) {
            logger.error(() -> "BankCard " + bankCard + " can't be added in dataBase. DataBase connection error.", e);
            throw new DaoException("BankCard " + bankCard + " can't be added in dataBase. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
    }

    @Override
    public boolean update(long id, BankCard entity) {
        throw new UnsupportedOperationException("update(long id, BankCard bankCard) method is not supported");
    }

    @Override
    public boolean updateBankCardAmount(long id, BigDecimal amount) throws DaoException, ConnectionPoolException {
        int rowsNumber = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CARD_AMOUNT)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, amount);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating bankCard with id " + id + " by amount " + amount + " can't be executed.", e);
            throw new DaoException("Updating bankCard with id " + id + " by amount " + amount + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean deleteById(long id) throws ConnectionPoolException, DaoException {
        int rowsNumber = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_CARD_BY_ID + id);
        } catch (SQLException e) {
            logger.error("Exception from deleteById method. DataBase connection error.", e);
            throw new DaoException("Exception from deleteById method. DataBase connection error.", e);
        }
        return rowsNumber != 0;
    }
}