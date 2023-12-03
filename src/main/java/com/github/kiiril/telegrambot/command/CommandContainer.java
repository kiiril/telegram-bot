package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.kiiril.telegrambot.service.GroupSubService;
import com.github.kiiril.telegrambot.service.SendBotMessageService;
import com.github.kiiril.telegrambot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import static com.github.kiiril.telegrambot.command.CommandName.*;

public class CommandContainer {
    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService, TelegramUserService telegramUserService) {
        commandMap = new ImmutableMap.Builder<String, Command>()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(sendBotMessageService, javaRushGroupClient, groupSubService))
                .put(LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();
        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
