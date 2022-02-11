package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.Bill;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Bill dao.
 */
public interface BillDao extends BaseDao<Bill> {
    /**
     * Find by status list.
     *
     * @param billStatus the bill status
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bill> findByStatus(Bill.BillStatus billStatus) throws DaoException;

    /**
     * Find by payment date list.
     *
     * @param dateTime the date time
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bill> findByPaymentDate(LocalDate dateTime) throws DaoException;

    /**
     * Find by payment date list.
     *
     * @param startPeriod the start period
     * @param endPeriod   the end period
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bill> findByPaymentDate(LocalDate startPeriod, LocalDate endPeriod) throws DaoException;

    /**
     * Find bill by price list.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Bill> findBillByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws DaoException;

    /**
     * Update bill status boolean.
     *
     * @param id     the id
     * @param status the status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateBillStatus(long id, Bill.BillStatus status) throws DaoException;

    /**
     * Update bill payment date boolean.
     *
     * @param id          the id
     * @param paymentDate the payment date
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateBillPaymentDate(long id, LocalDate paymentDate) throws DaoException;

    /**
     * Update bill price boolean.
     *
     * @param id       the id
     * @param newPrice the new price
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateBillPrice(long id, BigDecimal newPrice) throws DaoException;
}