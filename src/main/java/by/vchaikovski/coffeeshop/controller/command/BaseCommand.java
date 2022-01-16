package by.vchaikovski.coffeeshop.controller.command;

import by.vchaikovski.coffeeshop.controller.Router;
import by.vchaikovski.coffeeshop.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface BaseCommand {
    Router execute(HttpServletRequest request) throws CommandException;
}