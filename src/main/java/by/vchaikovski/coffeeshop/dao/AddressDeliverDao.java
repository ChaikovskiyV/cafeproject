package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.util.List;

public interface AddressDeliverDao extends BaseDao<AddressDelivery> {
    List<AddressDelivery> findByStreetName(String streetName) throws DaoException;

    boolean updateAddressDeliveryStreetName(long id, String streetName) throws DaoException;

    boolean updateAddressDeliveryHouseNumber(long id, String houseNumber) throws DaoException;

    boolean updateAddressDeliveryBuildingNumber(long id, int buildingNumber) throws DaoException;

    boolean updateAddressDeliveryFlatNumber(long id, int flatNumber) throws DaoException;
}