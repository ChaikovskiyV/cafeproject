package by.vchaikovski.coffeehouse.model.service.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.dao.DaoProvider;
import by.vchaikovski.coffeehouse.model.dao.MenuDao;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.service.MenuService;
import by.vchaikovski.coffeehouse.util.PictureLoader;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.FormValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import by.vchaikovski.coffeehouse.util.validator.impl.FormValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Menu service.
 */
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LogManager.getLogger();
    private static MenuService instance;
    private final MenuDao menuDao;

    private MenuServiceImpl() {
        menuDao = DaoProvider.getInstance().getMenuDao();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MenuService getInstance() {
        if (instance == null) {
            instance = new MenuServiceImpl();
        }
        return instance;
    }

    @Override
    public long create(Map<String, String> menuParameters) throws ServiceException {
        long menuId = 0;
        FormValidator formValidator = FormValidatorImpl.getInstance();
        if (formValidator.isMenuParametersValid(menuParameters)) {
            String name = menuParameters.get(MENU_NAME);
            String type = menuParameters.get(MENU_TYPE);
            String price = menuParameters.get(MENU_PRICE);
            String quantityInStock = menuParameters.get(MENU_QUANTITY_IN_STOCK);
            String description = menuParameters.get(MENU_DESCRIPTION);
            String imagePath = menuParameters.get(MENU_IMAGE);
            Menu.FoodType foodType = Menu.FoodType.valueOf(type.toUpperCase());
            PictureLoader pictureLoader = PictureLoader.getInstance();
            byte[] image;
            if (imagePath != null) {
                image = pictureLoader.loadPicture(imagePath);
            } else {
                image = pictureLoader.loadDefaultPicture(foodType);
            }
            Menu menu = new Menu.MenuBuilder()
                    .setName(name)
                    .setType(foodType)
                    .setPrice(new BigDecimal(price))
                    .setQuantityInStock(Integer.parseInt(quantityInStock))
                    .setDescription(description)
                    .setFoodImage(image)
                    .build();
            try {
                menuId = menuDao.create(menu);
            } catch (DaoException e) {
                String message = "Menu can't be inserted in data base";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return menuId;
    }

    @Override
    public List<Menu> findAll() throws ServiceException {
        List<Menu> menuList;
        try {
            menuList = menuDao.findAll();
        } catch (DaoException e) {
            String message = "Any menu can't be found";
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return menuList;
    }

    @Override
    public Optional<Menu> findById(long id) throws ServiceException {
        Optional<Menu> optionalMenu;
        try {
            optionalMenu = menuDao.findById(id);
        } catch (DaoException e) {
            String message = "Menu can't be found by id=" + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return optionalMenu;
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
    public List<Menu> findMenuBySeveralParameter(Map<String, String> menuParameters) throws ServiceException {
        List<Menu> menuList = findAll();
        if (menuParameters != null && !menuParameters.isEmpty()) {
            DataValidator validator = DataValidatorImpl.getInstance();
            String menuName = menuParameters.get(MENU_NAME);
            String menuPrice = menuParameters.get(MENU_PRICE);
            String menuType = menuParameters.get(MENU_TYPE);
            String quantityInStock = menuParameters.get(MENU_QUANTITY_IN_STOCK);
            if (validator.isNameValid(menuName)) {
                menuList = menuList.stream()
                        .filter(menu -> menu.getName().equals(menuName))
                        .toList();
            }
            if (validator.isNumberValid(menuPrice)) {
                BigDecimal price = new BigDecimal(menuPrice);
                menuList = menuList.stream()
                        .filter(menu -> menu.getPrice().compareTo(price) == 0)
                        .toList();
            }
            if (validator.isEnumContains(menuType, Menu.FoodType.class)) {
                Menu.FoodType type = Menu.FoodType.valueOf(menuType.toUpperCase());
                menuList = menuList.stream()
                        .filter(menu -> menu.getType() == type)
                        .toList();
            }
            if (validator.isNumberValid(quantityInStock)) {
                int quantity = Integer.parseInt(quantityInStock);
                menuList = menuList.stream()
                        .filter(menu -> menu.getQuantityInStock() == quantity)
                        .toList();
            }
        }
        return menuList;
    }

    @Override
    public boolean updateMenu(long id, Map<String, String> menuParameters) throws ServiceException {
        boolean result = false;
        FormValidator formValidator = FormValidatorImpl.getInstance();
        if (formValidator.isMenuParametersValid(menuParameters)) {
            PictureLoader pictureLoader = PictureLoader.getInstance();
            String menuName = menuParameters.get(MENU_NAME);
            Menu.FoodType menuType = Menu.FoodType.valueOf(menuParameters.get(MENU_TYPE).toUpperCase());
            String description = menuParameters.get(MENU_DESCRIPTION);
            BigDecimal price = new BigDecimal(menuParameters.get(MENU_PRICE));
            int quantityInStock = Integer.parseInt(menuParameters.get(MENU_QUANTITY_IN_STOCK));
            byte[] image = pictureLoader.loadPicture(menuParameters.get(MENU_IMAGE));
            Menu menu = new Menu.MenuBuilder()
                    .setName(menuName)
                    .setType(menuType)
                    .setDescription(description)
                    .setPrice(price)
                    .setQuantityInStock(quantityInStock)
                    .setFoodImage(image)
                    .build();
            try {
                result = menuDao.update(id, menu);
            } catch (DaoException e) {
                String message = "Menu can't be updated by several parameters";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
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
                result = menuDao.updateMenuFoodType(id, Menu.FoodType.valueOf(foodType.toUpperCase()));
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
    public boolean increaseQuantityInStock(long id, String quantity) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(quantity)) {
            try {
                result = menuDao.increaseQuantityInStock(id, Integer.parseInt(quantity));
            } catch (DaoException e) {
                String message = "Quantity in stock can't be increased on number=" + quantity;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean reduceQuantityInStock(long id, String quantity) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(quantity)) {
            try {
                result = menuDao.reduceQuantityInStock(id, Integer.parseInt(quantity));
            } catch (DaoException e) {
                String message = "Quantity in stock can't be reduced on number=" + quantity;
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

    @Override
    public boolean deleteById(long id) throws ServiceException {
        boolean result;
        try {
            result = menuDao.deleteById(id);
        } catch (DaoException e) {
            String message = "Menu can't be deleted by id=" + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }
}