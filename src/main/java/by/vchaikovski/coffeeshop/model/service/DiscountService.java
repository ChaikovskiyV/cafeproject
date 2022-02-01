package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.Discount;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DiscountService {
    List<Discount> findAllDiscounts() throws ServiceException;

    Optional<Discount> findDiscountsById(long id) throws ServiceException;

    Optional<Discount> findDiscountByUserId(long userId) throws ServiceException;

    boolean updateDiscountType(long id, Discount.DiscountType discountType) throws ServiceException;

    boolean updateDiscountRate(long id, int rate) throws ServiceException;

    long createDiscount(Map<String, String> discountParameters) throws ServiceException;

    boolean deleteDiscountById(long id) throws ServiceException;
}