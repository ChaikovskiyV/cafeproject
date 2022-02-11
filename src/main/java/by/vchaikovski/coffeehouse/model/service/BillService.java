package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Bill;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Bill service.
 */
public interface BillService {

    /**
     * Create bill long.
     *
     * @param billParameters the bill parameters
     * @return the long
     * @throws ServiceException the service exception
     */
    long createBill(Map<String, String> billParameters) throws ServiceException;

    /**
     * Delete bill by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteBillById(long id) throws ServiceException;

    /**
     * Find all bills list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bill> findAllBills() throws ServiceException;

    /**
     * Find bill by id optional.
     *
     * @param billId the bill id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Bill> findBillById(long billId) throws ServiceException;

    /**
     * Find bill by status list.
     *
     * @param billStatus the bill status
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bill> findBillByStatus(String billStatus) throws ServiceException;

    /**
     * Find bill by payment time list.
     *
     * @param paymentTime the payment time
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bill> findBillByPaymentTime(String paymentTime) throws ServiceException;

    /**
     * Find bill by payment period list.
     *
     * @param startPeriod the start period
     * @param endPeriod   the end period
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bill> findBillByPaymentPeriod(String startPeriod, String endPeriod) throws ServiceException;

    /**
     * Find bill by price range list.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Bill> findBillByPriceRange(String minPrice, String maxPrice) throws ServiceException;

    /**
     * Update bill boolean.
     *
     * @param billId         the bill id
     * @param billParameters the bill parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateBill(long billId, Map<String, String> billParameters) throws ServiceException;

    /**
     * Update bill status boolean.
     *
     * @param id     the id
     * @param status the status
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateBillStatus(long id, Bill.BillStatus status) throws ServiceException;

    /**
     * Update bill payment time boolean.
     *
     * @param id          the id
     * @param paymentDate the payment date
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateBillPaymentTime(long id, String paymentDate) throws ServiceException;

    /**
     * Update bill price boolean.
     *
     * @param id       the id
     * @param newPrice the new price
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateBillPrice(long id, String newPrice) throws ServiceException;
}