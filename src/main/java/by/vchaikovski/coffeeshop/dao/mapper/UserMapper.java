package by.vchaikovski.coffeeshop.dao.mapper;

import by.vchaikovski.coffeeshop.dao.BaseDao;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.entity.User;
import by.vchaikovski.coffeeshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    private static final Logger logger = LogManager.getLogger();

    private UserMapper() {
    }

    public static User createUser(ResultSet resultSet) throws DaoException {
        User user;
        try {
            long id = resultSet.getLong(BaseDao.FIRST_PARAMETER_INDEX);
            String login = resultSet.getString(BaseDao.SECOND_PARAMETER_INDEX);
            String password = resultSet.getString(BaseDao.THIRD_PARAMETER_INDEX);
            String firstName = resultSet.getString(BaseDao.FOURTH_PARAMETER_INDEX);
            String lastName = resultSet.getString(BaseDao.FIFTH_PARAMETER_INDEX);
            String email = resultSet.getString(BaseDao.SIXTH_PARAMETER_INDEX);
            String phoneNumber = resultSet.getString(BaseDao.SEVENTH_PARAMETER_INDEX);
            User.Role role = User.Role.valueOf(resultSet.getString(BaseDao.EIGHTH_PARAMETER_INDEX).toUpperCase());
            User.Status status = User.Status.valueOf(resultSet.getString(BaseDao.NINTH_PARAMETER_INDEX).toUpperCase());
            //Discount discount = DiscountMapper.createDiscount(resultSet);//TODO check if possible use DiscountMapper
            long discountId = resultSet.getLong(BaseDao.TENTH_PARAMETER_INDEX);
            Discount.DiscountType discountType = Discount.DiscountType.valueOf(resultSet.getString(BaseDao.ELEVENTH_PARAMETER_INDEX).toUpperCase());
            int rate = resultSet.getInt(BaseDao.TWELFTH_PARAMETER_INDEX);
            Discount.DiscountBuilder discountBuilder = new Discount.DiscountBuilder();
            Discount discount = discountBuilder.setId(discountId)
                    .setType(discountType)
                    .setRate(rate)
                    .build();
            User.UserBuilder userBuilder = new User.UserBuilder();
            user = userBuilder.setId(id)
                    .setLogin(login)
                    .setPassword(password)
                    .setFirstName(firstName)
                    .setLastName(lastName)
                    .setEmail(email)
                    .setPhoneNumber(phoneNumber)
                    .setRole(role)
                    .setStatus(status)
                    .setDiscount(discount)
                    .build();
        } catch (SQLException e) {
            logger.error(() -> "User can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
            throw new DaoException("User can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
        }
        return user;
    }
}