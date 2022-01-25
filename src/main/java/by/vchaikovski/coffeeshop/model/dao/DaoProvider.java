package by.vchaikovski.coffeeshop.model.dao;

import by.vchaikovski.coffeeshop.model.dao.impl.*;

public class DaoProvider {
    private static DaoProvider instance = new DaoProvider();
    private final AddressDeliveryDao addressDeliveryDao;
    private final BankCardDao bankCardDao;
    private final BillDao billDao;
    private final DeliveryDao deliveryDao;
    private final DiscountDao discountDao;
    private final OrderDao orderDao;
    private final UserDao userDao;
    private final MenuDao menuDao;

    private DaoProvider() {
        addressDeliveryDao = AddressDeliveryDaoImpl.getInstance();
        bankCardDao = BankCardDaoImpl.getInstance();
        billDao = BillDaoImpl.getInstance();
        deliveryDao = DeliveryDaoImpl.getInstance();
        discountDao = DiscountDaoImpl.getInstance();
        menuDao = MenuDaoImpl.getInstance();
        orderDao = OrderDaoImpl.getInstance();
        userDao = UserDaoImpl.getInstance();
    }

    public static DaoProvider getInstance() {
        if(instance == null) {
            instance = new DaoProvider();
        }
        return instance;
    }

    public AddressDeliveryDao getAddressDeliveryDao() {
        return addressDeliveryDao;
    }

    public BankCardDao getBankCardDao() {
        return bankCardDao;
    }

    public BillDao getBillDao() {
        return billDao;
    }

    public DeliveryDao getDeliveryDao() {
        return deliveryDao;
    }

    public DiscountDao getDiscountDao() {
        return discountDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public MenuDao getMenuDao() {
        return menuDao;
    }
}