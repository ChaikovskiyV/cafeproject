package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.exception.ServiceException;
import by.vchaikovski.coffeeshop.model.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.model.entity.Delivery;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface DeliveryService {

    long createDelivery(Map<String, String> deliveryParameters) throws ServiceException;

    long createAddress(Map<String, String> deliveryParameters) throws ServiceException;

    boolean deleteDelivery(long id);

    boolean deleteAddress(long id);

    List<Delivery> findDeliveryByAddressId(long addressId);

    List<Delivery> findDeliveryByDate(LocalDateTime dateTime);

    List<Delivery> findDeliveryByPeriod(LocalDateTime startPeriod, LocalDateTime endPeriod);

    List<Delivery> findDeliveryByType(Delivery.DeliveryType type);

    List<AddressDelivery> findAddressByStreetName(String streetName);

    boolean updateDeliveryDate(long id, LocalDateTime dateTime);

    boolean updateDeliveryType(long id, Delivery.DeliveryType type);

    boolean updateAddressStreetName(String streetName);

    boolean updateAddressHouseNumber(String houseNumber);

    boolean updateAddressBuildingNumber(int buildingNumber);

    boolean updateAddressFlatNumber(int flatNumber);
}