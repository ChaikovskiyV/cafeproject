package by.vchaikovski.coffeehouse.model.dao.mapper.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.dao.mapper.BaseMapper;
import by.vchaikovski.coffeehouse.model.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeehouse.model.dao.ColumnTable.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type User mapper.
 */

public class UserMapperImpl implements BaseMapper<User> {
    private static final UserMapperImpl instance = new UserMapperImpl();

    private UserMapperImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UserMapperImpl getInstance() {
        return instance;
    }

    @Override
    public User createEntity(ResultSet resultSet) throws DaoException {
        User user;
        try {
            long id = resultSet.getLong(USER_ID);
            String login = resultSet.getString(USER_LOGIN);
            String firstName = resultSet.getString(USER_FIRST_NAME);
            String lastName = resultSet.getString(USER_LAST_NAME);
            String email = resultSet.getString(USER_EMAIL);
            String phoneNumber = resultSet.getString(USER_PHONE);
            User.Role role = User.Role.valueOf(resultSet.getString(USER_ROLE).toUpperCase());
            User.Status status = User.Status.valueOf(resultSet.getString(USER_STATUS).toUpperCase());
            long discountId = resultSet.getLong(USER_DISCOUNT_ID);
            User.UserBuilder userBuilder = new User.UserBuilder();
            user = userBuilder.setId(id)
                    .setLogin(login)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPhoneNumber(phoneNumber)
                    .setRole(role)
                    .setStatus(status)
                    .setDiscountId(discountId)
                    .build();
        } catch (SQLException e) {
            String message = "User can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return user;
    }
}