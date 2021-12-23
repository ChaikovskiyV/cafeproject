package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.Discount;

public interface DiscountDao extends BaseDao<Discount> {
    boolean updateDiscountType(long id, Discount.DiscountType discountType);

    boolean updateDiscountRate(long id, int rate);
}