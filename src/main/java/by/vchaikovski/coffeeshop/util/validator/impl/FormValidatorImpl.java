package by.vchaikovski.coffeeshop.util.validator.impl;

import by.vchaikovski.coffeeshop.model.entity.Delivery;
import by.vchaikovski.coffeeshop.util.validator.FormValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public class FormValidatorImpl extends DataValidatorImpl implements FormValidator {
    private static final Logger logger = LogManager.getLogger();
    private static final String UNKNOWN_PARAM_MESS = "Unknown parameter: ";
    private static FormValidatorImpl instance;

    private FormValidatorImpl() {
    }

    public static FormValidatorImpl getInstance() {
        if (instance == null) {
            instance = new FormValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isCardParametersValid(Map<String, String> cardParameters) {
        boolean result = !cardParameters.isEmpty();
        for (Map.Entry<String, String> param : cardParameters.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            switch (key) {
                case CARD_NUMBER -> {
                    if (!isCardNumberValid(value)) {
                        cardParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }

                case CARD_EXPIRATION_DATE -> {
                    if (!isDateValid(value)) {
                        cardParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                case CARD_AMOUNT -> {
                    if (!isNumberValid(value)) {
                        cardParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNKNOWN_PARAM_MESS + key);
            }
        }
        return result;
    }

    @Override
    public boolean isUserParametersValid(Map<String, String> userParameters) {
        boolean result = !userParameters.isEmpty();
        for (Map.Entry<String, String> param : userParameters.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            switch (key) {
                case LOGIN -> {
                    if (!isLoginValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                case PASSWORD -> {
                    if (!isPasswordValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                case FIRST_NAME, LAST_NAME -> {
                    if (!isNameValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                case EMAIL -> {
                    if (!isEmailValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                case PHONE_NUMBER -> {
                    if (!isPhoneNumberValid(value)) {
                        userParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNKNOWN_PARAM_MESS + key);
            }
        }
        return result;
    }

    @Override
    public boolean isMenuParametersValid(Map<String, String> menuParameters) {
        return false;
    }

    @Override
    public boolean isAddressParametersValid(Map<String, String> deliveryParameters) {
        boolean result = !deliveryParameters.isEmpty();
        for (Map.Entry<String, String> param : deliveryParameters.entrySet()) {
            String key = param.getKey();
            String value = param.getValue();
            switch (key) {
                case STREET -> {
                    if (!isStreetValid(value)) {
                        deliveryParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                case HOUSE_NUMBER -> {
                    if (!isHouseNumberValid(value)) {
                        deliveryParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                case BUILDING_NUMBER, FLAT_NUMBER -> {
                    if (!isNumberValid(value)) {
                        deliveryParameters.replace(key, WRONG_MEANING);
                        result = false;
                    }
                }
                default -> logger.debug(() -> UNKNOWN_PARAM_MESS + key);
            }
        }
        return result;
    }
}