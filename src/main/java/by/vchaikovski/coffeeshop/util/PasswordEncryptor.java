package by.vchaikovski.coffeeshop.util;

import java.math.BigInteger;
import java.util.Base64;

public class PasswordEncryptor {
    private static final int RADIX = 16;
    private static final int SIGN = 1;
    private static final int SALT = 2;

    private static PasswordEncryptor instance;

    private PasswordEncryptor() {
    }

    public static PasswordEncryptor getInstance() {
        if (instance == null) {
            instance = new PasswordEncryptor();
        }
        return instance;
    }

    public String encryptPassword(String password) {
        Base64.Encoder passwordEncoder = Base64.getEncoder();
        String saltPassword = String.valueOf(password.hashCode() | SALT);
        byte[] passwordBytes = saltPassword.getBytes();
        byte[] encodedBytes = passwordEncoder.encode(passwordBytes);
        BigInteger bigInt = new BigInteger(SIGN, encodedBytes);

        return bigInt.toString(RADIX);
    }
}