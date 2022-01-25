package by.vchaikovski.coffeeshop.model.service.impl;

import by.vchaikovski.coffeeshop.exception.DaoException;
import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.dao.DaoProvider;
import by.vchaikovski.coffeeshop.model.dao.MenuDao;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.model.service.MenuService;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LogManager.getLogger();
    private static MenuService instance;
    private final MenuDao menuDao;

    private MenuServiceImpl() {
        menuDao = DaoProvider.getInstance().getMenuDao();
    }

    public static MenuService getInstance() {
        if (instance == null) {
            instance = new MenuServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Menu> findByName(String name) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<Menu> menuList = new ArrayList<>();
        if (validator.isNameValid(name)) {
            try {
                menuList = menuDao.findByName(name);
            } catch (DaoException e) {
                String message = "Menu can't be found by name=" + name;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return menuList;
    }

    @Override
    public List<Menu> findByFoodType(String foodType) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<Menu> menuList = new ArrayList<>();
        if (validator.isEnumContains(foodType, Menu.FoodType.class)) {
            try {
                menuList = menuDao.findByFoodType(Menu.FoodType.valueOf(foodType.toUpperCase()));
            } catch (DaoException e) {
                String message = "Menu can't be found by food type=" + foodType;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return menuList;
    }

    @Override
    public List<Menu> findByPrice(String price) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<Menu> menuList = new ArrayList<>();
        if (validator.isNumberValid(price)) {
            try {
                menuList = menuDao.findByPrice(new BigDecimal(price));
            } catch (DaoException e) {
                String message = "Menu can't be found by price=" + price;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return menuList;
    }

    @Override
    public List<Menu> findByPriceRange(String minPrice, String maxPrice) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<Menu> menuList = new ArrayList<>();
        if (validator.isNumberValid(minPrice) && validator.isNumberValid(maxPrice)) {
            try {
                menuList = menuDao.findByPrice(new BigDecimal(minPrice), new BigDecimal(maxPrice));
            } catch (DaoException e) {
                String message = "Menu can't be found by price range=" + minPrice + " - " + maxPrice;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return menuList;
    }

    @Override
    public List<Menu> findByQuantityInStock(String quantityInStock) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<Menu> menuList = new ArrayList<>();
        if (validator.isNumberValid(quantityInStock)) {
            try {
                menuList = menuDao.findByQuantityInStock(Integer.parseInt(quantityInStock));
            } catch (DaoException e) {
                String message = "Menu can't be found by quantity in stock=" + quantityInStock;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return menuList;
    }

    @Override
    public List<Menu> findByQuantityInStockRange(String minQuantity, String maxQuantity) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        List<Menu> menuList = new ArrayList<>();
        if (validator.isNumberValid(minQuantity) && validator.isNumberValid(maxQuantity)) {
            try {
                menuList = menuDao.findByQuantityInStock(Integer.parseInt(minQuantity), Integer.parseInt(maxQuantity));
            } catch (DaoException e) {
                String message = "Menu can't be found by quantity in stock range=" +
                        minQuantity + " - " + maxQuantity;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return menuList;
    }

    @Override
    public boolean updateMenuName(long id, String name) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNameValid(name)) {
            try {
                result = menuDao.updateMenuName(id, name);
            } catch (DaoException e) {
                String message = "Menu can't be updated by name=" + name;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateMenuFoodType(long id, String foodType) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(foodType, Menu.FoodType.class)) {
            try {
                result = menuDao.updateMenuFoodType(id, Menu.FoodType.valueOf(foodType));
            } catch (DaoException e) {
                String message = "Menu can't be updated by food type=" + foodType;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateMenuPrice(long id, String price) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(price)) {
            try {
                result = menuDao.updateMenuPrice(id, new BigDecimal(price));
            } catch (DaoException e) {
                String message = "Menu can't be updated by price=" + price;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateMenuQuantityInStock(long id, String quantityInStock) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(quantityInStock)) {
            try {
                result = menuDao.updateMenuQuantityInStock(id, Integer.parseInt(quantityInStock));
            } catch (DaoException e) {
                String message = "Menu can't be updated by quantity in stock=" + quantityInStock;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateMenuDescription(long id, String description) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isTextValid(description)) {
            try {
                result = menuDao.updateMenuDescription(id, description);
            } catch (DaoException e) {
                String message = "Menu can't be updated by description=" + description;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateMenuImage(long id, String imagePath) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        Path filePath = Path.of(imagePath);
        if (validator.isPicture(imagePath) && Files.exists(filePath)) {
            try {
                byte[] imageBytes = Files.readAllBytes(filePath);
                result = menuDao.updateMenuImage(id, imageBytes);
            } catch (DaoException | IOException e) {
                String message = "Menu can't be updated by image";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }
}