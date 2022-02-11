package by.vchaikovski.coffeehouse.util.validator.impl;

import by.vchaikovski.coffeehouse.model.entity.Discount;
import by.vchaikovski.coffeehouse.model.entity.Menu;
import by.vchaikovski.coffeehouse.util.validator.FormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.vchaikovski.coffeehouse.controller.command.RequestParameter.*;
import static by.vchaikovski.coffeehouse.controller.command.SessionParameter.USER_ID;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Form validator.
 */
public class FormValidatorImpl extends DataValidatorImpl implements FormValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String UNCHECKED_PARAM_MESS = "Unchecked parameter: ";
    private static FormValidatorImpl instance;

    private FormValidatorImpl() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FormValidatorImpl getInstance() {
        if (instance == null) {
            instance = new FormValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isCardParametersValid(Map<String, String> cardParameters) {
        boolean result = true;
        if (cardParameters == null || cardParameters.isEmpty()) {
            logger.debug("Card parameters are empty");
            return false;
        }
        for (Map.Entry<String, String> param : cardParameters.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            switch (key) {
                case CARD_NUMBER -> {
                    if (!isCardNumberValid(value)) {
                        cardParameters.replace(key, WRONG_MEANING);
                        logger.debug("Card number is not correct");
                        result = false;
                    }
                }
                case CARD_EXPIRATION_DATE -> {
                    if (!isDateValid(value)) {
                        cardParameters.replace(key, WRONG_MEANING);
                        logger.debug("Expiration date is not correct");
                        result = false;
                    }
                }
                case CARD_AMOUNT -> {
                    if (!isNumberValid(value)) {
                        cardParameters.replace(key, WRONG_MEANING);
                        logger.debug("Card amount is not correct");
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNCHECKED_PARAM_MESS + key);
            }
        }
        return result;
    }

    @Override
    public boolean isUserParametersValid(Map<String, String> userParameters) {
        boolean result = true;
        if (userParameters == null || userParameters.isEmpty()) {
            logger.debug("Usr parameters are empty");
            return false;
        }
        for (Map.Entry<String, String> param : userParameters.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            switch (key) {
                case LOGIN -> {
                    if (!isLoginValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        logger.debug("Login is not correct");
                        result = false;
                    }
                }
                case PASSWORD -> {
                    if (!isPasswordValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        logger.debug("Password is not correct");
                        result = false;
                    }
                }
                case FIRST_NAME, LAST_NAME -> {
                    if (!isNameValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        logger.debug("First name is not correct");
                        result = false;
                    }
                }
                case EMAIL -> {
                    if (!isEmailValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        logger.debug("Email is not correct");
                        result = false;
                    }
                }
                case PHONE_NUMBER -> {
                    if (!isPhoneNumberValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        logger.debug("Phone number is not correct");
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNCHECKED_PARAM_MESS + key);
            }
        }
        return result;
    }

    @Override
    public boolean isMenuParametersValid(Map<String, String> menuParameters) {
        boolean result = true;
        if (menuParameters == null || menuParameters.isEmpty()) {
            logger.debug("Menu parameters are empty");
            return false;
        }
        for (Map.Entry<String, String> menuParam : menuParameters.entrySet()) {
            String key = menuParam.getKey();
            String value = menuParam.getValue();
            switch (key) {
                case MENU_NAME -> {
                    if (!isNameValid(value)) {
                        menuParameters.replace(key, WRONG_MEANING);
                        logger.debug("Menu name is not correct");
                        result = false;
                    }
                }
                case MENU_TYPE -> {
                    if (!isEnumContains(value, Menu.FoodType.class)) {
                        menuParameters.replace(key, WRONG_MEANING);
                        logger.debug("Menu type is not correct");
                        result = false;
                    }
                }
                case MENU_PRICE, MENU_QUANTITY_IN_STOCK -> {
                    if (!isNumberValid(value)) {
                        menuParameters.replace(key, WRONG_MEANING);
                        logger.debug("Quantity in stock is not correct");
                        result = false;
                    }
                }
                case MENU_DESCRIPTION -> {
                    if (!isTextValid(value)) {
                        menuParameters.replace(key, WRONG_MEANING);
                        logger.debug("Description is not correct");
                        result = false;
                    }
                }
                case MENU_IMAGE -> {
                    if (!isPicture(value)) {
                        menuParameters.replace(key, WRONG_MEANING);
                        logger.debug("Image is not correct");
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNCHECKED_PARAM_MESS + key);
            }
        }
        return result;
    }

    @Override
    public boolean isOrderParameterValid(Map<String, String> orderParameters) {
        boolean result = true;
        if (orderParameters == null || orderParameters.isEmpty() || !orderParameters.containsKey(USER_ID)) {
            logger.debug("Order parameters are empty");
            return false;
        }
        for (Map.Entry<String, String> orderParam : orderParameters.entrySet()) {
            String key = orderParam.getKey();
            String value = orderParam.getValue();
            switch (key) {
                case USER_ID -> {
                    if (!isNumberValid(value)) {
                        orderParameters.replace(key, WRONG_MEANING);
                        logger.debug("User id is not correct");
                        result = false;
                    }
                }
                case COMMENT -> {
                    if (!isTextValid(value)) {
                        orderParameters.replace(key, WRONG_MEANING);
                        logger.debug("Comment is not correct");
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNCHECKED_PARAM_MESS + key);
            }
        }
        return result;
    }

    @Override
    public boolean isAddressParametersValid(Map<String, String> deliveryParameters) {
        boolean result = true;
        if (deliveryParameters == null || deliveryParameters.isEmpty()) {
            logger.debug("Address parameters are empty");
            return false;
        }
        for (Map.Entry<String, String> param : deliveryParameters.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            switch (key) {
                case STREET -> {
                    if (!isStreetValid(value)) {
                        deliveryParameters.replace(key, WRONG_MEANING);
                        logger.debug("Street name is not correct");
                        result = false;
                    }
                }
                case HOUSE_NUMBER -> {
                    if (!isHouseNumberValid(value)) {
                        deliveryParameters.replace(key, WRONG_MEANING);
                        logger.debug("House number is not correct");
                        result = false;
                    }
                }
                case BUILDING_NUMBER, FLAT_NUMBER -> {
                    if (!isNumberValid(value)) {
                        deliveryParameters.replace(key, WRONG_MEANING);
                        logger.debug("Building or flat number is not correct");
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNCHECKED_PARAM_MESS + key);
            }
        }
        return result;
    }

    @Override
    public boolean isDiscountParametersValid(Map<String, String> discountParameters) {
        boolean result = true;
        if (discountParameters == null || discountParameters.isEmpty()) {
            logger.debug("Discount parameters are empty");
            return false;
        }
        String discountType = discountParameters.get(DISCOUNT_TYPE);
        String discountRate = discountParameters.get(DISCOUNT_RATE);
        if (!isEnumContains(discountType, Discount.DiscountType.class)) {
            discountParameters.replace(DISCOUNT_TYPE, WRONG_MEANING);
            logger.debug("Discount type is not correct");
            result = false;
        }
        if (!isNumberValid(discountRate)) {
            discountParameters.replace(DISCOUNT_RATE, WRONG_MEANING);
            logger.debug("Discount rate is not correct");
            result = false;
        }
        return result;
    }
}