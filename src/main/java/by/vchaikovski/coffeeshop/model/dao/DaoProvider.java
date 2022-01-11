package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.dao.impl.*;

public class DaoProvider {
    private static final DaoProvider instance = new DaoProvider();
    private static final AddressDeliveryDaoImpl addressDeliveryDao = AddressDeliveryDaoImpl.getInstance();
    private static final BankCardDaoImpl bankCardDao = BankCardDaoImpl.getInstance();
    private static final BillDaoImpl billDao = BillDaoImpl.getInstance();
    private static final DeliveryDaoImpl deliveryDao = DeliveryDaoImpl.getInstance();
    private static final DiscountDaoImpl discountDao = DiscountDaoImpl.getInstance();
    private static final OrderDaoImpl orderDao = OrderDaoImpl.getInstance();
    private static final UserDaoImpl userDao = UserDaoImpl.getInstance();

    private DaoProvider() {
    }

    public static DaoProvider getInstance() {
        return instance;
    }

    public AddressDeliveryDaoImpl getAddressDeliveryDao() {
        return addressDeliveryDao;
    }

    public BankCardDaoImpl getBankCardDao() {
        return bankCardDao;
    }

    public BillDaoImpl getBillDao() {
        return billDao;
    }

    public DeliveryDaoImpl getDeliveryDao() {
        return deliveryDao;
    }

    public DiscountDaoImpl getDiscountDao() {
        return discountDao;
    }

    public OrderDaoImpl getOrderDao() {
        return orderDao;
    }

    public UserDaoImpl getUserDao() {
        return userDao;
    }
}