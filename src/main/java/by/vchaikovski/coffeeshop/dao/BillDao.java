package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.Bill;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface BillDao extends BaseDao<Bill> {
    List<Bill> findByStatus(Bill.BillStatus billStatus) throws DaoException;

    List<Bill> findByPaymentDate(LocalDateTime dateTime) throws DaoException;

    List<Bill> findByPaymentDate(LocalDateTime startPeriod, LocalDateTime endPeriod) throws DaoException;

    List<Bill> findBillByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws DaoException;

    boolean updateBillStatus(long id, Bill.BillStatus status) throws DaoException;

    boolean updateBillPaymentDate(long id, LocalDateTime paymentDate) throws DaoException;

    boolean updateBillPrice(long id, BigDecimal newPrice) throws DaoException;
}