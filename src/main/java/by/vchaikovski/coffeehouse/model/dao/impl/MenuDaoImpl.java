package by.vchaikovski.coffeehouse.model.dao.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.MenuDao;
import by.vchaikovski.coffeehouse.model.dao.mapper.MapperProvider;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.model.pool.ConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.rowset.serial.SerialBlob;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Menu dao.
 */

public class MenuDaoImpl implements MenuDao {
    private static final Logger logger = LogManager.getLogger();
    private static final MapperProvider mapperProvider = MapperProvider.getInstance();
    private static final String FAILED_MESSAGE = "\" is failed. DataBase connection error.";
    private static final String FIND_ALL_MENU =
            "SELECT menu_id, name, product_type, description, price, quantity_in_stock, image FROM menu";
    private static final String BY_ID = " WHERE menu_id=";
    private static final String BY_NAME = " WHERE name=?";
    private static final String BY_PRODUCT_TYPE = " WHERE product_type=";
    private static final String BY_PRICE = " WHERE price=";
    private static final String BY_PRICE_RANGE = " WHERE price>=? AND price<=?";
    private static final String BY_QUANTITY_IN_STOCK = " WHERE quantity_in_stock=";
    private static final String BY_QUANTITY_IN_STOCK_RANGE = " WHERE quantity_in_stock>=? AND quantity_in_stock<=?";
    private static final String CREATE_MENU = "INSERT INTO menu" +
            "(name, product_type, description, price, quantity_in_stock, image) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_MENU_ALL_PARAM = "UPDATE menu SET " +
            "name=?, product_type=?, description=?, price=?, quantity_in_stock=?, image=? WHERE menu_id=?";
    private static final String UPDATE_MENU_NAME = "UPDATE menu SET name=? WHERE menu_id=?";
    private static final String UPDATE_MENU_PRODUCT_TYPE = "UPDATE menu SET product_type=? WHERE menu_id=?";
    private static final String UPDATE_MENU_PRICE = "UPDATE menu SET price=? WHERE menu_id=?";
    private static final String UPDATE_MENU_DESCRIPTION = "UPDATE menu SET description=? WHERE menu_id=?";
    private static final String UPDATE_MENU_QUANTITY_IN_STOCK = "UPDATE menu SET quantity_in_stock=? WHERE menu_id=?";
    private static final String INCREASE_MENU_QUANTITY_IN_STOCK = "UPDATE menu SET quantity_in_stock=quantity_in_stock+? WHERE menu_id=?";
    private static final String REDUCE_MENU_QUANTITY_IN_STOCK = "UPDATE menu SET quantity_in_stock=quantity_in_stock-? WHERE menu_id=? AND quantity_in_stock>=?";
    private static final String UPDATE_MENU_IMAGE = "UPDATE menu SET image=? WHERE menu_id=?";
    private static final String DELETE_MENU_BY_ID = "DELETE FROM menu WHERE menu_id=";
    private static MenuDao instance;

    private MenuDaoImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MenuDao getInstance() {
        if (instance == null) {
            instance = new MenuDaoImpl();
        }
        return instance;
    }

