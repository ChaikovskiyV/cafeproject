package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.Menu;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Menu service.
 */
public interface MenuService {
    /**
     * Create long.
     *
     * @param menuParameters the menu parameters
     * @return the long
     * @throws ServiceException the service exception
     */
    long create(Map<String, String> menuParameters) throws ServiceException;

    /**
     * Find all list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findAll() throws ServiceException;

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Menu> findById(long id) throws ServiceException;

    /**
     * Find by name list.
     *
     * @param name the name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findByName(String name) throws ServiceException;

    /**
     * Find by food type list.
     *
     * @param foodType the food type
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findByFoodType(String foodType) throws ServiceException;

    /**
     * Find by price list.
     *
     * @param price the price
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findByPrice(String price) throws ServiceException;

    /**
     * Find by price range list.
     *
     * @param minPrice the min price
     * @param maxPrice the max price
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findByPriceRange(String minPrice, String maxPrice) throws ServiceException;

    /**
     * Find by quantity in stock list.
     *
     * @param quantityInStock the quantity in stock
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findByQuantityInStock(String quantityInStock) throws ServiceException;

    /**
     * Find by quantity in stock range list.
     *
     * @param minQuantity the min quantity
     * @param maxQuantity the max quantity
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findByQuantityInStockRange(String minQuantity, String maxQuantity) throws ServiceException;

    /**
     * Find menu by several parameter list.
     *
     * @param menuParameters the menu parameters
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Menu> findMenuBySeveralParameter(Map<String, String> menuParameters) throws ServiceException;

    /**
     * Update menu boolean.
     *
     * @param id             the id
     * @param menuParameters the menu parameters
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateMenu(long id, Map<String, String> menuParameters) throws ServiceException;

    /**
     * Update menu name boolean.
     *
     * @param id   the id
     * @param name the name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateMenuName(long id, String name) throws ServiceException;

    /**
     * Update menu food type boolean.
     *
     * @param id       the id
     * @param foodType the food type
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateMenuFoodType(long id, String foodType) throws ServiceException;

    /**
     * Update menu price boolean.
     *
     * @param id    the id
     * @param price the price
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateMenuPrice(long id, String price) throws ServiceException;

    /**
     * Update menu quantity in stock boolean.
     *
     * @param id              the id
     * @param quantityInStock the quantity in stock
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateMenuQuantityInStock(long id, String quantityInStock) throws ServiceException;

    /**
     * Increase quantity in stock boolean.
     *
     * @param id       the id
     * @param quantity the quantity
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean increaseQuantityInStock(long id, String quantity) throws ServiceException;

    /**
     * Reduce quantity in stock boolean.
     *
     * @param id       the id
     * @param quantity the quantity
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean reduceQuantityInStock(long id, String quantity) throws ServiceException;

    /**
     * Update menu description boolean.
     *
     * @param id          the id
     * @param description the description
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateMenuDescription(long id, String description) throws ServiceException;

    /**
     * Update menu image boolean.
     *
     * @param id        the id
     * @param imagePath the image path
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateMenuImage(long id, String imagePath) throws ServiceException;

    /**
     * Delete by id boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteById(long id) throws ServiceException;
}