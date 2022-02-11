package by.vchaikovski.coffeehouse.model.dao.mapper.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeehouse.model.entity.Menu;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeehouse.model.dao.ColumnTable.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Menu mapper.
 */

public class MenuMapperImpl implements BaseMapper<Menu> {
    private static final MenuMapperImpl instance = new MenuMapperImpl();

    private MenuMapperImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MenuMapperImpl getInstance() {
        return instance;
    }

    @Override
    public Menu createEntity(ResultSet resultSet) throws DaoException {
        Menu menu;
        try {
            long id = resultSet.getLong(MENU_ID);
            String name = resultSet.getString(MENU_NAME);
            Menu.FoodType type = Menu.FoodType.valueOf(resultSet.getString(MENU_TYPE).toUpperCase());
            String description = resultSet.getString(MENU_DESCRIPTION);
            BigDecimal price = resultSet.getBigDecimal(MENU_PRICE);
            int quantityInStock = resultSet.getInt(MENU_QUANTITY_IN_STOCK);
            Blob blob = resultSet.getBlob(MENU_IMAGE);
            byte[] imageBytes = blob.getBytes(1, (int) blob.length());
            Menu.MenuBuilder builder = new Menu.MenuBuilder();
            menu = builder.setId(id)
                    .setName(name)
                    .setType(type)
                    .setDescription(description)
                    .setPrice(price)
                    .setQuantityInStock(quantityInStock)
                    .setFoodImage(imageBytes)
                    .build();
        } catch (SQLException e) {
            String message = "Menu can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menu;
    }
}