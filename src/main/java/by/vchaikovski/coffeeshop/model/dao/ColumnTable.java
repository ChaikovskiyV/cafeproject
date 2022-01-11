package by.vchaikovski.coffeeshop.model.dao;

public enum ColumnTable {
    ;
    public static final String USER_ID = "user_id";
    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_EMAIL = "email";
    public static final String USER_PHONE = "phone_number";
    public static final String USER_ROLE = "role";
    public static final String USER_STATUS = "user_status";
    public static final String USER_DISCOUNT_ID = "fk_discount_id";
    public static final String DISCOUNT_ID = "discount_id";
    public static final String DISCOUNT_TYPE = "type";
    public static final String DISCOUNT_RATE = "rate";
    public static final String CARD_ID = "card_id";
    public static final String CARD_NUMBER = "number";
    public static final String CARD_EXPIRATION_DATE = "expiration_date";
    public static final String CARD_AMOUNT = "amount";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_STATUS = "order_status";
    public static final String ORDER_CREATION_DATE = "creation_date";
    public static final String ORDER_COMMENT = "comment";
    public static final String ORDER_EVALUATION = "evaluation";
    public static final String ORDER_DELIVERY_ID = "fk_delivery_id";
    public static final String ORDER_BILL_ID = "fk_bill_id";
    public static final String ORDER_USER_ID = "fk_user_id";
    public static final String BILL_ID = "bill_id";
    public static final String BILL_TOTAL_PRICE = "total_price";
    public static final String BILL_STATUS = "bill_status";
    public static final String BILL_PAYMENT_DATE = "payment_date";
    public static final String MENU_ID = "menu_id";
    public static final String MENU_NAME = "name";
    public static final String MENU_TYPE = "product_type";
    public static final String MENU_DESCRIPTION = "description";
    public static final String MENU_PRICE = "price";
    public static final String MENU_QUANTITY_IN_STOCK = "quantity_in_stock";
    public static final String MENU_PRODUCTION_DATE = "production_date";
    public static final String MENU_EXPIRATION_DATE = "expiration_date";
    public static final String MENU_IMAGE = "image";
    public static final String CART_ORDER_ID = "fk_order_id";
    public static final String CART_MENU_ID = "fk_menu_id";
    public static final String CART_QUANTITY = "quantity";
    public static final String DELIVERY_ID = "delivery_id";
    public static final String DELIVERY_TYPE = "delivery_type";
    public static final String DELIVERY_TIME = "delivery_time";
    public static final String DELIVERY_ADDRESS_ID = "fk_address_id";
    public static final String ADDRESS_ID = "address_id";
    public static final String ADDRESS_STREET = "street";
    public static final String ADDRESS_HOUSE_NUMBER = "house_number";
    public static final String ADDRESS_BUILDING_NUMBER = "building_number";
    public static final String ADDRESS_FLAT_NUMBER = "flat_number";
}