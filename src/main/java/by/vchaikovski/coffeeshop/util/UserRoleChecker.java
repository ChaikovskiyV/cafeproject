package by.vchaikovski.coffeeshop.util;

import by.vchaikovski.coffeeshop.controller.command.CommandType;
import by.vchaikovski.coffeeshop.model.entity.User;
import by.vchaikovski.coffeeshop.util.validator.DataValidator;
import by.vchaikovski.coffeeshop.util.validator.impl.DataValidatorImpl;

import java.util.ArrayList;
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
        List<User.Role> allRoles = new ArrayList<>();
        DataValidator validator = DataValidatorImpl.getInstance();
        if (validator.isEnumContains(command, CommandType.class)) {
            allRoles = CommandType.valueOf(command.toUpperCase()).getUsersRole();
        }
        return allRoles.contains(userRole);
    }
}