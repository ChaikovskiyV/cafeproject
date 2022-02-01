package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.model.service.impl.*;

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

    public static ServiceProvider getInstance() {
        if(instance == null) {
            instance = new ServiceProvider();
        }
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public BankCardService getBankCardService() {
        return bankCardService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public DiscountService getDiscountService() {
        return discountService;
    }

    public BillService getBillService() {
        return billService;
    }

    public MenuService getMenuService() {
        return menuService;
    }
}