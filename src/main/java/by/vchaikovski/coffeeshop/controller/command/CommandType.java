package by.vchaikovski.coffeeshop.controller.command;

import by.vchaikovski.coffeeshop.controller.command.impl.ChangeLocaleCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.DefaultCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.user.guest.SignInCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.user.SignOutCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.card.*;
import by.vchaikovski.coffeeshop.controller.command.impl.go.GoToCardInfoCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.go.GoToRegistrationCardCommand;
import by.vchaikovski.coffeeshop.model.entity.User;

import java.util.List;
import static by.vchaikovski.coffeeshop.model.entity.User.Role.*;

public enum CommandType {
    DEFAULT(new DefaultCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    LOG_IN(new SignInCommand(), List.of(GUEST)),
    LOG_OUT(new SignOutCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    TOP_UP_CARD_BALANCE(new TopUpCardBalanceCommand(), List.of(CLIENT)),
    PAYMENT(new PaymentCommand(), List.of(CLIENT)),
    REGISTRATION_NEW_CARD(new RegistrationNewCardCommand(), List.of(CLIENT)),
    DELETE_BANK_CARD(new DeleteBankCardCommand(), List.of(CLIENT)),
    FIND_BANK_CARD(new FindBankCardCommand(), List.of(CLIENT)),
    CHANGE_LOCALE(new ChangeLocaleCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    GO_TO_REGISTRATION_CARD(new GoToRegistrationCardCommand(), List.of(CLIENT)),
    GO_TO_CARD_INFO(new GoToCardInfoCommand(), List.of(CLIENT))
    ;
    private BaseCommand command;
    private List<User.Role> usersRole;

    CommandType(BaseCommand command, List<User.Role> usersRole) {
        this.command = command;
        this.usersRole = usersRole;
    }

    public BaseCommand getCommand() {
        return command;
    }

    public List<User.Role> getUsersRole() {
        return usersRole;
    }
}