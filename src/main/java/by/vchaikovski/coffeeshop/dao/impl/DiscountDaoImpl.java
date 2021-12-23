package by.vchaikovski.coffeeshop.dao.impl;

import by.vchaikovski.coffeeshop.dao.DiscountDao;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.util.List;
import java.util.Optional;

public class DiscountDaoImpl implements DiscountDao {
    @Override
    public List<Discount> findAll() throws DaoException, ConnectionPoolException {
        return null;
    }

    @Override
    public Optional<Discount> findById(long id) throws ConnectionPoolException, DaoException {
        return Optional.empty();
    }

    @Override
    public long create(Discount entity) throws ConnectionPoolException, DaoException {
        return 0;
    }

    @Override
    public boolean update(long id, Discount entity) throws ConnectionPoolException, DaoException {
        return false;
    }

    @Override
    public boolean deleteById(long id) throws ConnectionPoolException, DaoException {
        return false;
    }

    @Override
    public boolean updateDiscountType(long id, Discount.DiscountType discountType) {
        return false;
    }

    @Override
    public boolean updateDiscountRate(long id, int rate) {
        return false;
    }
}
