package by.vchaikovski.coffeeshop.controller.command;

import by.vchaikovski.coffeeshop.controller.command.impl.common.*;
import by.vchaikovski.coffeeshop.controller.command.impl.DefaultCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.go.*;
import by.vchaikovski.coffeeshop.controller.command.impl.user.admin.*;
import by.vchaikovski.coffeeshop.controller.command.impl.user.barista.ChangeOrderStatusCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.user.client.SignOutCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.card.*;
import by.vchaikovski.coffeeshop.controller.command.impl.user.barista.FindOrderCommand;
import by.vchaikovski.coffeeshop.controller.command.impl.user.client.*;
import by.vchaikovski.coffeeshop.model.entity.User;

import java.util.List;
import static by.vchaikovski.coffeeshop.model.entity.User.Role.*;

public enum CommandType {
    DEFAULT(new DefaultCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    SIGN_IN(new SignInCommand(), List.of(GUEST)),
    CHANGE_LOCALE(new ChangeLocaleCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    GO_TO_MAIN(new GoToMainCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    GO_TO_ABOUT(new GoToAboutCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    GO_TO_SIGN_UP(new GoToSignUpCommand(), List.of(GUEST, ADMIN)),
    SIGN_UP_NEW_USER(new SignUpNewUserCommand(), List.of(GUEST, ADMIN)),
    SHOW_MENU(new ShowMenuCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    SHOW_MENU_INFO(new ShowMenuInfoCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),

    SIGN_OUT(new SignOutCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    GO_TO_REGISTRATION_CARD(new GoToRegistrationCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    GO_TO_CARD_INFO(new GoToCardInfoCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    REGISTRATION_NEW_CARD(new RegistrationNewCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    FIND_BANK_CARD(new FindBankCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    DELETE_BANK_CARD(new DeleteBankCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    TOP_UP_CARD_BALANCE(new TopUpCardBalanceCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    PAY(new PayCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    GO_TO_CART(new GoToCartCommand(), List.of(CLIENT)),
    ADD_TO_CART(new AddToCartCommand(), List.of(CLIENT)),
    DELETE_FROM_CART(new DeleteFromCartCommand(), List.of(CLIENT)),
    INCREASE_QUANTITY_IN_CART(new IncreaseQuantityInCartCommand(), List.of(CLIENT)),
    REDUCE_QUANTITY_IN_CART(new ReduceQuantityInCartCommand(), List.of(CLIENT)),
    GO_TO_USER_PROFILE(new GoToUserProfileCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    UPDATE_USER_DATA(new UpdateUserDataCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    CREATE_ORDER(new CreateOrderCommand(), List.of(CLIENT)),
    GO_TO_ORDERS(new GoToOrdersCommand(), List.of(CLIENT)),
    GO_TO_ORDER_INFO(new GoToOrderInfoCommand(), List.of(CLIENT, BARISTA, ADMIN)),
    SHOW_USER_ORDERS(new ShowUserOrdersCommand(), List.of(CLIENT)),
    UPDATE_ORDER_EVALUATION(new UpdateOrderEvaluationCommand(), List.of(CLIENT)),
    UPDATE_ORDER_COMMENT(new UpdateOrderCommentCommand(), List.of(CLIENT, ADMIN)),
    CHANGE_PASSWORD(new ChangePasswordCommand(), List.of(ADMIN, BARISTA, CLIENT)),


    FIND_ORDER(new FindOrderCommand(), List.of(BARISTA, ADMIN)),
    CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand(), List.of(ADMIN, BARISTA)),

    FIND_USER(new FindUserCommand(), List.of(ADMIN)),
    UPDATE_USER_STATUS(new UpdateUserStatusCommand(), List.of(ADMIN)),
    UPDATE_USER_ROLE(new UpdateUserRoleCommand(), List.of(ADMIN)),
    GO_TO_USER_INFO(new GoToUserInfoCommand(), List.of(ADMIN)),
    UPDATE_USER_DISCOUNT(new UpdateUserDiscountCommand(), List.of(ADMIN)),
    UPDATE_MENU(new UpdateMenuCommand(), List.of(ADMIN)),
    REPLENISH_MENU_STOCK(new ReplenishMenuStockCommand(), List.of(ADMIN)),
    DELETE_MENU(new DeleteMenuCommand(), List.of(ADMIN)),
    DELETE_USER(new DeleteUserCommand(), List.of(ADMIN)),
    DELETE_ORDER(new DeleteOrderCommand(), List.of(ADMIN)),
    DELETE_DISCOUNT(new DeleteDiscountCommand(), List.of(ADMIN)),
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