package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.Menu;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface MenuDao extends BaseDao<Menu> {
    List<Menu> findByName(String name);

    List<Menu> findByFoodType(Menu.FoodType foodType);

    List<Menu> findByPrice(BigDecimal price);

    List<Menu> findByPrice(BigDecimal minPrice, BigDecimal maxPrice);

    List<Menu> findByQuantityInStock(int quantityInStock);

    List<Menu> findByQuantityInStock(int minQuantity, int maxQuantity);

    List<Menu> findByProductionDate (LocalDateTime productionDate);

    List<Menu> findByProductionDate (LocalDateTime startPeriod, LocalDateTime endPeriod);

    List<Menu> findByExpirationDate(LocalDateTime expirationDate);

    List<Menu> findByExpirationDate(LocalDateTime startPeriod, LocalDateTime endPeriod);

    List<Menu> findWithOutDescription(); //TODO may be it can be deleted
}
