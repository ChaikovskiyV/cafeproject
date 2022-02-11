package by.vchaikovski.coffeehouse.model.dao.mapper;

import by.vchaikovski.coffeehouse.model.dao.mapper.impl.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Mapper provider.
 */
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static MapperProvider getInstance() {
        return instance;
    }

    /**
     * Gets address delivery mapper.
     *
     * @return the address delivery mapper
     */
    public AddressDeliveryMapperImpl getAddressDeliveryMapper() {
        return addressDeliveryMapper;
    }

    /**
     * Gets bank card mapper.
     *
     * @return the bank card mapper
     */
    public BankCardMapperImpl getBankCardMapper() {
        return bankCardMapper;
    }

    /**
     * Gets bill mapper.
     *
     * @return the bill mapper
     */
    public BillMapperImpl getBillMapper() {
        return billMapper;
    }

    /**
     * Gets delivery mapper.
     *
     * @return the delivery mapper
     */
    public DeliveryMapperImpl getDeliveryMapper() {
        return deliveryMapper;
    }

    /**
     * Gets discount mapper.
     *
     * @return the discount mapper
     */
    public DiscountMapperImpl getDiscountMapper() {
        return discountMapper;
    }

    /**
     * Gets food mapper.
     *
     * @return the food mapper
     */
    public FoodOrderMapperImpl getFoodMapper() {
        return foodMapper;
    }

    /**
     * Gets menu mapper.
     *
     * @return the menu mapper
     */
    public MenuMapperImpl getMenuMapper() {
        return menuMapper;
    }

    /**
     * Gets user mapper.
     *
     * @return the user mapper
     */
    public UserMapperImpl getUserMapper() {
        return userMapper;
    }
}