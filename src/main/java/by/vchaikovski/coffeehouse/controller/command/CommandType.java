package by.vchaikovski.coffeehouse.controller.command;

import by.vchaikovski.coffeehouse.controller.command.impl.admin.*;
import by.vchaikovski.coffeehouse.controller.command.impl.barista.ChangeOrderStatusCommand;
import by.vchaikovski.coffeehouse.controller.command.impl.barista.FindOrderCommand;
import by.vchaikovski.coffeehouse.controller.command.impl.client.*;
import by.vchaikovski.coffeehouse.controller.command.impl.common.ChangeLocaleCommand;
import by.vchaikovski.coffeehouse.controller.command.impl.common.FindMenuCommand;
import by.vchaikovski.coffeehouse.controller.command.impl.common.ShowMenuCommand;
import by.vchaikovski.coffeehouse.controller.command.impl.common.ShowMenuInfoCommand;
import by.vchaikovski.coffeehouse.controller.command.impl.go.*;
import by.vchaikovski.coffeehouse.controller.command.impl.guest.SignInCommand;
import by.vchaikovski.coffeehouse.controller.command.impl.guest.SignUpNewUserCommand;
import by.vchaikovski.coffeehouse.model.entity.User;

import java.util.List;

import static by.vchaikovski.coffeehouse.model.entity.User.Role.*;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The enum Command type.
 */

