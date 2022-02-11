package by.vchaikovski.coffeehouse.model.service;

import by.vchaikovski.coffeehouse.model.service.impl.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Service provider.
 */
public class ServiceProvider {
    private static ServiceProvider instance;
    private final UserService userService;
    private final BankCardService bankCardService;
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final DiscountService discountService;
    private final BillService billService;
    private final MenuService menuService;


    private ServiceProvider() {
        userService = UserServiceImpl.getInstance();
        bankCardService = BankCardServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
        deliveryService = DeliveryServiceImpl.getInstance();
        discountService = DiscountServiceImpl.getInstance();
        billService = BillServiceImpl.getInstance();
        menuService = MenuServiceImpl.getInstance();
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ServiceProvider getInstance() {
        if(instance == null) {
            instance = new ServiceProvider();
        }
        return instance;
    }

    /**
     * Gets user service.
     *
     * @return the user service
     */
    public UserService getUserService() {
        return userService;
    }

    /**
     * Gets bank card service.
     *
     * @return the bank card service
     */
    public BankCardService getBankCardService() {
        return bankCardService;
    }

    /**
     * Gets order service.
     *
     * @return the order service
     */
    public OrderService getOrderService() {
        return orderService;
    }

    /**
     * Gets delivery service.
     *
     * @return the delivery service
     */
    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    /**
     * Gets discount service.
     *
     * @return the discount service
     */
    public DiscountService getDiscountService() {
        return discountService;
    }

    /**
     * Gets bill service.
     *
     * @return the bill service
     */
    public BillService getBillService() {
        return billService;
    }

    /**
     * Gets menu service.
     *
     * @return the menu service
     */
    public MenuService getMenuService() {
        return menuService;
    }
}