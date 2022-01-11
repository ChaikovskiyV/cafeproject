package by.vchaikovski.coffeeshop.model.dao.impl;

import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.dao.BillDao;
import by.vchaikovski.coffeeshop.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeeshop.model.entity.Bill;
import by.vchaikovski.coffeeshop.model.pool.ConnectionPool;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillDaoImpl implements BillDao {
    private static final BillDaoImpl instance = new BillDaoImpl();
    private static final MapperProvider MAPPER_PROVIDER = MapperProvider.getInstance();
    private static final String UPDATE_MESSAGE = "The query \"update bill with id=";
    private static final String FIND_ALL_BILLS = "SELECT bill_id, total_price, bill_status, payment_date FROM bills";
    private static final String FIND_BILL_BY_ID = " WHERE bill_id=";
    private static final String FIND_BILL_BY_STATUS = " WHERE bill_status=?";
    private static final String FIND_BILL_BY_PAYMENT_DATE = " WHERE payment_date=?";
    private static final String FIND_BILL_BY_PAYMENT_PERIOD = " WHERE payment_date>=? AND payment_date<=?";
    private static final String FIND_BILL_BY_PRICE = " WHERE total_price>=? AND total_price<=?";
    private static final String UPDATE_BILL_STATUS = "UPDATE bills SET bill_status=? WHERE bill_id=?";
    private static final String UPDATE_BILL_PAYMENT_DATE = "UPDATE bills SET payment_date=? WHERE bill_id=?";
    private static final String UPDATE_BILL_PRICE = "UPDATE bills SET total_price=? WHERE bill_id=?";
    private static final String CREATE_BILL = "INSERT INTO bills(total_price, bill_status, payment_date) VALUES (?, ?, ?)";
    private static final String DELETE_BILL_BY_ID = "DELETE FROM bills WHERE id=";

    private BillDaoImpl() {
    }

    public static BillDaoImpl getInstance() {
        return instance;
    }

    @Override
    public List<Bill> findAll() throws DaoException {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_BILLS)) {
            while (resultSet.next()) {
                Bill bill = MAPPER_PROVIDER.getBillMapper().createEntity(resultSet);
                bills.add(bill);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find all bills\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bills;
    }

    @Override
    public Optional<Bill> findById(long id) throws DaoException {
        Bill bill = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_BILLS + FIND_BILL_BY_ID + id)) {
            if (resultSet.next()) {
                bill = MAPPER_PROVIDER.getBillMapper().createEntity(resultSet);
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find a bill by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(bill);
    }

    @Override
    public List<Bill> findByStatus(Bill.BillStatus billStatus) throws DaoException {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BILLS + FIND_BILL_BY_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, billStatus.name());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = MAPPER_PROVIDER.getBillMapper().createEntity(resultSet);
                    bills.add(bill);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find bills by status=" + billStatus + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bills;
    }

    @Override
    public List<Bill> findByPaymentDate(LocalDateTime paymentDate) throws DaoException {
        List<Bill> bills = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BILLS + FIND_BILL_BY_PAYMENT_DATE)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(paymentDate.format(timeFormatter)));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = MAPPER_PROVIDER.getBillMapper().createEntity(resultSet);
                    bills.add(bill);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find bills by paymentDate=" + paymentDate.format(timeFormatter) + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bills;
    }

    @Override
    public List<Bill> findByPaymentDate(LocalDateTime startPeriod, LocalDateTime endPeriod) throws DaoException {
        List<Bill> bills = new ArrayList<>();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BILLS + FIND_BILL_BY_PAYMENT_PERIOD)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(startPeriod.format(timeFormatter)));
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(endPeriod.format(timeFormatter)));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = MAPPER_PROVIDER.getBillMapper().createEntity(resultSet);
                    bills.add(bill);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find bills by paymentPeriod from " + startPeriod.format(timeFormatter) +
                    " to " + endPeriod.format(timeFormatter) + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bills;
    }

    @Override
    public List<Bill> findBillByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws DaoException {
        List<Bill> bills = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BILLS + FIND_BILL_BY_PRICE)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, minPrice);
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, maxPrice);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Bill bill = MAPPER_PROVIDER.getBillMapper().createEntity(resultSet);
                    bills.add(bill);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"find bills by price from " + minPrice + " to " + maxPrice + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return bills;
    }

    @Override
    public boolean update(long id, Bill bill) {
        throw new UnsupportedOperationException("The update(long id, Bill bill) method is not supported");
    }

    @Override
    public boolean updateBillStatus(long id, Bill.BillStatus status) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, status.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by status=" + status.name() + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateBillPaymentDate(long id, LocalDateTime paymentDate) throws DaoException {
        int rowsNumber;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_PAYMENT_DATE)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(paymentDate.format(timeFormatter)));
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by paymentDate=" + paymentDate.format(timeFormatter) + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateBillPrice(long id, BigDecimal newPrice) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_PRICE)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, newPrice);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException | ConnectionPoolException e) {
            String message = UPDATE_MESSAGE + id + " by price=" + newPrice + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(Bill bill) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_BILL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, bill.getTotalPrice());
            statement.setString(SECOND_PARAMETER_INDEX, bill.getStatus().name());
            statement.setDate(THIRD_PARAMETER_INDEX, Date.valueOf(bill.getPaymentDate().toLocalDate()));
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long billId = 0;
                if (resultSet.next()) {
                    billId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
                return billId;
            }
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"create bill " + bill + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_BILL_BY_ID + id);
        } catch (SQLException | ConnectionPoolException e) {
            String message = "The query \"delete bill with id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return rowsNumber != 0;
    }
}