package by.vchaikovski.coffeeshop.util.validator.impl;

import by.vchaikovski.coffeeshop.util.validator.DataValidator;

import java.time.LocalDateTime;

public class DataValidatorImpl implements DataValidator {
    private static final String LOGIN_REGEX = "\\p{Alpha}\\w{5,19}";
    private static final String PASSWORD_REGEX = "\\w{6,20}";
    private static final String USER_NAME_REGEX = "(.{4,20})(([a-zA-Z]+[-\\s]?[a-zA-Z]+)|([а-яА-Я]+[-\\s]?[а-яА-Я]+))";
    private static final String EMAIL_REGEX = "(.{8,35})(\\w+[._-]?\\w+@\\p{Alpha}+\\.\\p{Alpha}+)";
    private static final String PHONE_NUMBER_REGEX = "\\+\\d{12}";
    private static final String NUMBER_REGEX = "\\d+";
    private static final String STREET_NAME_REGEX = "(.{3,35})(([a-zA-Z]+[-\\s]?[a-zA-Z]+)|([а-яА-Я]+[-\\s]?[а-яА-Я]+))";
    private static final String HOUSE_NUMBER_REGEX = "[1-9]\\d*\\p{Alpha}?";
    private static final String CARD_NUMBER_REGEX = "\\d{16}";
    private static final String DISCOUNT_RATE_REGEX = "[1-9]\\d?";
    private static final String DATE_REGEX = "(\\d{2}|\\d{4})[-\\s]\\d{2}[-\\s]\\d{2}\\s\\d{2}:\\d{2}:?(?<=:)\\d{2}";

    private static DataValidatorImpl instance;

    private DataValidatorImpl() {
    }

    public static DataValidatorImpl getInstance() {
        if (instance == null) {
            instance = new DataValidatorImpl();
        }
        return instance;
    }

    @Override
    public boolean isLoginValid(String login) {
        return login != null && login.strip().matches(LOGIN_REGEX);
    }

    @Override
    public boolean isPasswordValid(String password) {
        return password != null && password.strip().matches(PASSWORD_REGEX);
    }

    @Override
    public boolean isEmailValid(String email) {
        return email != null && email.strip().matches(EMAIL_REGEX);
    }

    @Override
    public boolean isNumberValid(String numberString) {
        return numberString != null && numberString.strip().matches(NUMBER_REGEX);
    }

    @Override
    public boolean isStreetValid(String streetName) {
        return streetName != null && streetName.strip().matches(STREET_NAME_REGEX);
    }

    @Override
    public boolean isHouseNumberValid(String houseNumber) {
        return houseNumber != null && houseNumber.strip().matches(HOUSE_NUMBER_REGEX);
    }

    @Override
    public boolean isNameValid(String userName) {
        return userName != null && userName.strip().matches(USER_NAME_REGEX);
    }

    @Override
    public boolean isCardNumberValid(String cardNumber) {
        return cardNumber != null && cardNumber.strip().matches(CARD_NUMBER_REGEX);
    }

    @Override
    public boolean isDateValid(String dateString) {
        return dateString != null && dateString.strip().matches(DATE_REGEX);
    }

    @Override
    public boolean isDateLaterCurrently(LocalDateTime dateTime) {
        return dateTime != null && dateTime.isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isDiscountRateValid(String discountRate) {
        return discountRate != null && discountRate.strip().matches(DISCOUNT_RATE_REGEX);
    }

    @Override
    public boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber != null && phoneNumber.strip().matches(PHONE_NUMBER_REGEX);
    }
}