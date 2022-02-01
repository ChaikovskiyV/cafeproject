package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.Bill;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BillService {

    long createBill(Map<String, String> billParameters) throws ServiceException;

    boolean deleteBillById(long id) throws ServiceException;

    List<Bill> findAllBills() throws ServiceException;

    Optional<Bill> findBillById(long billId) throws ServiceException;

    List<Bill> findBillByStatus(String billStatus) throws ServiceException;

    List<Bill> findBillByPaymentTime(String paymentTime) throws ServiceException;

    List<Bill> findBillByPaymentPeriod(String startPeriod, String endPeriod) throws ServiceException;

    List<Bill> findBillByPriceRange(String minPrice, String maxPrice) throws ServiceException;

    boolean updateBill(long billId, Map<String, String> billParameters) throws ServiceException;

    boolean updateBillStatus(long id, Bill.BillStatus status) throws ServiceException;

    boolean updateBillPaymentTime(long id, String paymentDate) throws ServiceException;

    boolean updateBillPrice(long id, String newPrice) throws ServiceException;
}