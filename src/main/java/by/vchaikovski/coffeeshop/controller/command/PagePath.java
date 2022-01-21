package by.vchaikovski.coffeeshop.controller.command;

/*
 * @author Viktor Chaikovski
 *
 * The page paths
 */

public enum PagePath {
    ;
    public static final String INDEX_PAGE = "/index.jsp";
    public static final String LOG_IN_PAGE = "/jsp/login.jsp";
    public static final String MAIN_PAGE = "/jsp/main.jsp";
    public static final String MENU_PAGE = "/jsp/menu.jsp";
    public static final String REGISTRATION_CARD_PAGE = "/jsp/card/registration_card.jsp";
    public static final String CARD_INFO_PAGE = "/jsp/card/card_info.jsp";
    /* client pages */
    public static final String ORDER_CREATION_PAGE = "/jsp/order_creation.jsp";
    public static final String CART_PAGE = "/jsp/cart.jsp";
    public static final String ORDERS_HISTORY_PAGE = "/jsp/orders_history.jsp";
    public static final String ORDER_EVALUATION_PAGE = "/jsp/order_evaluation.jsp";
    public static final String BALANCE_REPLENISHMENT_PAGE = "/jsp/balance_replenishment.jsp";
    public static final String PAYMENT_PAGE = "/jsp/payment.jsp";
    public static final String REGISTRATION_PAGE = "/jsp/registration.jsp";
    public static final String MENU_PARAMETERS_PAGE = "/jsp/menu_parameters.jsp";
    /* admin pages */
    public static final String WAITING_ORDERS_PAGE = "/jsp/waiting_orders.jsp";
    public static final String ORDERS_PROCESSING_PAGE = "/jsp/orders_processing.jsp";
    public static final String QUANTITY_PRODUCTS_REPLENISHMENT_PAGE = "/jsp/quantity_product_replenishment.jsp";
    public static final String VISITORS_PROFILE_MANAGEMENT_PAGE = "/jsp/visitors_profile_management.jsp";
    public static final String MENU_CREATION_PAGE = "/jsp/menu_creation.jsp";
    public static final String MENU_UPDATING_PAGE = "/jsp/menu_updating.jsp";
    /* barista pages */
    public static final String ACTIVE_ORDERS_PAGE = "/jsp/active_orders.jsp";
    public static final String ORDER_STATUS_UPDATING_PAGE = "/jsp/order_status_updating.jps";
    /* error pages */
    public static final String ERROR_400_PAGE = "/jsp/error/error400.jsp";
    public static final String ERROR_500_PAGE = "/jsp/error/error500.jsp";
}