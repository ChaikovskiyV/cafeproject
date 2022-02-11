package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Discount;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Discount service.
 */
public interface DiscountService {
    /**
     * Find all discounts list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Discount> findAllDiscounts() throws ServiceException;

    /**
     * Find discounts by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Discount> findDiscountsById(long id) throws ServiceException;

    /**
     * Find discount by user id optional.
     *
     * @param userId the user id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Discount> findDiscountByUserId(long userId) throws ServiceException;

    /**
     * Find discounts by rate list.
     *
     * @param rate the rate
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Discount> findDiscountsByRate(String rate) throws ServiceException;

    /**
     * Find discounts by type list.
     *
     * @param discountType the discount type
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Discount> findDiscountsByType(String discountType) throws ServiceException;

    /**
     * Update discount type boolean.
     *
     * @param id           the id
     * @param discountType the discount type
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateDiscountType(long id, String discountType) throws ServiceException;

    /**
     * Update discount rate boolean.
     *
     * @param id   the id
     * @param rate the rate
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateDiscountRate(long id, String rate) throws ServiceException;

    /**
     * Create discount long.
     *
     * @param discountParameters the discount parameters
     * @return the long
     * @throws ServiceException the service exception
     */
    long createDiscount(Map<String, String> discountParameters) throws ServiceException;

    /**
     * Delete discount by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteDiscountById(long id) throws ServiceException;
}