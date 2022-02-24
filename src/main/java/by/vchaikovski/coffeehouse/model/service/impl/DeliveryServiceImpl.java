package by.vchaikovski.coffeehouse.model.service.impl;

import by.vchaikovski.coffeehouse.exception.DaoException;
import by.vchaikovski.coffeehouse.exception.ServiceException;
import by.vchaikovski.coffeehouse.model.dao.AddressDeliveryDao;
import by.vchaikovski.coffeehouse.model.dao.DaoProvider;
import by.vchaikovski.coffeehouse.model.dao.DeliveryDao;
import by.vchaikovski.coffeehouse.model.entity.AddressDelivery;
import by.vchaikovski.coffeehouse.model.entity.Delivery;
import by.vchaikovski.coffeehouse.model.service.DeliveryService;
import by.vchaikovski.coffeehouse.util.validator.DataValidator;
import by.vchaikovski.coffeehouse.util.validator.FormValidator;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import by.vchaikovski.coffeehouse.util.validator.impl.FormValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Delivery service.
 */
public class DeliveryServiceImpl implements DeliveryService {
    private static final Logger logger = LogManager.getLogger();
    private static DeliveryService instance;
    private final DeliveryDao deliveryDao;
    private final AddressDeliveryDao addressDao;

    /**
     * Instantiates a new Delivery service.
     */
    public DeliveryServiceImpl() {
        deliveryDao = DaoProvider.getInstance().getDeliveryDao();
        addressDao = DaoProvider.getInstance().getAddressDeliveryDao();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DeliveryService getInstance() {
        if (instance == null) {
            instance = new DeliveryServiceImpl();
        }
        return instance;
    }

    @Override
    public long createDelivery(Map<String, String> deliveryParameters) throws ServiceException {
        DataValidator validator = DataValidatorImpl.getInstance();
        long deliveryId = 0;
        String deliveryType = deliveryParameters.get(DELIVERY_TYPE);
        String deliveryTime = deliveryParameters.get(DELIVERY_TIME);
        if (validator.isEnumContains(deliveryType, Delivery.DeliveryType.class)
                && validator.isDateValid(deliveryTime)) {
            Delivery.DeliveryType type = Delivery.DeliveryType.valueOf(deliveryType.toUpperCase());
            LocalDate date = LocalDate.parse(deliveryTime);
            if (!validator.isDateLaterCurrently(date)) {
                deliveryParameters.replace(DELIVERY_TIME, WRONG_MEANING);
                return deliveryId;
            }
            long addressId = createAddress(deliveryParameters);
            Delivery delivery = new Delivery(type, date, addressId);
            try {
                deliveryId = deliveryDao.create(delivery);
            } catch (DaoException e) {
                String message = "Delivery can't be inserted in data base";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        } else {
            if (!validator.isEnumContains(deliveryType, Delivery.DeliveryType.class)) {
                deliveryParameters.replace(DELIVERY_TYPE, WRONG_MEANING);
            }
            if (!validator.isDateValid(deliveryTime)) {
                deliveryParameters.replace(DELIVERY_TIME, WRONG_MEANING);
            }
        }
        return deliveryId;
    }

    @Override
    public long createAddress(Map<String, String> deliveryParameters) throws ServiceException {
        long addressId = 0;
        FormValidator formValidator = FormValidatorImpl.getInstance();
        if (formValidator.isAddressParametersValid(deliveryParameters) &&
                deliveryParameters.containsKey(STREET) && deliveryParameters.containsKey(HOUSE_NUMBER)) {
            String streetName = deliveryParameters.get(STREET);
            String houseNumber = deliveryParameters.get(HOUSE_NUMBER);
            AddressDelivery addressDelivery = new AddressDelivery.AddressDeliveryBuilder()
                    .setStreetName(streetName)
                    .setHouseNumber(houseNumber)
                    .build();
            if (deliveryParameters.containsKey(BUILDING_NUMBER)) {
                String buildingNumber = deliveryParameters.get(BUILDING_NUMBER);
                addressDelivery.setBuildingNumber(Integer.parseInt(buildingNumber));
            }
            if (deliveryParameters.containsKey(FLAT_NUMBER)) {
                String flatNumber = deliveryParameters.get(FLAT_NUMBER);
                addressDelivery.setFlatNumber(Integer.parseInt(flatNumber));
            }
            try {
                addressId = addressDao.create(addressDelivery);
            } catch (DaoException e) {
                String message = "Address delivery can't be inserted in data base";
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return addressId;
    }

    @Override
    public boolean deleteDelivery(long id) throws ServiceException {
        boolean result;
        try {
            result = deliveryDao.deleteById(id);
        } catch (DaoException e) {
            String message = "Delivery can't be deleted by id=" + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    @Override
    public boolean deleteAddress(long id) throws ServiceException {
        boolean result;
        try {
            result = addressDao.deleteById(id);
        } catch (DaoException e) {
            String message = "Address delivery can't be deleted by id=" + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return result;
    }

    @Override
    public Optional<Delivery> findDeliveryById(long id) throws ServiceException {
        Optional<Delivery> delivery;
        try {
            delivery = deliveryDao.findById(id);
        } catch (DaoException e) {
            String message = "Delivery can't be found by id=" + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return delivery;
    }

    @Override
    public Optional<AddressDelivery> findAddressById(long id) throws ServiceException {
        Optional<AddressDelivery> address;
        try {
            address = addressDao.findById(id);
        } catch (DaoException e) {
            String message = "Address can't be found by id=" + id;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return address;
    }

    @Override
    public List<Delivery> findDeliveryByAddressId(long addressId) throws ServiceException {
        List<Delivery> deliveryList;
        try {
            deliveryList = deliveryDao.findByAddressDelivery(addressId);
        } catch (DaoException e) {
            String message = "Delivery can't be found by address id=" + addressId;
            logger.error(message, e);
            throw new ServiceException(message, e);
        }
        return deliveryList;
    }

    @Override
    public List<Delivery> findDeliveryByDate(String timeDelivery) throws ServiceException {
        List<Delivery> deliveryList = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateValid(timeDelivery)) {
            LocalDate time = LocalDate.parse(timeDelivery);
            try {
                deliveryList = deliveryDao.findByDateDelivery(time);
            } catch (DaoException e) {
                String message = "Delivery can't be found by delivery time=" + timeDelivery;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return deliveryList;
    }

    @Override
    public List<Delivery> findDeliveryByPeriod(String startPeriod, String endPeriod) throws ServiceException {
        List<Delivery> deliveryList = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateValid(startPeriod) && validator.isDateValid(endPeriod)) {

            LocalDate start = LocalDate.parse(startPeriod);
            LocalDate end = LocalDate.parse(endPeriod);
            try {
                deliveryList = deliveryDao.findByDateDelivery(start, end);
            } catch (DaoException e) {
                String message = "Delivery can't be found by delivery period=" + startPeriod + " - " + endPeriod;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return deliveryList;
    }

    @Override
    public List<Delivery> findDeliveryByType(String deliveryType) throws ServiceException {
        List<Delivery> deliveryList = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(deliveryType, Delivery.DeliveryType.class)) {
            Delivery.DeliveryType type = Delivery.DeliveryType.valueOf(deliveryType.toUpperCase());
            try {
                deliveryList = deliveryDao.findByDeliveryType(type);
            } catch (DaoException e) {
                String message = "Delivery can't be found by delivery type=" + deliveryType;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return deliveryList;
    }

    @Override
    public List<AddressDelivery> findAddressByStreetName(String streetName) throws ServiceException {
        List<AddressDelivery> addressList = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isStreetValid(streetName)) {
            try {
                addressList = addressDao.findByStreetName(streetName);
            } catch (DaoException e) {
                String message = "Address can't be found by street name=" + streetName;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return addressList;
    }

    @Override
    public boolean updateDeliveryDate(long id, String timeDelivery) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isDateValid(timeDelivery)) {
            LocalDate time = LocalDate.parse(timeDelivery);
            try {
                result = deliveryDao.updateDeliveryDate(id, time);
            } catch (DaoException e) {
                String message = "Delivery can't be updated by delivery time=" + timeDelivery;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateDeliveryType(long id, String deliveryType) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(deliveryType, Delivery.DeliveryType.class)) {
            Delivery.DeliveryType type = Delivery.DeliveryType.valueOf(deliveryType);
            try {
                result = deliveryDao.updateDeliveryType(id, type);
            } catch (DaoException e) {
                String message = "Delivery can't be updated by delivery type=" + deliveryType;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateAddressStreetName(long id, String streetName) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isStreetValid(streetName)) {
            try {
                result = addressDao.updateAddressDeliveryStreetName(id, streetName);
            } catch (DaoException e) {
                String message = "Delivery can't be updated by street name=" + streetName;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateAddressHouseNumber(long id, String houseNumber) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isHouseNumberValid(houseNumber)) {
            try {
                result = addressDao.updateAddressDeliveryHouseNumber(id, houseNumber);
            } catch (DaoException e) {
                String message = "Delivery can't be updated by house number=" + houseNumber;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateAddressBuildingNumber(long id, String buildingNumber) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(buildingNumber)) {
            try {
                result = addressDao.updateAddressDeliveryBuildingNumber(id, Integer.parseInt(buildingNumber));
            } catch (DaoException e) {
                String message = "Delivery can't be updated by building number=" + buildingNumber;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }

    @Override
    public boolean updateAddressFlatNumber(long id, String flatNumber) throws ServiceException {
        boolean result = false;
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isNumberValid(flatNumber)) {
            try {
                result = addressDao.updateAddressDeliveryFlatNumber(id, Integer.parseInt(flatNumber));
            } catch (DaoException e) {
                String message = "Delivery can't be updated by flat number=" + flatNumber;
                logger.error(message, e);
                throw new ServiceException(message, e);
            }
        }
        return result;
    }
}