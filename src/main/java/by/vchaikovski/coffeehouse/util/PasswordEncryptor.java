package by.vchaikovski.coffeehouse.util;

import java.math.BigInteger;
import java.util.Base64;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The type Password encryptor.
 */
public class PasswordEncryptor {
    private static final int RADIX = 16;
    private static final int SIGN = 1;
    private static final int SALT = 2;

    private static PasswordEncryptor instance;

    private PasswordEncryptor() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static PasswordEncryptor getInstance() {
        if (instance == null) {
            instance = new PasswordEncryptor();
        }
        return instance;
    }

    /**
     * Encrypt password string.
     *
     * @param password the password
     * @return the string
     */
    public String encryptPassword(String password) {
        Base64.Encoder passwordEncoder = Base64.getEncoder();
        String saltPassword = String.valueOf(password.hashCode() | SALT);
        byte[] passwordBytes = saltPassword.getBytes();
        byte[] encodedBytes = passwordEncoder.encode(passwordBytes);
        BigInteger bigInt = new BigInteger(SIGN, encodedBytes);

        return bigInt.toString(RADIX);
    }
}