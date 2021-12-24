package by.vchaikovski.coffeeshop.dao.mapper;

import by.vchaikovski.coffeeshop.dao.DeliveryDao;
import by.vchaikovski.coffeeshop.entity.AddressDelivery;
import by.vchaikovski.coffeeshop.entity.Delivery;
import by.vchaikovski.coffeeshop.exception.ConnectionPoolException;
import by.vchaikovski.coffeeshop.exception.DaoException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class DeliveryDaoImpl implements DeliveryDao {
    @Override
    public List<Delivery> findAll() throws DaoException, ConnectionPoolException {
        return null;
    }

    @Override
    public Optional<Delivery> findById(long id) throws ConnectionPoolException, DaoException {
        return Optional.empty();
    }

    @Override
    public long create(Delivery entity) throws ConnectionPoolException, DaoException {
        return 0;
    }

    @Override
    public boolean update(long id, Delivery entity) throws ConnectionPoolException, DaoException {
        return false;
    }

    @Override
    public boolean deleteById(long id) throws ConnectionPoolException, DaoException {
        return false;
    }

    @Override
    public List<Delivery> findByDeliveryType(Delivery.DeliveryType deliveryType) {
        return null;
    }

    @Override
    public List<Delivery> findByAddressDelivery(AddressDelivery addressDelivery) {
        return null;
    }

    @Override
    public List<Delivery> findByDateDelivery(LocalDateTime dateTime) {
        return null;
    }

    @Override
    public List<Delivery> findByDateDelivery(LocalDateTime startPeriod, LocalDateTime endPeriod) {
        return null;
    }

    @Override
    public boolean updateDeliveryType(long id, Delivery.DeliveryType deliveryType) {
        return false;
    }

    @Override
    public boolean updateDeliveryDate(long id, LocalDateTime deliveryDate) {
        return false;
    }
}