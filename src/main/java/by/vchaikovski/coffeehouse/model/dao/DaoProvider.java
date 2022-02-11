package by.vchaikovski.coffeehouse.model.dao;

import by.vchaikovski.coffeehouse.model.dao.impl.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Dao provider.
 */
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DaoProvider getInstance() {
        if (instance == null) {
            instance = new DaoProvider();
        }
        return instance;
    }

    /**
     * Gets address delivery dao.
     *
     * @return the address delivery dao
     */
    public AddressDeliveryDao getAddressDeliveryDao() {
        return addressDeliveryDao;
    }

    /**
     * Gets bank card dao.
     *
     * @return the bank card dao
     */
    public BankCardDao getBankCardDao() {
        return bankCardDao;
    }

    /**
     * Gets bill dao.
     *
     * @return the bill dao
     */
    public BillDao getBillDao() {
        return billDao;
    }

    /**
     * Gets delivery dao.
     *
     * @return the delivery dao
     */
    public DeliveryDao getDeliveryDao() {
        return deliveryDao;
    }

    /**
     * Gets discount dao.
     *
     * @return the discount dao
     */
    public DiscountDao getDiscountDao() {
        return discountDao;
    }

    /**
     * Gets order dao.
     *
     * @return the order dao
     */
    public OrderDao getOrderDao() {
        return orderDao;
    }

    /**
     * Gets user dao.
     *
     * @return the user dao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Gets menu dao.
     *
     * @return the menu dao
     */
    public MenuDao getMenuDao() {
        return menuDao;
    }
}