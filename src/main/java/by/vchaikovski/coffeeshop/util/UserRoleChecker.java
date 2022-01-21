package by.vchaikovski.coffeeshop.util;

import by.vchaikovski.coffeeshop.controller.command.CommandType;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;

import java.util.List;

public class UserRoleChecker {
    private static UserRoleChecker instance;

    private UserRoleChecker() {
    }

    public static UserRoleChecker getInstance() {
        if (instance == null) {
            instance = new UserRoleChecker();
        }
        return instance;
    }

    public boolean isAllowed(User.Role userRole, String command) {
        DataValidator validator = DataValidatorImpl.getInstance();
        boolean isCorrectCommand = validator.isEnumContains(command, CommandType.class);
        List<User.Role> allRoles = CommandType.valueOf(command.toUpperCase()).getUsersRole();

        return userRole != null && isCorrectCommand && allRoles.contains(userRole);
    }
}