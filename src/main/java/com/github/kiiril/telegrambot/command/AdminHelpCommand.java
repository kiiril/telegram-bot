package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.kiiril.telegrambot.command.CommandName.STAT;

public class AdminHelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String ADMIN_HELP_MESSAGE = String.format("✨<b>Доступные команды админа</b>✨\n\n"
                    + "<b>Получить статистику</b>\n"
                    + "%s - статистика бота\n",
                    STAT.getCommandName());

    public AdminHelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(CommandUtils.getChatId(update), ADMIN_HELP_MESSAGE);
    }
}
