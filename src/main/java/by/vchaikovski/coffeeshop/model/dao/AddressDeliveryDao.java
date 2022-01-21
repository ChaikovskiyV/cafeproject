package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.util.List;

public interface AddressDeliveryDao extends BaseDao<AddressDelivery> {
    List<AddressDelivery> findByStreetName(String streetName) throws DaoException;

    boolean updateAddressDeliveryStreetName(long id, String streetName) throws DaoException;

    boolean updateAddressDeliveryHouseNumber(long id, String houseNumber) throws DaoException;

    boolean updateAddressDeliveryBuildingNumber(long id, int buildingNumber) throws DaoException;

    boolean updateAddressDeliveryFlatNumber(long id, int flatNumber) throws DaoException;
}