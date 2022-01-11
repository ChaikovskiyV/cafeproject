package by.vchaikovski.coffeeshop.util.validator;

import java.time.LocalDateTime;

public interface DataValidator {
    boolean isLoginValid(String login);

    boolean isPasswordValid(String password);

    boolean isEmailValid(String email);

    boolean isNumberValid(String number);

    boolean isStreetValid(String streetName);

    boolean isHouseNumberValid(String houseNumber);

    boolean isNameValid(String userName);

    boolean isCardNumberValid(String cardNumber);

    boolean isDateValid(String dateString);

    boolean isDateLaterCurrently(LocalDateTime dateTime);

    boolean isDiscountRateValid(String discountRate);

    boolean isPhoneNumberValid(String phoneNumber);

    <T extends Enum<T>> boolean isEnumContains(String value, Class<T> enumClass);
}