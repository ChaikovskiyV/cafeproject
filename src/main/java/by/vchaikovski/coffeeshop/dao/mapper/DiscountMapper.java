package by.vchaikovski.coffeeshop.dao.mapper;

import by.vchaikovski.coffeeshop.dao.BaseDao;
import by.vchaikovski.coffeeshop.entity.Discount;
import by.vchaikovski.coffeeshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiscountMapper {
    private static final Logger logger = LogManager.getLogger();

    private DiscountMapper() {
    }

    public static Discount createDiscount(ResultSet resultSet) throws DaoException {
        Discount discount;
        try {
            long id = resultSet.getLong(BaseDao.FIRST_PARAMETER_INDEX);
            Discount.DiscountType type = Discount.DiscountType.valueOf(resultSet.getString(BaseDao.SECOND_PARAMETER_INDEX).toUpperCase());
            int rate = resultSet.getInt(BaseDao.THIRD_PARAMETER_INDEX);
            Discount.DiscountBuilder builder = new Discount.DiscountBuilder();
            discount = builder.setId(id)
                    .setType(type)
                    .setRate(rate)
                    .build();
        } catch (SQLException e) {
            logger.error(() -> "Discount can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
            throw new DaoException("Discount can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
        }
        return discount;
    }
}