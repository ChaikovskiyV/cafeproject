package by.vchaikovski.coffeeshop.model.dao.impl;

import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.BankCardDao;
import by.vchaikovski.coffeeshop.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeeshop.model.entity.BankCard;
import by.vchaikovski.coffeeshop.model.pool.ConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankCardDaoImpl implements BankCardDao {
    private static final BankCardDaoImpl instance = new BankCardDaoImpl();
    private static final MapperProvider MAPPER_PROVIDER = MapperProvider.getInstance();
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String UPDATE_MESSAGE = "The query \"update bankCard with id=";
    private static final String FIND_ALL_CARDS = "SELECT card_id, number, card_expiration_date, amount FROM cards";
    private static final String FIND_CARD_BY_ID = " WHERE id=";
    private static final String FIND_CARD_BY_NUMBER = " WHERE number=?";
    private static final String FIND_CARD_BY_NUMBER_AND_EXPIRATION_DATE = " WHERE number=? AND expiration_date=?";
    private static final String CREATE_CARD = "INSERT INTO cards(number, card_expiration_date, amount) VALUES (?, ?, ?)";
    private static final String UPDATE_CARD_AMOUNT = "UPDATE cards SET amount=? WHERE card_id=?";
    private static final String DELETE_CARD_BY_ID = "DELETE FROM cards WHERE id=";

    private BankCardDaoImpl() {
    }

    public static BankCardDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<BankCard> findAll() throws DaoException {
        List<BankCard> cards = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_CARDS)) {
            while (resultSet.next()) {
                BankCard card = MAPPER_PROVIDER.getBankCardMapper().createEntity(resultSet);
                cards.add(card);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find all bankCards\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return cards;
    }

    @Override
    public Optional<BankCard> findById(long id) throws DaoException {
        BankCard card = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_CARDS + FIND_CARD_BY_ID + id)) {
            if (resultSet.next()) {
                card = MAPPER_PROVIDER.getBankCardMapper().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find bankCard by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(card);
    }

    @Override
    public Optional<BankCard> findByCardNumber(String cardNumber) throws DaoException {
        BankCard card = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_CARDS + FIND_CARD_BY_NUMBER)) {
            statement.setString(FIRST_PARAMETER_INDEX, cardNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card = MAPPER_PROVIDER.getBankCardMapper().createEntity(resultSet);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find bankCard by cardNumber=" + cardNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(card);
    }

    @Override
    public Optional<BankCard> findByCardNumberAndExpirationDate(String cardNumber, LocalDate expirationDate) throws DaoException {
        BankCard card = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_CARDS + FIND_CARD_BY_NUMBER_AND_EXPIRATION_DATE)) {
            statement.setString(FIRST_PARAMETER_INDEX, cardNumber);
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(expirationDate));
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    card = MAPPER_PROVIDER.getBankCardMapper().createEntity(resultSet);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find bankCard by expiration date=" + expirationDate +
                    " and card number=" + cardNumber + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(card);
    }

    @Override
    public boolean update(long id, BankCard entity) {
        throw new UnsupportedOperationException("The update(long id, BankCard bankCard) method is not supported");
    }

    @Override
    public boolean updateBankCardAmount(long id, BigDecimal amount) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CARD_AMOUNT)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, amount);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by amount=" + amount + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(BankCard bankCard) throws DaoException {
        long cardId = 0;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_CARD, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, bankCard.getCardNumber());
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(bankCard.getExpirationDate()));
            statement.setBigDecimal(THIRD_PARAMETER_INDEX, bankCard.getAmount());
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    cardId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
            }
            return cardId;
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"create bankCard " + bankCard + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_CARD_BY_ID + id);
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete bankCard with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}