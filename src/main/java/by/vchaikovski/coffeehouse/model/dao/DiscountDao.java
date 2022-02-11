package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.Discount;

import java.util.List;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Discount dao.
 */
public interface DiscountDao extends BaseDao<Discount> {
    /**
     * Find by type and rate optional.
     *
     * @param discountType the discount type
     * @param rate         the rate
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Discount> findByTypeAndRate(Discount.DiscountType discountType, int rate) throws DaoException;

    /**
     * Find by type list.
     *
     * @param discountType the discount type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Discount> findByType(Discount.DiscountType discountType) throws DaoException;

    /**
     * Find by rate list.
     *
     * @param rate the rate
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Discount> findByRate(int rate) throws DaoException;

    /**
     * Find by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<Discount> findByUserId(long userId) throws DaoException;

    /**
     * Update discount type boolean.
     *
     * @param id           the id
     * @param discountType the discount type
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateDiscountType(long id, Discount.DiscountType discountType) throws DaoException;

    /**
     * Update discount rate boolean.
     *
     * @param id   the id
     * @param rate the rate
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateDiscountRate(long id, int rate) throws DaoException;
}