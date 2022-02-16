package by.vchaikovski.coffeehouse.util.validator;

import by.vchaikovski.coffeehouse.model.entity.User;
import by.vchaikovski.coffeehouse.util.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Data validator impl test.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataValidatorImplTest {
    private static final Logger logger = LogManager.getLogger();
    private static DataValidatorImpl validator;
    private static LocalDate date;
    private static LocalDate pastDate;
    private static String login;
    private static String password;
    private static String cardNumber;
    private static String dateString;
    private static String discountRate;
    private static String email;
    private static String numberString;
    private static String nameEn;
    private static String nameRus;
    private static String houseNumber;
    private static String phoneNumber;
    private static String streetNameEn;
    private static String streetNameRus;
    private static String wrongString;
    private static String enumString;
    private static Class<User.Role> enumRole;
    private static boolean result;

    /**
     * Sets up.
     */
    @BeforeAll
    void setUp() {
        logger.info("Testing is starting...");
        validator = DataValidatorImpl.getInstance();
        long dayNumber = 1;
        date = LocalDate.now().plusDays(dayNumber);
        pastDate = LocalDate.now().minusDays(dayNumber);
        login = "Developer123";
        password = "D12jk96GH15";
        cardNumber = "1234567890123456";
        dateString = "2022-01-07";
        discountRate = "99";
        email = "hello_everyone@hi.com";
        numberString = "12398";
        nameEn = "Anna-Maria";
        nameRus = "Андрей";
        houseNumber = "41A";
        phoneNumber = "+375339681599";
        streetNameEn = "Gikalo street";
        streetNameRus = "Наполеона Орды";
        enumString = "barista";
        enumRole = User.Role.class;
    }

    /**
     * Tear down.
     */
    @AfterAll
    void tearDown() {
        logger.info("Testing has finished.");
    }

    /**
     * Is login valid.
     */
    @Test
    void isLoginValid() {
        result = validator.isLoginValid(login);
        assertTrue(result);
    }

    /**
     * If login not valid.
     */
    @Test
    void ifLoginNotValid() {
        wrongString = "123gh";
        result = validator.isLoginValid(wrongString);
        assertFalse(result);
    }

    /**
     * If login too short.
     */
    @Test
    void ifLoginTooShort() {
        wrongString = "Lo0";
        result = validator.isLoginValid(wrongString);
        assertFalse(result);
    }

    /**
     * If login too long.
     */
    @Test
    void ifLoginTooLong() {
        wrongString = "ThereIsTooLongLogin11";
        result = validator.isLoginValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is password valid.
     */
    @Test
    void isPasswordValid() {
        result = validator.isPasswordValid(password);
        assertTrue(result);
    }

    /**
     * If password not valid.
     */
    @Test
    void ifPasswordNotValid() {
        wrongString = "*/_*_/*";
        result = validator.isPasswordValid(wrongString);
        assertFalse(result);
    }

    /**
     * If password too short.
     */
    @Test
    void ifPasswordTooShort() {
        wrongString = "123";
        result = validator.isPasswordValid(wrongString);
        assertFalse(result);
    }

    /**
     * If password too long.
     */
    @Test
    void ifPasswordTooLong() {
        wrongString = "ThereIsTooLongPassword123";
        result = validator.isPasswordValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is email valid.
     */
    @Test
    void isEmailValid() {
        result = validator.isEmailValid(email);
        assertTrue(result);
    }

    /**
     * If email not valid.
     */
    @Test
    void ifEmailNotValid() {
        wrongString = "hjh123.by";
        result = validator.isEmailValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is number valid.
     */
    @Test
    void isNumberValid() {
        result = validator.isNumberValid(numberString);
        assertTrue(result);
    }

    /**
     * If number not valid.
     */
    @Test
    void ifNumberNotValid() {
        wrongString = "123f";
        result = validator.isNumberValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is street valid en.
     */
    @Test
    void isStreetValidEn() {
        result = validator.isNameValid(streetNameEn);
        assertTrue(result);
    }

    /**
     * Is street valid rus.
     */
    @Test
    void isStreetValidRus() {
        result = validator.isNameValid(streetNameRus);
        assertTrue(result);
    }

    /**
     * If street not valid.
     */
    @Test
    void ifStreetNotValid() {
        wrongString = "123456";
        result = validator.isNameValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is house number valid.
     */
    @Test
    void isHouseNumberValid() {
        result = validator.isHouseNumberValid(houseNumber);
        assertTrue(result);
    }

    /**
     * If house number not valid.
     */
    @Test
    void ifHouseNumberNotValid() {
        wrongString = "01A";
        result = validator.isHouseNumberValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is name valid rus.
     */
    @Test
    void isNameValidRus() {
        result = validator.isNameValid(nameRus);
        assertTrue(result);
    }

    /**
     * Is name valid en.
     */
    @Test
    void isNameValidEn() {
        result = validator.isNameValid(nameEn);
        assertTrue(result);
    }

    /**
     * If name not valid.
     */
    @Test
    void ifNameNotValid() {
        wrongString = "Agent007";
        result = validator.isNameValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is card number valid.
     */
    @Test
    void isCardNumberValid() {
        result = validator.isCardNumberValid(cardNumber);
        assertTrue(result);
    }

    /**
     * If card number not valid.
     */
    @Test
    void ifCardNumberNotValid() {
        wrongString = "123456789Ol234S6";
        result = validator.isCardNumberValid(wrongString);
        assertFalse(result);
    }

    /**
     * If card number too short.
     */
    @Test
    void ifCardNumberTooShort() {
        wrongString = "12345678901234";
        result = validator.isCardNumberValid(wrongString);
        assertFalse(result);
    }

    /**
     * If card number too long.
     */
    @Test
    void ifCardNumberTooLong() {
        wrongString = "12345678901234567";
        result = validator.isCardNumberValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is date valid.
     */
    @Test
    void isDateValid() {
        result = validator.isDateValid(dateString);
        assertTrue(result);
    }

    /**
     * If date not valid.
     */
    @Test
    void ifDateNotValid() {
        wrongString = "now";
        result = validator.isDateValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is date later currently.
     */
    @Test
    void isDateLaterCurrently() {
        result = validator.isDateLaterCurrently(date);
        assertTrue(result);
    }

    /**
     * If date not later currently.
     */
    @Test
    void ifDateNotLaterCurrently() {
        result = validator.isDateLaterCurrently(pastDate);
        assertFalse(result);
    }

    /**
     * Is discount rate valid.
     */
    @Test
    void isDiscountRateValid() {
        result = validator.isDiscountRateValid(discountRate);
        assertTrue(result);
    }

    /**
     * If discount rate not valid.
     */
    @Test
    void ifDiscountRateNotValid() {
        wrongString = "101";
        result = validator.isDiscountRateValid(wrongString);
        assertFalse(result);
    }

    /**
     * Is phone number valid.
     */
    @Test
    void isPhoneNumberValid() {
        result = validator.isPhoneNumberValid(phoneNumber);
        assertTrue(result);
    }

    /**
     * If phone number not valid.
     */
    @Test
    void ifPhoneNumberNotValid() {
        wrongString = "+37533698lЗ9O";
        result = validator.isPhoneNumberValid(wrongString);
        assertFalse(result);
    }

    /**
     * If phone number null.
     */
    @Test
    void ifPhoneNumberNull() {
        result = validator.isPhoneNumberValid(null);
        assertFalse(result);
    }

    /**
     * Is enum contains.
     */
    @Test
    void isEnumContains() {
        result = validator.isEnumContains(enumString, enumRole);
        assertTrue(result);
    }

    /**
     * If enum not contain value.
     */
    @Test
    void ifEnumNotContainValue() {
        wrongString = "driver";
        result = validator.isEnumContains(wrongString, enumRole);
        assertFalse(result);
    }
}