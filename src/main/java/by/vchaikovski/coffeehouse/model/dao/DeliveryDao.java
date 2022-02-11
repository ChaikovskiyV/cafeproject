package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.model.entity.Delivery;

import java.time.LocalDate;
import java.util.List;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Delivery dao.
 */
public interface DeliveryDao extends BaseDao<Delivery> {
    /**
     * Find by delivery type list.
     *
     * @param deliveryType the delivery type
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Delivery> findByDeliveryType(Delivery.DeliveryType deliveryType) throws DaoException;

    /**
     * Find by address delivery list.
     *
     * @param addressDeliveryId the address delivery id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Delivery> findByAddressDelivery(long addressDeliveryId) throws DaoException;

    /**
     * Find by date delivery list.
     *
     * @param dateTime the date time
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Delivery> findByDateDelivery(LocalDate dateTime) throws DaoException;

    /**
     * Find by date delivery list.
     *
     * @param startPeriod the start period
     * @param endPeriod   the end period
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Delivery> findByDateDelivery(LocalDate startPeriod, LocalDate endPeriod) throws DaoException;

    /**
     * Update delivery type boolean.
     *
     * @param id           the id
     * @param deliveryType the delivery type
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateDeliveryType(long id, Delivery.DeliveryType deliveryType) throws DaoException;

    /**
     * Update delivery date boolean.
     *
     * @param id           the id
     * @param deliveryDate the delivery date
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateDeliveryDate(long id, LocalDate deliveryDate) throws DaoException;
}