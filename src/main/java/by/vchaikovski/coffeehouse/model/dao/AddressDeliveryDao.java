package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.model.entity.AddressDelivery;
import by.vchaikovski.coffeehouse.exception.DaoException;

import java.util.List;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Address delivery dao.
 */
public interface AddressDeliveryDao extends BaseDao<AddressDelivery> {
    /**
     * Find by street name list.
     *
     * @param streetName the street name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<AddressDelivery> findByStreetName(String streetName) throws DaoException;

    /**
     * Update address delivery street name boolean.
     *
     * @param id         the id
     * @param streetName the street name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateAddressDeliveryStreetName(long id, String streetName) throws DaoException;

    /**
     * Update address delivery house number boolean.
     *
     * @param id          the id
     * @param houseNumber the house number
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateAddressDeliveryHouseNumber(long id, String houseNumber) throws DaoException;

    /**
     * Update address delivery building number boolean.
     *
     * @param id             the id
     * @param buildingNumber the building number
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateAddressDeliveryBuildingNumber(long id, int buildingNumber) throws DaoException;

    /**
     * Update address delivery flat number boolean.
     *
     * @param id         the id
     * @param flatNumber the flat number
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateAddressDeliveryFlatNumber(long id, int flatNumber) throws DaoException;
}