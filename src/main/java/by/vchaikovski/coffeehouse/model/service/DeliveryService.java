package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.entity.AddressDelivery;
import by.vchaikovski.coffeehouse.model.entity.Delivery;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Delivery service.
 */
public interface DeliveryService {

    /**
     * Create delivery long.
     *
     * @param deliveryParameters the delivery parameters
     * @return the long
     * @throws ServiceException the service exception
     */
    long createDelivery(Map<String, String> deliveryParameters) throws ServiceException;

    /**
     * Create address long.
     *
     * @param deliveryParameters the delivery parameters
     * @return the long
     * @throws ServiceException the service exception
     */
    long createAddress(Map<String, String> deliveryParameters) throws ServiceException;

    /**
     * Delete delivery boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteDelivery(long id) throws ServiceException;

    /**
     * Delete address boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean deleteAddress(long id) throws ServiceException;

    /**
     * Find delivery by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Delivery> findDeliveryById(long id) throws ServiceException;

    /**
     * Find address by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<AddressDelivery> findAddressById(long id) throws ServiceException;

    /**
     * Find delivery by address id list.
     *
     * @param addressId the address id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Delivery> findDeliveryByAddressId(long addressId) throws ServiceException;

    /**
     * Find delivery by date list.
     *
     * @param dateTime the date time
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Delivery> findDeliveryByDate(String dateTime) throws ServiceException;

    /**
     * Find delivery by period list.
     *
     * @param startPeriod the start period
     * @param endPeriod   the end period
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Delivery> findDeliveryByPeriod(String startPeriod, String endPeriod) throws ServiceException;

    /**
     * Find delivery by type list.
     *
     * @param deliveryType the delivery type
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Delivery> findDeliveryByType(String deliveryType) throws ServiceException;

    /**
     * Find address by street name list.
     *
     * @param streetName the street name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<AddressDelivery> findAddressByStreetName(String streetName) throws ServiceException;

    /**
     * Update delivery date boolean.
     *
     * @param id       the id
     * @param dateTime the date time
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateDeliveryDate(long id, String dateTime) throws ServiceException;

    /**
     * Update delivery type boolean.
     *
     * @param id           the id
     * @param deliveryType the delivery type
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateDeliveryType(long id, String deliveryType) throws ServiceException;

    /**
     * Update address street name boolean.
     *
     * @param id         the id
     * @param streetName the street name
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateAddressStreetName(long id, String streetName) throws ServiceException;

    /**
     * Update address house number boolean.
     *
     * @param id          the id
     * @param houseNumber the house number
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateAddressHouseNumber(long id, String houseNumber) throws ServiceException;

    /**
     * Update address building number boolean.
     *
     * @param id             the id
     * @param buildingNumber the building number
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateAddressBuildingNumber(long id, String buildingNumber) throws ServiceException;

    /**
     * Update address flat number boolean.
     *
     * @param id         the id
     * @param flatNumber the flat number
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateAddressFlatNumber(long id, String flatNumber) throws ServiceException;
}