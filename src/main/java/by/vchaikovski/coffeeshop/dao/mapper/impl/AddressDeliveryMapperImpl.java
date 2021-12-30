package by.vchaikovski.coffeeshop.dao.mapper.impl;

import by.vchaikovski.coffeeshop.dao.mapper.BaseMapper;
import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.vchaikovski.coffeeshop.dao.ColumnTable.*;

public class AddressDeliveryMapperImpl implements BaseMapper<AddressDelivery> {
    @Override
    public AddressDelivery createEntity(ResultSet resultSet) throws DaoException {
        AddressDelivery addressDelivery;
        try {
            long id = resultSet.getLong(ADDRESS_ID);
            String streetName = resultSet.getString(ADDRESS_STREET);
            String houseNumber = resultSet.getString(ADDRESS_HOUSE_NUMBER);
            int buildingNumber = resultSet.getInt(ADDRESS_BUILDING_NUMBER);
            int flatNumber = resultSet.getInt(ADDRESS_FLAT_NUMBER);
            AddressDelivery.AddressDeliveryBuilder builder = new AddressDelivery.AddressDeliveryBuilder();
            addressDelivery = builder.setId(id)
                    .setStreetName(streetName)
                    .setHouseNumber(houseNumber)
                    .setBuildingNumber(buildingNumber)
                    .setFlatNumber(flatNumber)
                    .build();
        } catch (SQLException e) {
            String message = "AddressDelivery can't be created. The resultSet " + resultSet + " doesn't contain required parameters.";
            logger.error(message, e);
            throw new DaoException(message, e);
        }
        return addressDelivery;
    }
}