    @Override
    public List<Menu> findAll() throws DaoException {
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_MENU)) {
            while (resultSet.next()) {
                Menu menu = mapperProvider.getMenuMapper().createEntity(resultSet);
                menuList.add(menu);
            }
        } catch (SQLException e) {
            String message = "The query \"find all menu\" is failed. DataBase connection error.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menuList;
    }

    @Override
    public Optional<Menu> findById(long id) throws DaoException {
        Menu menu = null;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_MENU + BY_ID + id)) {
            if (resultSet.next()) {
                menu = mapperProvider.getMenuMapper().createEntity(resultSet);
            }
        } catch (SQLException e) {
            String message = "The query \"find menu by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return Optional.ofNullable(menu);
    }

    @Override
    public List<Menu> findByName(String name) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_MENU + BY_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Menu menu = mapperProvider.getMenuMapper().createEntity(resultSet);
                    menuList.add(menu);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find menu by name=" + name + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menuList;
    }

    @Override
    public List<Menu> findByFoodType(Menu.FoodType productType) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_MENU + BY_PRODUCT_TYPE + productType)) {
            while (resultSet.next()) {
                Menu menu = mapperProvider.getMenuMapper().createEntity(resultSet);
                menuList.add(menu);
            }
        } catch (SQLException e) {
            String message = "The query \"find menu by product type=" + productType + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menuList;
    }

    @Override
    public List<Menu> findByPrice(BigDecimal price) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_MENU + BY_PRICE + price)) {
            while (resultSet.next()) {
                Menu menu = mapperProvider.getMenuMapper().createEntity(resultSet);
                menuList.add(menu);
            }
        } catch (SQLException e) {
            String message = "The query \"find menu by price=" + price + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menuList;
    }

    @Override
    public List<Menu> findByPrice(BigDecimal minPrice, BigDecimal maxPrice) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_MENU + BY_PRICE_RANGE)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, minPrice);
            statement.setBigDecimal(SECOND_PARAMETER_INDEX, maxPrice);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Menu menu = mapperProvider.getMenuMapper().createEntity(resultSet);
                    menuList.add(menu);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find menu by price range=" + minPrice + " - " + maxPrice + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menuList;
    }

    @Override
    public List<Menu> findByQuantityInStock(int quantityInStock) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(FIND_ALL_MENU + BY_QUANTITY_IN_STOCK + quantityInStock)) {
            while (resultSet.next()) {
                Menu menu = mapperProvider.getMenuMapper().createEntity(resultSet);
                menuList.add(menu);
            }
        } catch (SQLException e) {
            String message = "The query \"find menu by quantity in stock=" + quantityInStock + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menuList;
    }

    @Override
    public List<Menu> findByQuantityInStock(int minQuantity, int maxQuantity) throws DaoException {
        List<Menu> menuList = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_MENU + BY_QUANTITY_IN_STOCK_RANGE)) {
            statement.setInt(FIRST_PARAMETER_INDEX, minQuantity);
            statement.setInt(SECOND_PARAMETER_INDEX, maxQuantity);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Menu menu = mapperProvider.getMenuMapper().createEntity(resultSet);
                    menuList.add(menu);
                }
            }
        } catch (SQLException e) {
            String message = "The query \"find menu by quantity in stock range=" + minQuantity + " - " + maxQuantity + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menuList;
    }

    @Override
    public boolean update(long id, Menu menu) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MENU_ALL_PARAM)) {
            statement.setString(FIRST_PARAMETER_INDEX, menu.getName());
            statement.setString(SECOND_PARAMETER_INDEX, menu.getType().name());
            statement.setString(THIRD_PARAMETER_INDEX, menu.getDescription());
            statement.setBigDecimal(FOURTH_PARAMETER_INDEX, menu.getPrice());
            statement.setInt(FIFTH_PARAMETER_INDEX, menu.getQuantityInStock());
            byte[] imageBytes = menu.getImage();
            Blob blob = new SerialBlob(imageBytes);
            statement.setBlob(SIXTH_PARAMETER_INDEX, blob);
            statement.setLong(SEVENTH_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"update menu by all parameters: \n" + menu + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean updateMenuName(long id, String name) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MENU_NAME)) {
            statement.setString(FIRST_PARAMETER_INDEX, name);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"update menu by name=" + name + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean updateMenuFoodType(long id, Menu.FoodType productType) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MENU_PRODUCT_TYPE)) {
            statement.setString(FIRST_PARAMETER_INDEX, productType.name());
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"update menu by product type=" + productType + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean updateMenuPrice(long id, BigDecimal price) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MENU_PRICE)) {
            statement.setBigDecimal(FIRST_PARAMETER_INDEX, price);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"update menu by price=" + price + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean updateMenuQuantityInStock(long id, int quantityInStock) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MENU_QUANTITY_IN_STOCK)) {
            statement.setInt(FIRST_PARAMETER_INDEX, quantityInStock);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"update menu by quantity in stock=" + quantityInStock + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean increaseQuantityInStock(long id, int quantity) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(INCREASE_MENU_QUANTITY_IN_STOCK)) {
            statement.setInt(FIRST_PARAMETER_INDEX, quantity);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"increase quantity in stock on number=" + quantity + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean reduceQuantityInStock(long id, int quantity) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(REDUCE_MENU_QUANTITY_IN_STOCK)) {
            statement.setInt(FIRST_PARAMETER_INDEX, quantity);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            statement.setInt(THIRD_PARAMETER_INDEX, quantity);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"reduce quantity in stock on number=" + quantity + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean updateMenuDescription(long id, String description) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MENU_DESCRIPTION)) {
            statement.setString(FIRST_PARAMETER_INDEX, description);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"update menu by description=" + description + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public boolean updateMenuImage(long id, byte[] image) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_MENU_IMAGE)) {
            Blob imageBlob = new SerialBlob(image);
            statement.setBlob(FIRST_PARAMETER_INDEX, imageBlob);
            statement.setLong(SECOND_PARAMETER_INDEX, id);
            result = statement.execute();
        } catch (SQLException e) {
            String message = "The query \"update menu by image\"" + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }

    @Override
    public long create(Menu menu) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(CREATE_MENU, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(FIRST_PARAMETER_INDEX, menu.getName());
            statement.setString(SECOND_PARAMETER_INDEX, menu.getType().name());
            statement.setString(THIRD_PARAMETER_INDEX, menu.getDescription());
            statement.setBigDecimal(FOURTH_PARAMETER_INDEX, menu.getPrice());
            statement.setInt(FIFTH_PARAMETER_INDEX, menu.getQuantityInStock());
            byte[] imageBytes = menu.getImage();
            Blob blob = new SerialBlob(imageBytes);
            statement.setBlob(SIXTH_PARAMETER_INDEX, blob);
            statement.executeUpdate();
            try (ResultSet resultSet = statement.getGeneratedKeys()) {
                long menuId = 0;
                if (resultSet.next()) {
                    menuId = resultSet.getLong(FIRST_PARAMETER_INDEX);
                }
                return menuId;
            }
        } catch (SQLException e) {
            String message = "The query \"insert menu in database: \n" + menu + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
    }

    @Override
    public boolean deleteById(long id) throws DaoException {
        boolean result;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             Statement statement = connection.createStatement()) {
            result = statement.execute(DELETE_MENU_BY_ID + id);
        } catch (SQLException e) {
            String message = "The query \"delete menu by id=" + id + FAILED_MESSAGE;
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return result;
    }
}