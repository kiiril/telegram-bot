package com.github.kiiril.telegrambot.bot;

import com.github.kiiril.telegrambot.command.CommandContainer;
import com.github.kiiril.telegrambot.javarushclient.JavaRushGroupClient;
import com.github.kiiril.telegrambot.service.GroupSubService;
import com.github.kiiril.telegrambot.service.SendBotMessageServiceImpl;
import com.github.kiiril.telegrambot.service.StatisticsService;
import com.github.kiiril.telegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.kiiril.telegrambot.command.CommandName.NO;

@Component
public class JavarushTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    public static String COMMAND_PREFIX = "/";

    private final CommandContainer commandContainer;

    @Autowired
    public JavarushTelegramBot(TelegramUserService telegramUserService,
                               JavaRushGroupClient javaRushGroupClient,
                               GroupSubService groupSubService,
                               StatisticsService statisticsService,
                               @Value("#{'${bot.admins}'.split(',')}") List<String> admins) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), javaRushGroupClient, groupSubService, telegramUserService, statisticsService, admins);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.findCommand(commandIdentifier, update.getMessage().getFrom().getUserName()).execute(update);
            } else {
                commandContainer.findCommand(NO.getCommandName(), update.getMessage().getFrom().getUserName()).execute(update);
            }
        }
    }
    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }
}
