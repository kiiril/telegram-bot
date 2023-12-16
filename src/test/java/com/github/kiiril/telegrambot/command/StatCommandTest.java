package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.dto.GroupStatDTO;
import com.github.kiiril.telegrambot.dto.StatisticDTO;
import com.github.kiiril.telegrambot.service.SendBotMessageService;
import com.github.kiiril.telegrambot.service.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;

import static com.github.kiiril.telegrambot.command.StatCommand.STAT_MESSAGE;

@DisplayName("Unit-level testing for StatCommand")
public class StatCommandTest {
    private StatisticsService statisticsService;
    private SendBotMessageService sendBotMessageService;

    private Command statCommand;

    @BeforeEach
    public void init() {
        statisticsService = Mockito.mock(StatisticsService.class);
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);

        statCommand = new StatCommand(sendBotMessageService, statisticsService);
    }

    @Test
    public void shouldProperlySendMessage() {
        // given
        Long chatId = 123456L;
        GroupStatDTO groupStatDTO = new GroupStatDTO(1, "group1", 1);
        StatisticDTO statisticDTO = new StatisticDTO(1, 1, Collections.singletonList(groupStatDTO), 2.5);

        Mockito.when(statisticsService.countBotStatistic()).thenReturn(statisticDTO);

        // when
        statCommand.execute(AbstractCommandTest.prepareUpdate(chatId, CommandName.STAT.getCommandName()));

        // then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, String.format(
                STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInActiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                String.format("%s (id = %s) - %s подписчиков", groupStatDTO.getTitle(), groupStatDTO.getId(), groupStatDTO.getActiveUserCount())
        ));
    }
}
