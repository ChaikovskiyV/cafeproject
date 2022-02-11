package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.Menu;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Menu dao.
 */
public interface MenuDao extends BaseDao<Menu> {
    /**
     * Find by name list.
     *
     * @param name the name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Menu> findByName(String name) throws DaoException;

    /**
     * Find by food type list.
     *
     * @param foodType the food type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Menu> findByFoodType(Menu.FoodType foodType) throws DaoException;

    /**
     * Find by price list.
     *
     * @param price the price
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Menu> findByPrice(BigDecimal price) throws DaoException;

    /**
     * Find by price list.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Menu> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws DaoException;

    /**
     * Find by quantity in stock list.
     *
     * @param quantityInStock the quantity in stock
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Menu> findByQuantityInStock(int quantityInStock) throws DaoException;

    /**
     * Find by quantity in stock list.
     *
     * @param minQuantity the min quantity
     * @param maxQuantity the max quantity
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Menu> findByQuantityInStock(int minQuantity, int maxQuantity) throws DaoException;

    /**
     * Update menu name boolean.
     *
     * @param id   the id
     * @param name the name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateMenuName(long id, String name) throws DaoException;

    /**
     * Update menu food type boolean.
     *
     * @param id       the id
     * @param foodType the food type
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateMenuFoodType(long id, Menu.FoodType foodType) throws DaoException;

    /**
     * Update menu price boolean.
     *
     * @param id    the id
     * @param price the price
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateMenuPrice(long id, BigDecimal price) throws DaoException;

    /**
     * Update menu quantity in stock boolean.
     *
     * @param id              the id
     * @param quantityInStock the quantity in stock
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateMenuQuantityInStock(long id, int quantityInStock) throws DaoException;

    /**
     * Increase quantity in stock boolean.
     *
     * @param id       the id
     * @param quantity the quantity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean increaseQuantityInStock(long id, int quantity) throws DaoException;

    /**
     * Reduce quantity in stock boolean.
     *
     * @param id       the id
     * @param quantity the quantity
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean reduceQuantityInStock(long id, int quantity) throws DaoException;

    /**
     * Update menu description boolean.
     *
     * @param id          the id
     * @param description the description
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateMenuDescription(long id, String description) throws DaoException;

    /**
     * Update menu image boolean.
     *
     * @param id    the id
     * @param image the image
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateMenuImage(long id, byte[] image) throws DaoException;
}