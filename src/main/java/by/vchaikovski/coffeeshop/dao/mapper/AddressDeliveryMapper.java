package by.vchaikovski.coffeeshop.dao.mapper;

import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDeliveryMapper {
    private static final Logger logger = LogManager.getLogger();

    private AddressDeliveryMapper() {
    }

    public static AddressDelivery createAddressDelivery(ResultSet resultSet) throws DaoException {
        AddressDelivery addressDelivery;
        try {
            long id = resultSet.getLong(1);
            String streetName = resultSet.getString(2);
            String houseNumber = resultSet.getString(3);
            int buildingNumber = resultSet.getInt(4);
            int flatNumber = resultSet.getInt(5);
            AddressDelivery.AddressDeliveryBuilder builder = new AddressDelivery.AddressDeliveryBuilder();
            addressDelivery = builder.setId(id)
                    .setStreetName(streetName)
                    .setHouseNumber(houseNumber)
                    .setBuildingNumber(buildingNumber)
                    .setFlatNumber(flatNumber)
                    .build();
        } catch (SQLException e) {
            logger.error(() -> "AddressDelivery can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
            throw new DaoException("AddressDelivery can't be created. The resultSet " + resultSet + " doesn't contain required parameters.", e);
        }
        return addressDelivery;
    }
}
