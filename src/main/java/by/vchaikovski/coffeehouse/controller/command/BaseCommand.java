package by.vchaikovski.coffeehouse.controller.command;

import by.vchaikovski.coffeehouse.controller.Router;
import by.vchaikovski.coffeehouse.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

/**
 * @author VChaikovski
 * @project Coffeehouse
 * The interface Base command.
 */
@FunctionalInterface
public interface BaseCommand {
    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     * @throws CommandException the command exception
     */
    Router execute(HttpServletRequest request) throws CommandException;
}