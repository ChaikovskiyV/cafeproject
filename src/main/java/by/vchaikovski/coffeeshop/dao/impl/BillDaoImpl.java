package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.BillDao;
import by.vchaikovski.coffeeshop.dao.mapper.BillMapper;
import by.vchaikovski.coffeeshop.entity.Bill;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BillDaoImpl implements BillDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String FIND_ALL_BILLS = "SELECT bill_id, total_price, bill_status, payment_date FROM bills";
    private static final String FIND_BILL_BY_ID = " WHERE bill_id=";
    private static final String FIND_BILL_BY_STATUS = " WHERE bill_status=";
    private static final String FIND_BILL_BY_PAYMENT_DATE = " WHERE payment_date=";
    private static final String FIND_BILL_BY_PAYMENT_PERIOD = " WHERE payment_date>=? AND payment_date<=?";
    private static final String FIND_BILL_BY_PRICE = " WHERE total_price>=? AND total_price<=?";
    private static final String UPDATE_BILL_STATUS = "UPDATE bills SET bill_status=? WHERE bill_id=?";
    private static final String UPDATE_BILL_PAYMENT_DATE = "UPDATE bills SET payment_date=? WHERE bill_id=?";
    private static final String UPDATE_BILL_PRICE = "UPDATE bills SET total_price=? WHERE bill_id=?";
    private static final String CREATE_BILL = "INSERT INTO bills(total_price, bill_status, payment_date) VALUES (?, ?, ?)";
    private static final String DELETE_BILL_BY_ID = "DELETE FROM bills WHERE id=";

    @Override
    public List<Bill> findAll() throws DaoException, ConnectionPoolException {
        List<Bill> bills = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL_BILLS);
            while (resultSet.next()) {
                Bill bill = BillMapper.createBill(resultSet);
                bills.add(bill);
            }
        } catch (SQLException e) {
            logger.error("Exception from findAll method. DataBase connection error.", e);
            throw new DaoException("Exception from findAll method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return bills;
    }

    @Override
    public Optional<Bill> findById(long id) throws ConnectionPoolException, DaoException {
        Bill bill = null;
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_BILL_BY_ID + id);
            if (resultSet.next()) {
                bill = BillMapper.createBill(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Exception from findById method. DataBase connection error.", e);
            throw new DaoException("Exception from findById method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return bill != null ? Optional.of(bill) : Optional.empty();
    }

    @Override
    public List<Bill> findByStatus(Bill.BillStatus billStatus) throws ConnectionPoolException, DaoException {
        List<Bill> bills = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL_BILLS + FIND_BILL_BY_STATUS + billStatus);
            while (resultSet.next()) {
                Bill bill = BillMapper.createBill(resultSet);
                bills.add(bill);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByStatus method. DataBase connection error.", e);
            throw new DaoException("Exception from findByStatus method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return bills;
    }

    @Override
    public List<Bill> findByPaymentDate(LocalDateTime dateTime) throws ConnectionPoolException, DaoException {
        List<Bill> bills = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(FIND_ALL_BILLS + FIND_BILL_BY_PAYMENT_DATE + dateTime);
            while (resultSet.next()) {
                Bill bill = BillMapper.createBill(resultSet);
                bills.add(bill);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByPaymentDate method. DataBase connection error.", e);
            throw new DaoException("Exception from findByPaymentDate method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return bills;
    }

    @Override
    public List<Bill> findByPaymentDate(LocalDateTime startPeriod, LocalDateTime endPeriod) throws ConnectionPoolException, DaoException {
        List<Bill> bills = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BILLS + FIND_BILL_BY_PAYMENT_PERIOD)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(startPeriod.toLocalDate()));
            statement.setDate(SECOND_PARAMETER_INDEX, Date.valueOf(endPeriod.toLocalDate()));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bill bill = BillMapper.createBill(resultSet);
                bills.add(bill);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByPaymentDate method. DataBase connection error.", e);
            throw new DaoException("Exception from findByPaymentDate method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return bills;
    }

    @Override
    public List<Bill> findBillByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws ConnectionPoolException, DaoException {
        List<Bill> bills = new ArrayList<>();
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_BILLS + FIND_BILL_BY_PRICE)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, minPrice);
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, maxPrice);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Bill bill = BillMapper.createBill(resultSet);
                bills.add(bill);
            }
        } catch (SQLException e) {
            logger.error("Exception from findByPrice method. DataBase connection error.", e);
            throw new DaoException("Exception from findByPrice method. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
        return bills;
    }

    @Override
    public boolean updateBillStatus(long id, Bill.BillStatus status) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_STATUS)) {
            statement.setString(FIRST_PARAMETER_INDEX, status.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating bill with id " + id + " by status " + status + " can't be executed.", e);
            throw new DaoException("Updating bill with id " + id + " by status " + status + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateBillPaymentDate(long id, LocalDateTime paymentDate) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_PAYMENT_DATE)) {
            statement.setDate(FIRST_PARAMETER_INDEX, Date.valueOf(paymentDate.toLocalDate()));
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating bill with id " + id + " by paymentDate " + paymentDate + " can't be executed.", e);
            throw new DaoException("Updating bill with id " + id + " by paymentDate " + paymentDate + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public boolean updateBillPrice(long id, BigDecimal newPrice) throws DaoException, ConnectionPoolException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_BILL_PRICE)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, newPrice);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            rowsNumber = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(() -> "Updating bill with id " + id + " by price " + newPrice + " can't be executed.", e);
            throw new DaoException("Updating bill with id " + id + " by price " + newPrice + " can't be executed.", e);
        }
        return rowsNumber != 0;
    }

    @Override
    public long create(Bill bill) throws ConnectionPoolException, DaoException {
        ResultSet resultSet = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_BILL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, bill.getTotalPrice());
            statement.setString(SECOND_PARAMETER_INDEX, bill.getStatus().name());
            statement.setDate(THIRD_PARAMETER_INDEX, Date.valueOf(bill.getPaymentDate().toLocalDate()));
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            long billId = 0;
            if (resultSet.next()) {
                billId = resultSet.getLong(FIRST_PARAMETER_INDEX);
            }
            return billId;
        } catch (SQLException e) {
            logger.error(() -> "Bill " + bill + " can't be added in dataBase. DataBase connection error.", e);
            throw new DaoException("Bill " + bill + " can't be added in dataBase. DataBase connection error.", e);
        } finally {
            close(resultSet);
        }
    }

    @Override
    public boolean update(long id, Bill bill) {
        throw new UnsupportedOperationException("update(long id, Bill bill) method is not supported");
    }

    @Override
    public boolean deleteById(long id) throws ConnectionPoolException, DaoException {
        int rowsNumber;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            rowsNumber = statement.executeUpdate(DELETE_BILL_BY_ID + id);
        } catch (SQLException e) {
            logger.error("Exception from deleteById method. DataBase connection error.", e);
            throw new DaoException("Exception from deleteById method. DataBase connection error.", e);
        }
        return rowsNumber != 0;
    }
}