public enum CommandType {
    /**
     * The Go to sign in.
     */
    GO_TO_SIGN_IN(new GoToSignInCommand(), List.of(GUEST)),
    /**
     * The Sign in.
     */
    SIGN_IN(new SignInCommand(), List.of(GUEST)),
    /**
     * The Change locale.
     */
    CHANGE_LOCALE(new ChangeLocaleCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    /**
     * The Go to main.
     */
    GO_TO_MAIN(new GoToMainCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    /**
     * The Go to about.
     */
    GO_TO_ABOUT(new GoToAboutCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    /**
     * The Go to sign up.
     */
    GO_TO_SIGN_UP(new GoToSignUpCommand(), List.of(GUEST, ADMIN)),
    /**
     * The Sign up new user.
     */
    SIGN_UP_NEW_USER(new SignUpNewUserCommand(), List.of(GUEST, ADMIN)),
    /**
     * The Show menu.
     */
    SHOW_MENU(new ShowMenuCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    /**
     * The Show menu info.
     */
    SHOW_MENU_INFO(new ShowMenuInfoCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    /**
     * The Find menu.
     */
    FIND_MENU(new FindMenuCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),
    /**
     * The Go to find menu.
     */
    GO_TO_FIND_MENU(new GoToFindMenuCommand(), List.of(ADMIN, BARISTA, CLIENT, GUEST)),

    /**
     * The Go to home.
     */
    GO_TO_HOME(new GoToHomeCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Sign out.
     */
    SIGN_OUT(new SignOutCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Go to registration card.
     */
    GO_TO_REGISTRATION_CARD(new GoToRegistrationCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Go to card info.
     */
    GO_TO_CARD_INFO(new GoToCardInfoCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Registration new card.
     */
    REGISTRATION_NEW_CARD(new RegistrationNewCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Find bank card.
     */
    FIND_BANK_CARD(new FindBankCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Delete bank card.
     */
    DELETE_BANK_CARD(new DeleteBankCardCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Top up card balance.
     */
    TOP_UP_CARD_BALANCE(new TopUpCardBalanceCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Pay.
     */
    PAY(new PayCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Go to cart.
     */
    GO_TO_CART(new GoToCartCommand(), List.of(CLIENT)),
    /**
     * The Clear cart.
     */
    CLEAR_CART(new ClearCartCommand(), List.of(CLIENT)),
    /**
     * The Add to cart.
     */
    ADD_TO_CART(new AddToCartCommand(), List.of(CLIENT)),
    /**
     * The Delete from cart.
     */
    DELETE_FROM_CART(new DeleteFromCartCommand(), List.of(CLIENT)),
    /**
     * The Increase quantity in cart.
     */
    INCREASE_QUANTITY_IN_CART(new IncreaseQuantityInCartCommand(), List.of(CLIENT)),
    /**
     * The Reduce quantity in cart.
     */
    REDUCE_QUANTITY_IN_CART(new ReduceQuantityInCartCommand(), List.of(CLIENT)),
    /**
     * The Go to user profile.
     */
    GO_TO_USER_PROFILE(new GoToUserProfileCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Update user data.
     */
    UPDATE_USER_DATA(new UpdateUserDataCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Create order.
     */
    CREATE_ORDER(new CreateOrderCommand(), List.of(CLIENT)),
    /**
     * The Go to create order.
     */
    GO_TO_CREATE_ORDER(new GoToCreateOrderCommand(), List.of(CLIENT)),
    /**
     * The Go to orders.
     */
    GO_TO_ORDERS(new GoToOrdersCommand(), List.of(CLIENT)),
    /**
     * The Go to order info.
     */
    GO_TO_ORDER_INFO(new GoToOrderInfoCommand(), List.of(CLIENT, BARISTA, ADMIN)),
    /**
     * The Update order evaluation.
     */
    UPDATE_ORDER_EVALUATION(new UpdateOrderEvaluationCommand(), List.of(CLIENT)),
    /**
     * The Update order comment.
     */
    UPDATE_ORDER_COMMENT(new UpdateOrderCommentCommand(), List.of(CLIENT, ADMIN)),
    /**
     * The Change password.
     */
    CHANGE_PASSWORD(new ChangePasswordCommand(), List.of(ADMIN, BARISTA, CLIENT)),
    /**
     * The Find order.
     */
    FIND_ORDER(new FindOrderCommand(), List.of(BARISTA, ADMIN)),
    /**
     * The Change order status.
     */
    CHANGE_ORDER_STATUS(new ChangeOrderStatusCommand(), List.of(ADMIN, BARISTA)),
    /**
     * The Go to find order.
     */
    GO_TO_FIND_ORDER(new GoToFindOrderCommand(), List.of(ADMIN, BARISTA)),
    /**
     * The Go to find user.
     */
    GO_TO_FIND_USER(new GoToFindUserCommand(), List.of(ADMIN)),
    /**
     * The Find user.
     */
    FIND_USER(new FindUserCommand(), List.of(ADMIN)),
    /**
     * The Update user status.
     */
    UPDATE_USER_STATUS(new UpdateUserStatusCommand(), List.of(ADMIN)),
    /**
     * The Update user role.
     */
    UPDATE_USER_ROLE(new UpdateUserRoleCommand(), List.of(ADMIN)),
    /**
     * The Go to user info.
     */
    GO_TO_USER_INFO(new GoToUserInfoCommand(), List.of(ADMIN)),
    /**
     * The Update user discount.
     */
    UPDATE_USER_DISCOUNT(new UpdateUserDiscountCommand(), List.of(ADMIN)),
    /**
     * The Replenish menu stock.
     */
    REPLENISH_MENU_STOCK(new ReplenishMenuStockCommand(), List.of(ADMIN)),
    /**
     * The Delete menu.
     */
    DELETE_MENU(new DeleteMenuCommand(), List.of(ADMIN)),
    /**
     * The Delete user.
     */
    DELETE_USER(new DeleteUserCommand(), List.of(ADMIN)),
    /**
     * The Go to creation menu.
     */
    GO_TO_CREATION_MENU(new GoToCreationMenuCommand(), List.of(ADMIN)),
    /**
     * The Create menu.
     */
    CREATE_MENU(new CreateMenuCommand(), List.of(ADMIN)),
    /**
     * The Update menu name.
     */
    UPDATE_MENU_NAME(new UpdateMenuNameCommand(), List.of(ADMIN)),
    /**
     * The Update menu type.
     */
    UPDATE_MENU_TYPE(new UpdateMenuTypeCommand(), List.of(ADMIN)),
    /**
     * The Update menu description.
     */
    UPDATE_MENU_DESCRIPTION(new UpdateMenuDescriptionCommand(), List.of(ADMIN)),
    /**
     * The Update menu price.
     */
    UPDATE_MENU_PRICE(new UpdateMenuPriceCommand(), List.of(ADMIN)),
    /**
     * The Update menu image.
     */
    UPDATE_MENU_IMAGE(new UpdateMenuImageCommand(), List.of(ADMIN)),
    /**
     * The Create discount.
     */
    CREATE_DISCOUNT(new CreateDiscountCommand(), List.of(ADMIN));
    private final BaseCommand command;
    private final List<User.Role> usersRole;

    CommandType(BaseCommand command, List<User.Role> usersRole) {
        this.command = command;
        this.usersRole = usersRole;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public BaseCommand getCommand() {
        return command;
    }

    /**
     * Gets users role.
     *
     * @return the users role
     */
    public List<User.Role> getUsersRole() {
        return usersRole;
    }
}