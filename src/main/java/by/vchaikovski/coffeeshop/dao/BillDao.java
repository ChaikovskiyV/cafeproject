package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.Bill;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BillDao extends BaseDao<Bill> {
    List<Bill> findByStatus(Bill.BillStatus billStatus) throws ConnectionPoolException, DaoException;

    List<Bill> findByPaymentDate(LocalDateTime dateTime) throws ConnectionPoolException, DaoException;

    List<Bill> findByPaymentDate(LocalDateTime startPeriod, LocalDateTime endPeriod) throws ConnectionPoolException, DaoException;

    List<Bill> findBillByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws ConnectionPoolException, DaoException;

    boolean updateBillStatus(long id, Bill.BillStatus status) throws ConnectionPoolException, DaoException;

    boolean updateBillPaymentDate(long id, LocalDateTime paymentDate) throws ConnectionPoolException, DaoException;

    boolean updateBillPrice(long id, BigDecimal newPrice) throws DaoException, ConnectionPoolException;
}
