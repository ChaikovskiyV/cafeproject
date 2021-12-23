package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.util.List;

public interface AddressDeliverDao extends BaseDao<AddressDelivery> {
    List<AddressDelivery> findByStreetName(String streetName) throws ConnectionPoolException, DaoException;

    boolean updateAddressDeliveryStreetName(long id, String streetName) throws DaoException, ConnectionPoolException;

    boolean updateAddressDeliveryHouseNumber(long id, String houseNumber) throws ConnectionPoolException, DaoException;

    boolean updateAddressDeliveryBuildingNumber(long id, int buildingNumber) throws ConnectionPoolException, DaoException;

    boolean updateAddressDeliveryFlatNumber(long id, int flatNumber) throws ConnectionPoolException, DaoException;
}
