package by.vchaikovski.coffeeshop.controller.command;

import by.vchaikovski.coffeeshop.controller.command.impl.DefaultCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.LogInCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.LogOutCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.card.*;

import java.util.List;
import static by.vchaikovski.coffeeshop.controller.command.RequestParameter.*;

public enum CommandType {
    DEFAULT(new DefaultCommand(), List.of(ADMINISTRATOR, BARISTA, COURIER, CLIENT, GUEST)),
    LOG_IN(new LogInCommand(), List.of(GUEST)),
    LOG_OUT(new LogOutCommand(), List.of(ADMINISTRATOR, BARISTA, COURIER, CLIENT)),
    TOP_UP_CARD_BALANCE(new TopUpCardBalanceCommand(), List.of(CLIENT)),
    PAYMENT(new PaymentCommand(), List.of(CLIENT)),
    REGISTRATION_NEW_CARD(new RegistrationNewCardCommand(), List.of(CLIENT)),
    DELETE_BANK_CARD(new DeleteBankCardCommand(), List.of(CLIENT)),
    FIND_BANK_CARD(new FindBankCardCommand(), List.of(CLIENT))
    ;
    private BaseCommand command;
    private List<String> usersName;

    CommandType(BaseCommand command, List<String> usersName) {
        this.command = command;
        this.usersName = usersName;
    }

    public BaseCommand getCommand() {
        return command;
    }

    public List<String> getUsersName() {
        return usersName;
    }
}