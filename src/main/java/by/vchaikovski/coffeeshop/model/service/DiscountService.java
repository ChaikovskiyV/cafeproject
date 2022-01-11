package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.model.entity.Discount;

import java.util.List;
import java.util.Optional;

public interface DiscountService {
    List<Discount> findAllDiscounts();

    Optional<Discount> findDiscountsById(long id);

    Optional<Discount> findDiscountByUserId(long userId);

    boolean updateDiscountType(long id, Discount.DiscountType discountType);

    boolean updateDiscountRate(long id, int rate);

    long createDiscount(Discount discount);

    boolean deleteDiscountById(long id);
}