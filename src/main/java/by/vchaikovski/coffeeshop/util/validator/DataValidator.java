package by.vchaikovski.coffeeshop.util.validator;

import java.time.LocalDate;

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

    boolean isDateTimeValid(String dateTimeString);

    boolean isDateLaterCurrently(LocalDate date);

    boolean isDiscountRateValid(String discountRate);

    boolean isPhoneNumberValid(String phoneNumber);

    <T extends Enum<T>> boolean isEnumContains(String value, Class<T> enumClass);

    boolean isTextValid(String text);

    boolean isPicture(String filePath);
}