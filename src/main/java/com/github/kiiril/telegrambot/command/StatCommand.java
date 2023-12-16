package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.command.annotation.AdminCommand;
import com.github.kiiril.telegrambot.dto.StatisticDTO;
import com.github.kiiril.telegrambot.service.SendBotMessageService;
import com.github.kiiril.telegrambot.service.StatisticsService;
import com.github.kiiril.telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@AdminCommand
public class StatCommand implements Command {
    private final StatisticsService statisticsService;
    private final SendBotMessageService sendBotMessageService;


    public final static String STAT_MESSAGE = "✨<b>Подготовил статистику</b>✨\n" +
            "- Количество активных пользователей: %s\n" +
            "- Количество неактивных пользователей: %s\n" +
            "- Среднее количество групп на одного пользователя: %s\n\n" +
            "<b>Информация по активным группам</b>:\n" +
            "%s";

    public StatCommand(SendBotMessageService sendBotMessageService, StatisticsService statisticsService) {
        this.sendBotMessageService = sendBotMessageService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroups = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(
                STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInActiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collectedGroups
        ));
    }
}
