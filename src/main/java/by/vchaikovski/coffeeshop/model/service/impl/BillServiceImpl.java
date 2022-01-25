package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.BillDao;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.entity.Bill;
import by.vchaikovski.coffeeshop.model.service.BillService;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class BillServiceImpl implements BillService {
    private static final Logger logger = LogManager.getLogger();
    private static final BillDao billDao = DaoProvider.getInstance().getBillDao();
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:ss";

    @Override
    public long createBill(Map<String, String> billParameters) throws ServiceException {
        long billId = 0;
        DataValidator validator = DataValidatorImpl.getInstance();
        String totalPrice = billParameters.get(TOTAL_PRICE);
        String billStatus = billParameters.get(BILL_STATUS);
        String paymentDate = billParameters.get(PAYMENT_DATE);
        if (validator.isNumberValid(totalPrice) && validator.isEnumContains(billStatus, Bill.BillStatus.class)) {
            Bill.BillStatus status = Bill.BillStatus.valueOf(billStatus.toUpperCase());
            BigDecimal price = new BigDecimal(totalPrice);
            LocalDateTime date = null;
            if (status == Bill.BillStatus.PAID) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                date = validator.isDateTimeValid(paymentDate) ? LocalDateTime.parse(paymentDate)
                        : LocalDateTime.parse(LocalDateTime.now().format(formatter));
            }
            Bill bill = new Bill(status, date, price);
            try {
                billId = billDao.create(bill);
            } catch (DaoException e) {
                String message = "Bill can't be inserted in data base";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        } else {
            if (!validator.isNumberValid(totalPrice)) {
                billParameters.replace(TOTAL_PRICE, WRONG_MEANING);
            }
            if (!validator.isEnumContains(billStatus, Bill.BillStatus.class)) {
                billParameters.replace(BILL_STATUS, WRONG_MEANING);
            }
        }
        return billId;
    }

    @Override
    public boolean deleteBillById(long id) throws ServiceException {
        boolean result;
        try {
            result = billDao.deleteById(id);
        } catch (DaoException e) {
            String message = "Bill can't be inserted in data base";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    @Override
    public List<Bill> findAllBills() throws ServiceException {
        List<Bill> bills;
        try {
            bills = billDao.findAll();
        } catch (DaoException e) {
            String message = "Bills can't be found";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return bills;
    }

    @Override
    public Optional<Bill> findBillById(long billId) throws ServiceException {
        Optional<Bill> optionalBill;
        try {
            optionalBill = billDao.findById(billId);
        } catch (DaoException e) {
            String message = "Bill can't be found by id=" + billId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalBill;
    }

    @Override
    public List<Bill> findBillByStatus(String billStatus) throws ServiceException {
        List<Bill> bills = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(billStatus, Bill.BillStatus.class)) {
            try {
                bills = billDao.findByStatus(Bill.BillStatus.valueOf(billStatus.toUpperCase()));
            } catch (DaoException e) {
                String message = "Bills can't be found by status=" + billStatus;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return bills;
    }

    @Override
    public List<Bill> findBillByPaymentTime(String paymentTime) throws ServiceException {
        List<Bill> bills = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateTimeValid(paymentTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            try {
                bills = billDao.findByPaymentDate(LocalDateTime.parse(paymentTime, formatter));
            } catch (DaoException e) {
                String message = "Bills can't be found by payment time=" + paymentTime;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return bills;
    }

    @Override
    public List<Bill> findBillByPaymentPeriod(String startPeriod, String endPeriod) throws ServiceException {
        List<Bill> bills = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateTimeValid(startPeriod) && validator.isDateTimeValid(endPeriod)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            try {
                LocalDateTime start = LocalDateTime.parse(startPeriod, formatter);
                LocalDateTime finish = LocalDateTime.parse(endPeriod, formatter);
                bills = billDao.findByPaymentDate(start, finish);
            } catch (DaoException e) {
                String message = "Bills can't be found by payment time range=" + startPeriod + " - " + endPeriod;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return bills;
    }

    @Override
    public List<Bill> findBillByPriceRange(String minPrice, String maxPrice) throws ServiceException {
        List<Bill> bills = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(minPrice) && validator.isNumberValid(maxPrice)) {
            try {
                bills = billDao.findBillByPrice(new BigDecimal(minPrice), new BigDecimal(maxPrice));
            } catch (DaoException e) {
                String message = "Bills can't be found by price range=" + minPrice + " - " + maxPrice;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return bills;
    }

    @Override
    public boolean updateBill(long billId, Map<String, String> billParameters) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        String totalPrice = billParameters.get(TOTAL_PRICE);
        String billStatus = billParameters.get(BILL_STATUS);
        String paymentDate = billParameters.get(PAYMENT_DATE);
        if (validator.isNumberValid(totalPrice) && validator.isEnumContains(billStatus, Bill.BillStatus.class)) {
            Bill.BillStatus status = Bill.BillStatus.valueOf(billStatus.toUpperCase());
            BigDecimal price = new BigDecimal(totalPrice);
            LocalDateTime date = null;
            if (status == Bill.BillStatus.PAID) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
                date = validator.isDateTimeValid(paymentDate) ? LocalDateTime.parse(paymentDate)
                        : LocalDateTime.parse(LocalDateTime.now().format(formatter));
            }
            Bill bill = new Bill(status, date, price);
            try {
                result = billDao.update(billId, bill);
            } catch (DaoException e) {
                String message = "Bill can't be updated";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        } else {
            if (!validator.isNumberValid(totalPrice)) {
                billParameters.replace(TOTAL_PRICE, WRONG_MEANING);
            }
            if (!validator.isEnumContains(billStatus, Bill.BillStatus.class)) {
                billParameters.replace(BILL_STATUS, WRONG_MEANING);
            }
        }
        return result;
    }

    @Override
    public boolean updateBillStatus(long billId, String status) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(status, Bill.BillStatus.class)) {
            try {
                result = billDao.updateBillStatus(billId, Bill.BillStatus.valueOf(status.toUpperCase()));
            } catch (DaoException e) {
                String message = "Bill can't be updated by status=" + status;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateBillPaymentTime(long billId, String paymentDate) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateTimeValid(paymentDate)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
            try {
                LocalDateTime dateTime = LocalDateTime.parse(paymentDate, formatter);
                result = billDao.updateBillPaymentDate(billId, dateTime);
            } catch (DaoException e) {
                String message = "Bill can't be updated by payment date=" + paymentDate;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateBillPrice(long billId, String newPrice) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(newPrice)) {
            try {
                result = billDao.updateBillPrice(billId, new BigDecimal(newPrice));
            } catch (DaoException e) {
                String message = "Bill can't be updated by total price=" + newPrice;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }
}