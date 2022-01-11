package by.vchaikovski.coffeeshop.model.service;

import by.vchaikovski.coffeeshop.model.service.impl.BankCardServiceImpl;
import by.vchaikovski.coffeeshop.model.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static ServiceProvider instance;
    private static UserService userService = UserServiceImpl.getInstance();
    private static BankCardService bankCardService = BankCardServiceImpl.getInstance();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        if(instance == null) {
            instance = new ServiceProvider();
        }
        return instance;
    }

    public static UserService getUserService() {
        return userService;
    }

    public static BankCardService getBankCardService() {
        return bankCardService;
    }
}