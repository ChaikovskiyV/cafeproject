package by.vchaikovski.coffeehouse.model.service.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.dao.DaoProvider;
import by.vchaikovski.coffeehouse.model.dao.DiscountDao;
import by.vchaikovski.coffeehouse.model.entity.Discount;
import by.vchaikovski.coffeehouse.model.service.DiscountService;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.FormValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import by.vchaikovski.coffeehouse.util.validator.impl.FormValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.DISCOUNT_ID;
import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.DISCOUNT_RATE;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Discount service.
 */
public class DiscountServiceImpl implements DiscountService {
    private static final Logger logger = LogManager.getLogger();
    private static DiscountService instance;
    private final DiscountDao discountDao;

    private DiscountServiceImpl() {
        discountDao = DaoProvider.getInstance().getDiscountDao();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
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
    public List<Discount> findDiscountsByRate(String rate) throws ServiceException {
        List<Discount> discounts = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(rate)) {
            try {
                discounts = discountDao.findByRate(Integer.parseInt(rate));
            } catch (DaoException e) {
                String message = "Discounts can't be found by rate=" + rate;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return discounts;
    }

    @Override
    public List<Discount> findDiscountsByType(String discountType) throws ServiceException {
        List<Discount> discounts = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(discountType, Discount.DiscountType.class)) {
            Discount.DiscountType type = Discount.DiscountType.valueOf(discountType.toUpperCase());
            try {
                discounts = discountDao.findByType(type);
            } catch (DaoException e) {
                String message = "Discounts can't be found by discount type=" + discountType;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return discounts;
    }

    @Override
    public boolean updateDiscountType(long id, String discountType) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(discountType, Discount.DiscountType.class)) {
            Discount.DiscountType type = Discount.DiscountType.valueOf(discountType.toUpperCase());
            try {
                result = discountDao.updateDiscountType(id, type);
            } catch (DaoException e) {
                String message = "Discount can't be updated by type " + discountType;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateDiscountRate(long id, String rate) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDiscountRateValid(rate)) {
            try {
                result = discountDao.updateDiscountRate(id, Integer.parseInt(rate));
            } catch (DaoException e) {
                String message = "Discount can't be updated by discount rate " + rate;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public long createDiscount(Map<String, String> discountParameters) throws ServiceException {
        long discountId = 0;
        FormValidator validator = FormValidatorImpl.getInstance();
        if (validator.isDiscountParametersValid(discountParameters)) {
            try {
                Discount.DiscountType discountType =
                        Discount.DiscountType.valueOf(discountParameters.get(DISCOUNT_ID).toUpperCase());
                int discountRate = Integer.parseInt(discountParameters.get(DISCOUNT_RATE));
                Optional<Discount> optionalDiscount = discountDao.findByTypeAndRate(discountType, discountRate);
                if (optionalDiscount.isPresent()) {
                    discountId = optionalDiscount.get().getId();
                } else {
                    Discount discount = new Discount(discountType, discountRate);
                    discountId = discountDao.create(discount);
                }
            } catch (DaoException e) {
                String message = "Discount can't be inserted in data base";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
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