package by.vchaikovski.coffeehouse.util.validator;

import java.util.Map;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Form validator.
 */
public interface FormValidator {
    /**
     * Is card parameters valid boolean.
     *
     * @param cardParameters the card parameters
     * @return the boolean
     */
    boolean isCardParametersValid(Map<String, String> cardParameters);

    /**
     * Is user parameters valid boolean.
     *
     * @param userParameters the user parameters
     * @return the boolean
     */
    boolean isUserParametersValid(Map<String, String> userParameters);

    /**
     * Is menu parameters valid boolean.
     *
     * @param menuParameters the menu parameters
     * @return the boolean
     */
    boolean isMenuParametersValid(Map<String, String> menuParameters);

    /**
     * Is order parameter valid boolean.
     *
     * @param orderParameters the order parameters
     * @return the boolean
     */
    boolean isOrderParameterValid(Map<String, String> orderParameters);

    /**
     * Is address parameters valid boolean.
     *
     * @param deliveryParameters the delivery parameters
     * @return the boolean
     */
    boolean isAddressParametersValid(Map<String, String> deliveryParameters);

    /**
     * Is discount parameters valid boolean.
     *
     * @param discountParameters the discount parameters
     * @return the boolean
     */
    boolean isDiscountParametersValid(Map<String, String> discountParameters);
}