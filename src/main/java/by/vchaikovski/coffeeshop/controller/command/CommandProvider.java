package by.vchaikovski.coffeeshop.controller.command;

import by.vchaikovski.coffeeshop.controller.command.impl.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.EnumMap;

import static by.vchaikovski.coffeeshop.controller.command.CommandType.*;

public class CommandProvider {
    private static final Logger logger = LogManager.getLogger();
    private static CommandProvider instance;
    private final EnumMap<CommandType, BaseCommand> commands = new EnumMap<>(CommandType.class);

    private CommandProvider() {
        commands.put(DEFAULT, new DefaultCommand());
        commands.put(LOG_IN, new LogInCommand());
        commands.put(LOG_OUT, new LogOutCommand());
        commands.put(TOP_UP_CARD_BALANCE, new TopUpCardBalanceCommand());
        commands.put(WITHDRAW_MONEY_FROM_CARD, new WithdrawMoneyFromCardCommand());
    }

    public static CommandProvider getInstance() {
        if (instance == null) {
            instance = new CommandProvider();
        }
        return instance;
    }

    public BaseCommand getCommand(String command) {
        CommandType commandType;
        try {
            commandType = command != null ? CommandType.valueOf(command.toUpperCase()) : DEFAULT;
        } catch (EnumConstantNotPresentException e) {
            logger.warn(() -> "Unknown command: " + command, e);
            commandType = DEFAULT;
        }
        return commands.get(commandType);
    }
}