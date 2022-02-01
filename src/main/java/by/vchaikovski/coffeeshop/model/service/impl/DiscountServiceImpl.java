package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.dao.DiscountDao;
import by.vchaikovski.coffeeshop.model.entity.Discount;
import by.vchaikovski.coffeeshop.model.service.DiscountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public class DiscountServiceImpl implements DiscountService {
    private static final Logger logger = LogManager.getLogger();
    private static DiscountService instance;
    private final DiscountDao discountDao;

    private DiscountServiceImpl() {
        discountDao = DaoProvider.getInstance().getDiscountDao();
    }

    public static DiscountService getInstance() {
        if (instance == null) {
            instance = new DiscountServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Discount> findAllDiscounts() throws ServiceException {
        List<Discount> discounts;
        try {
            discounts = discountDao.findAll();
        } catch (DaoException e) {
            String message = "Discounts can't be found";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return discounts;
    }

    @Override
    public Optional<Discount> findDiscountsById(long id) throws ServiceException {
        Optional<Discount> optionalDiscount;
        try {
            optionalDiscount = discountDao.findById(id);
        } catch (DaoException e) {
            String message = "Discount can't be found by id " + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalDiscount;
    }

    @Override
    public Optional<Discount> findDiscountByUserId(long userId) throws ServiceException {
        Optional<Discount> optionalDiscount;
        try {
            optionalDiscount = discountDao.findById(userId);
        } catch (DaoException e) {
            String message = "Discount can't be found by user id " + userId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalDiscount;
    }

    @Override
    public boolean updateDiscountType(long id, Discount.DiscountType discountType) throws ServiceException {
        boolean result;
        try {
            result = discountType != null && discountDao.updateDiscountType(id, discountType);
        } catch (DaoException e) {
            String message = "Discount can't be updated by type " + discountType;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    @Override
    public boolean updateDiscountRate(long id, int rate) throws ServiceException {
        boolean result;
        try {
            result = discountDao.updateDiscountRate(id, rate);
        } catch (DaoException e) {
            String message = "Discount can't be updated by discount rate " + rate;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    @Override
    public long createDiscount(Discount discount) throws ServiceException { //TODO Make validation
        long discountId;
        try {
            discountId = discountDao.create(discount);
        } catch (DaoException e) {
            String message = "Discount can't be inserted in data base";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return discountId;
    }

    @Override
    public boolean deleteDiscountById(long id) throws ServiceException {
        boolean result;
        try {
            result = discountDao.deleteById(id);
        } catch (DaoException e) {
            String message = "Discount can't be deleted";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }
}