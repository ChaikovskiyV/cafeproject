package by.vchaikovski.coffeeshop.model.dao.mapper;

import by.vchaikovski.coffeeshop.model.dao.mapper.impl.*;

public class MapperProvider {
    private static final MapperProvider instance = new MapperProvider();
    private static final AddressDeliveryMapperImpl addressDeliveryMapper = AddressDeliveryMapperImpl.getInstance();
    private static final BankCardMapperImpl bankCardMapper = BankCardMapperImpl.getInstance();
    private static final BillMapperImpl billMapper = BillMapperImpl.getInstance();
    private static final DeliveryMapperImpl deliveryMapper = DeliveryMapperImpl.getInstance();
    private static final DiscountMapperImpl discountMapper = DiscountMapperImpl.getInstance();
    private static final FoodOrderMapperImpl foodMapper = FoodOrderMapperImpl.getInstance();
    private static final MenuMapperImpl menuMapper = MenuMapperImpl.getInstance();
    private static final UserMapperImpl userMapper = UserMapperImpl.getInstance();

    private MapperProvider() {
    }

    public static MapperProvider getInstance() {
        return instance;
    }

    public AddressDeliveryMapperImpl getAddressDeliveryMapper() {
        return addressDeliveryMapper;
    }

    public BankCardMapperImpl getBankCardMapper() {
        return bankCardMapper;
    }

    public BillMapperImpl getBillMapper() {
        return billMapper;
    }

    public DeliveryMapperImpl getDeliveryMapper() {
        return deliveryMapper;
    }

    public DiscountMapperImpl getDiscountMapper() {
        return discountMapper;
    }

    public FoodOrderMapperImpl getFoodMapper() {
        return foodMapper;
    }

    public MenuMapperImpl getMenuMapper() {
        return menuMapper;
    }

    public UserMapperImpl getUserMapper() {
        return userMapper;
    }
}