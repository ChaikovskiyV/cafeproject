package by.vchaikovski.coffeeshop.model.dao.mapper.impl;

import by.vchaikovski.coffeeshop.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.model.entity.Menu;
import by.vchaikovski.coffeeshop.exception.DaoException;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static by.vchaikovski.coffeeshop.model.dao.ColumnTable.*;

public class MenuMapperImpl implements BaseMapper<Menu> {
    private static final MenuMapperImpl instance = new MenuMapperImpl();

    private MenuMapperImpl() {
    }

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
            LocalDate productionDate = resultSet.getDate(MENU_PRODUCTION_DATE).toLocalDate();
            LocalDate expirationDate = resultSet.getDate(MENU_EXPIRATION_DATE).toLocalDate();
            Blob blob = resultSet.getBlob(MENU_IMAGE);
            Image image = getImage(blob);
            Menu.MenuBuilder builder = new Menu.MenuBuilder();
            menu = builder.setId(id)
                    .setName(name)
                    .setType(type)
                    .setDescription(description)
                    .setPrice(price)
                    .setQuantityInStock(quantityInStock)
                    .setProductionDate(productionDate)
                    .setExpirationDate(expirationDate)
                    .setFoodImage(image)
                    .build();
        } catch (SQLException e) {
            String message = "Menu can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return menu;
    }

    private Image getImage(Blob blob) throws SQLException {
        int length = (int) blob.length();
        byte[] byteImg = blob.getBytes(1, length);
        ImageIcon icon = new ImageIcon(byteImg);

        return icon.getImage();
    }
}