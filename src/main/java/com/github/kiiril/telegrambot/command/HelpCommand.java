package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.kiiril.telegrambot.command.CommandName.*;

public class HelpCommand implements Command {
    private final SendBotMessageService sendBotMessageService;

    public final static String HELP_MESSAGE = String.format("✨<b>Доcтупные команды</b>✨\n\n"
                                                + "<b>Начать\\закончить работу с ботом</b>\n"
                                                + "%s - начать работу со мной\n"
                                                + "%s - приостановить работу со мной\n\n"

                                                + "Работа с подписками на группы:\n"
                                                + "%s - подписаться на группу статей\n"
                                                + "%s - отписаться от группы статей\n"
                                                + "%s - получить список групп, на которые подписан\n\n"

                                                + "%s - получить помощь в работе со мной\n"
                                                + "%s - узнать сколько человек пользуется мной\n",
            START.getCommandName(), STOP.getCommandName(), ADD_GROUP_SUB.getCommandName(), DELETE_GROUP_SUB.getCommandName(), LIST_GROUP_SUB.getCommandName(), HELP.getCommandName(), STAT.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(CommandUtils.getChatId(update), HELP_MESSAGE);
    }
}
