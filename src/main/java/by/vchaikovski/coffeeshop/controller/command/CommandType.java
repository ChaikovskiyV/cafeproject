package by.vchaikovski.coffeeshop.controller.command;

public enum CommandType {
    DEFAULT(""),
    LOG_IN(""),
    LOG_OUT(""),
    TOP_UP_CARD_BALANCE(""),
    WITHDRAW_MONEY_FROM_CARD("")

    ;
    private String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}