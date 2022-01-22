package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.model.entity.Menu;

import java.math.BigDecimal;
import java.util.List;

public interface MenuDao extends BaseDao<Menu> {
    List<Menu> findByName(String name) throws DaoException;

    List<Menu> findByFoodType(Menu.FoodType foodType) throws DaoException;

    List<Menu> findByPrice(BigDecimal price) throws DaoException;

    List<Menu> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws DaoException;

    List<Menu> findByQuantityInStock(int quantityInStock) throws DaoException;

    List<Menu> findByQuantityInStock(int minQuantity, int maxQuantity) throws DaoException;

    boolean updateMenuName(long id, String name) throws DaoException;

    boolean updateMenuFoodType(long id, Menu.FoodType foodType) throws DaoException;

    boolean updateMenuPrice(long id, BigDecimal price) throws DaoException;

    boolean updateMenuQuantityInStock(long id, int quantityInStock) throws DaoException;

    boolean updateMenuDescription(long id, String description) throws DaoException;

    boolean updateMenuImage(long id, byte[] image) throws  DaoException;
}