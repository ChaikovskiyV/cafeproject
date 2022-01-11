package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.entity.Delivery;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;

public interface DeliveryDao extends BaseDao<Delivery> {
    List<Delivery> findByDeliveryType(Delivery.DeliveryType deliveryType) throws DaoException;

    List<Delivery> findByAddressDelivery(long addressDeliveryId) throws DaoException;

    List<Delivery> findByDateDelivery(LocalDateTime dateTime) throws DaoException;

    List<Delivery> findByDateDelivery(LocalDateTime startPeriod, LocalDateTime endPeriod) throws DaoException;

    boolean updateDeliveryType(long id, Delivery.DeliveryType deliveryType) throws DaoException;

    boolean updateDeliveryDate(long id, LocalDateTime deliveryDate) throws DaoException;
}
