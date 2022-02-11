package by.vchaikovski.coffeehouse.util.validator;

import java.time.LocalDate;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Data validator.
 */
public interface DataValidator {
    /**
     * Is login valid boolean.
     *
     * @param login the login
     * @return the boolean
     */
    boolean isLoginValid(String login);

    /**
     * Is password valid boolean.
     *
     * @param password the password
     * @return the boolean
     */
    boolean isPasswordValid(String password);

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    boolean isEmailValid(String email);

    /**
     * Is number valid boolean.
     *
     * @param number the number
     * @return the boolean
     */
    boolean isNumberValid(String number);

    /**
     * Is street valid boolean.
     *
     * @param streetName the street name
     * @return the boolean
     */
    boolean isStreetValid(String streetName);

    /**
     * Is house number valid boolean.
     *
     * @param houseNumber the house number
     * @return the boolean
     */
    boolean isHouseNumberValid(String houseNumber);

    /**
     * Is name valid boolean.
     *
     * @param userName the user name
     * @return the boolean
     */
    boolean isNameValid(String userName);

    /**
     * Is card number valid boolean.
     *
     * @param cardNumber the card number
     * @return the boolean
     */
    boolean isCardNumberValid(String cardNumber);

    /**
     * Is date valid boolean.
     *
     * @param dateString the date string
     * @return the boolean
     */
    boolean isDateValid(String dateString);

    /**
     * Is date later currently boolean.
     *
     * @param date the date
     * @return the boolean
     */
    boolean isDateLaterCurrently(LocalDate date);

    /**
     * Is discount rate valid boolean.
     *
     * @param discountRate the discount rate
     * @return the boolean
     */
    boolean isDiscountRateValid(String discountRate);

    /**
     * Is phone number valid boolean.
     *
     * @param phoneNumber the phone number
     * @return the boolean
     */
    boolean isPhoneNumberValid(String phoneNumber);

    /**
     * Is enum contains boolean.
     *
     * @param <T>       the type parameter
     * @param value     the value
     * @param enumClass the enum class
     * @return the boolean
     */
    <T extends Enum<T>> boolean isEnumContains(String value, Class<T> enumClass);

    /**
     * Is text valid boolean.
     *
     * @param text the text
     * @return the boolean
     */
    boolean isTextValid(String text);

    /**
     * Is picture boolean.
     *
     * @param filePath the file path
     * @return the boolean
     */
    boolean isPicture(String filePath);
}