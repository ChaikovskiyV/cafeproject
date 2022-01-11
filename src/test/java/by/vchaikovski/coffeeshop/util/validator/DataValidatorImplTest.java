package by.vchaikovski.coffeeshop.util.validator;

import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataValidatorImplTest {
    private static final Logger logger = LogManager.getLogger();
    private static DataValidatorImpl validator;
    private static LocalDateTime dateTime;
    private static LocalDateTime pastDateTime;
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

    @BeforeAll
    void setUp() {
        logger.info("Testing is starting...");
        validator = DataValidatorImpl.getInstance();
        long dayNumber = 1;
        dateTime = LocalDateTime.now().plusDays(dayNumber);
        pastDateTime = LocalDateTime.now().minusDays(dayNumber);
        login = "Developer123";
        password = "D12jk96GH15";
        cardNumber = "1234567890123456";
        dateString = "2022-01-07 00:00:00";
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

    @AfterAll
    void tearDown() {
        logger.info("Testing has finished.");
    }

    @Test
    public void isLoginValid() {
        result = validator.isLoginValid(login);
        assertTrue(result);
    }

    @Test
    public void ifLoginNotValid() {
        wrongString = "123gh";
        result = validator.isLoginValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void ifLoginTooShort() {
        wrongString = "Lo0";
        result = validator.isLoginValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void ifLoginTooLong() {
        wrongString = "ThereIsTooLongLogin11";
        result = validator.isLoginValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isPasswordValid() {
        result = validator.isPasswordValid(password);
        assertTrue(result);
    }

    @Test
    public void ifPasswordNotValid() {
        wrongString = "*/_*_/*";
        result = validator.isPasswordValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void ifPasswordTooShort() {
        wrongString = "123";
        result = validator.isPasswordValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void ifPasswordTooLong() {
        wrongString = "ThereIsTooLongPassword123";
        result = validator.isPasswordValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isEmailValid() {
        result = validator.isEmailValid(email);
        assertTrue(result);
    }

    @Test
    public void ifEmailNotValid() {
        wrongString = "hjh123.by";
        result = validator.isEmailValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isNumberValid() {
        result = validator.isNumberValid(numberString);
        assertTrue(result);
    }

    @Test
    public void ifNumberNotValid() {
        wrongString = "123f";
        result = validator.isNumberValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isStreetValidEn() {
        result = validator.isNameValid(streetNameEn);
        assertTrue(result);
    }

    @Test
    public void isStreetValidRus() {
        result = validator.isNameValid(streetNameRus);
        assertTrue(result);
    }

    @Test
    public void ifStreetNotValid() {
        wrongString = "123456";
        result = validator.isNameValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isHouseNumberValid() {
        result = validator.isHouseNumberValid(houseNumber);
        assertTrue(result);
    }

    @Test
    public void ifHouseNumberNotValid() {
        wrongString = "01A";
        result = validator.isHouseNumberValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isNameValidRus() {
        result = validator.isNameValid(nameRus);
        assertTrue(result);
    }

    @Test
    public void isNameValidEn() {
        result = validator.isNameValid(nameEn);
        assertTrue(result);
    }

    @Test
    public void ifNameNotValid() {
        wrongString = "Agent007";
        result = validator.isNameValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isCardNumberValid() {
        result = validator.isCardNumberValid(cardNumber);
        assertTrue(result);
    }

    @Test
    public void ifCardNumberNotValid() {
        wrongString = "123456789Ol234S6";
        result = validator.isCardNumberValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void ifCardNumberTooShort() {
        wrongString = "12345678901234";
        result = validator.isCardNumberValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void ifCardNumberTooLong() {
        wrongString = "12345678901234567";
        result = validator.isCardNumberValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isDateValid() {
        result = validator.isDateValid(dateString);
        assertTrue(result);
    }

    @Test
    public void ifDateNotValid() {
        wrongString = "now";
        result = validator.isDateValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isDateLaterCurrently() {
        result = validator.isDateLaterCurrently(dateTime);
        assertTrue(result);
    }

    @Test
    public void ifDateNotLaterCurrently() {
        result = validator.isDateLaterCurrently(pastDateTime);
        assertFalse(result);
    }

    @Test
    public void isDiscountRateValid() {
        result = validator.isDiscountRateValid(discountRate);
        assertTrue(result);
    }

    @Test
    public void ifDiscountRateNotValid() {
        wrongString = "101";
        result = validator.isDiscountRateValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isPhoneNumberValid() {
        result = validator.isPhoneNumberValid(phoneNumber);
        assertTrue(result);
    }

    @Test
    public void ifPhoneNumberNotValid() {
        wrongString = "+37533698lЗ9O";
        result = validator.isPhoneNumberValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void ifPhoneNumberNull() {
        wrongString = null;
        result = validator.isPhoneNumberValid(wrongString);
        assertFalse(result);
    }

    @Test
    public void isEnumContains() {
        result = validator.isEnumContains(enumString, enumRole);
        assertTrue(result);
    }

    @Test
    public void ifEnumNotContainValue() {
        wrongString = "driver";
        result = validator.isEnumContains(wrongString, enumRole);
        assertFalse(result);
    }
}