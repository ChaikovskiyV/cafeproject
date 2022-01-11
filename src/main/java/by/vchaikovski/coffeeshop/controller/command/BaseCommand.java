package by.vchaikovski.coffeeshop.controller.command;

import by.vchaikovski.coffeeshop.controller.Router;
import jakarta.servlet.http.HttpServletRequest;

@FunctionalInterface
public interface BaseCommand {
    Router execute(HttpServletRequest request);
}