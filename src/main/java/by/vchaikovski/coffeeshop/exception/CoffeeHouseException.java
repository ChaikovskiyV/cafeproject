package by.vchaikovski.coffeeshop.exception;

public class CoffeeHouseException extends Exception {
    public CoffeeHouseException() {
    }

    public CoffeeHouseException(String message) {
        super(message);
    }

    public CoffeeHouseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CoffeeHouseException(Throwable cause) {
        super(cause);
    }
}
