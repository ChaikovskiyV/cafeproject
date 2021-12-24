package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;

public interface DiscountDao extends BaseDao<Discount> {
    boolean updateDiscountType(long id, Discount.DiscountType discountType) throws ConnectionPoolException, DaoException;

    boolean updateDiscountRate(long id, int rate) throws DaoException, ConnectionPoolException;
}