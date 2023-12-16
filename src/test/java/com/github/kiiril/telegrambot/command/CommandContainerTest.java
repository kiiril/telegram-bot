package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.kiiril.telegrambot.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

@DisplayName("Unit-level testing for CommandContainer")
public class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        JavaRushGroupClient javaRushGroupClient = Mockito.mock(JavaRushGroupClient.class);
        GroupSubService groupSubService = Mockito.mock(GroupSubService.class);
        StatisticsService statisticsService = Mockito.mock(StatisticsService.class);
        commandContainer = new CommandContainer(
                sendBotMessageService,
                javaRushGroupClient,
                groupSubService,
                telegramUserService,
                statisticsService,
                Collections.singletonList("username"));
    }

    @Test
    public void shouldGetAllExistingCommands() {
        // when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.findCommand(commandName.getCommandName(), "username");
                    Assertions.assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }


    @Test
    public void shouldReturnUnknownCommand() {
        // given
        String unknownCommand = "/fkwekfwevwe";

        // when
        Command command = commandContainer.findCommand(unknownCommand, "username");

        // then
        Assertions.assertEquals(UnknownCommand.class, command.getClass());
    }

}
