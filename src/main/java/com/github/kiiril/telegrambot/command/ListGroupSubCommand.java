package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.repository.entity.TelegramUser;
import com.github.kiiril.telegrambot.service.GroupSubService;
import com.github.kiiril.telegrambot.service.SendBotMessageService;
import com.github.kiiril.telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.stream.Collectors;

/**
 * {@link Command} for getting list of {@link GroupSub}.
 */
public class ListGroupSubCommand implements Command {
    private SendBotMessageService sendBotMessageService;
    private TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        // TODO add exception handling
        TelegramUser user = telegramUserService.findByChatId(CommandUtils.getChatId(update).toString()).orElseThrow(NotFoundException::new);

        String message = "Я нашел все подписки на группы: \n\n";
        String collectedGroups = user.getGroupSubs().stream()
                .map(it -> "Группа: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                .collect(Collectors.joining());

        sendBotMessageService.sendMessage(user.getChatId(), message + collectedGroups);
    }
}
