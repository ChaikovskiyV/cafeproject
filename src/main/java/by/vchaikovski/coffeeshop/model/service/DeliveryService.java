package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.model.entity.Delivery;

import java.util.List;
import java.util.Map;

public interface DeliveryService {

    long createDelivery(Map<String, String> deliveryParameters) throws ServiceException;

    long createAddress(Map<String, String> deliveryParameters) throws ServiceException;

    boolean deleteDelivery(long id) throws ServiceException;

    boolean deleteAddress(long id) throws ServiceException;

    List<Delivery> findDeliveryByAddressId(long addressId) throws ServiceException;

    List<Delivery> findDeliveryByDate(String dateTime) throws ServiceException;

    List<Delivery> findDeliveryByPeriod(String startPeriod, String endPeriod) throws ServiceException;

    List<Delivery> findDeliveryByType(String deliveryType) throws ServiceException;

    List<AddressDelivery> findAddressByStreetName(String streetName) throws ServiceException;

    boolean updateDeliveryDate(long id, String dateTime) throws ServiceException;

    boolean updateDeliveryType(long id, String deliveryType) throws ServiceException;

    boolean updateAddressStreetName(long id, String streetName) throws ServiceException;

    boolean updateAddressHouseNumber(long id, String houseNumber) throws ServiceException;

    boolean updateAddressBuildingNumber(long id, String buildingNumber) throws ServiceException;

    boolean updateAddressFlatNumber(long id, String flatNumber) throws ServiceException;
}