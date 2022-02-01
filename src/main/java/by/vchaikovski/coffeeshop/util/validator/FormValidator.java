package by.vchaikovski.coffeeshop.util.validator;

import java.util.Map;

public interface FormValidator {
    boolean isCardParametersValid(Map<String, String> cardParameters);

    boolean isUserParametersValid(Map<String, String> userParameters);

    boolean isMenuParametersValid(Map<String, String> menuParameters);

    boolean isOrderParameterValid(Map<String, String> orderParameters);

    boolean isAddressParametersValid(Map<String, String> deliveryParameters);

    boolean isDiscountParametersValid(Map<String, String> discountParameters);
}