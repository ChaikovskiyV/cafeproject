package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.entity.Discount;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.util.Optional;

public interface DiscountDao extends BaseDao<Discount> {
    Optional<Discount> findByTypeAndRate(Discount.DiscountType discountType, int rate) throws DaoException;

    Optional<Discount> findByUserId(long userId) throws DaoException;

    boolean updateDiscountType(long id, Discount.DiscountType discountType) throws DaoException;

    boolean updateDiscountRate(long id, int rate) throws DaoException;
}