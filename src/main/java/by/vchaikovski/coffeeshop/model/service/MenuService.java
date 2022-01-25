package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> findByName(String name) throws ServiceException;

    List<Menu> findByFoodType(String foodType) throws ServiceException;

    List<Menu> findByPrice(String price) throws ServiceException;

    List<Menu> findByPriceRange(String minPrice, String maxPrice) throws ServiceException;

    List<Menu> findByQuantityInStock(String quantityInStock) throws ServiceException;

    List<Menu> findByQuantityInStockRange(String minQuantity, String maxQuantity) throws ServiceException;

    boolean updateMenuName(long id, String name) throws ServiceException;

    boolean updateMenuFoodType(long id, String foodType) throws ServiceException;

    boolean updateMenuPrice(long id, String price) throws ServiceException;

    boolean updateMenuQuantityInStock(long id, String quantityInStock) throws ServiceException;

    boolean updateMenuDescription(long id, String description) throws ServiceException;

    boolean updateMenuImage(long id, String imagePath) throws ServiceException;
}