package by.vchaikovski.coffeeshop.dao;

import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.entity.Delivery;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryDao extends BaseDao<Delivery> {
    List<Delivery> findByDeliveryType(Delivery.DeliveryType deliveryType);

    List<Delivery> findByAddressDelivery(AddressDelivery addressDelivery);

    List<Delivery> findByDateDelivery(LocalDateTime dateTime);

    List<Delivery> findByDateDelivery(LocalDateTime startPeriod, LocalDateTime endPeriod);

    boolean updateDeliveryType(long id, Delivery.DeliveryType deliveryType);

    boolean updateDeliveryDate(long id, LocalDateTime deliveryDate);
}
