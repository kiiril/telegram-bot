package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.repository.entity.TelegramUser;
import com.github.kiiril.telegrambot.service.GroupSubService;
import com.github.kiiril.telegrambot.service.SendBotMessageService;
import com.github.kiiril.telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.stream.Collectors;

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
        TelegramUser user = telegramUserService.findByChatId(CommandUtils.getChatId(update)).orElseThrow(NotFoundException::new);

        String message;

        if (user.getGroupSubs().isEmpty()) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напиши /addGroupSub";
        } else {
            String collectedGroups = user.getGroupSubs().stream()
                    .map(it -> "Группа: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                    .collect(Collectors.joining());
            message = String.format("Я нашел все подписки на группы: \n\n %s", collectedGroups);
        }

        sendBotMessageService.sendMessage(user.getChatId(), message);
    }
}
