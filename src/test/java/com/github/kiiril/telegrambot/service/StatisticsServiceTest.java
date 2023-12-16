package com.github.kiiril.telegrambot.service;

import com.github.kiiril.telegrambot.dto.GroupStatDTO;
import com.github.kiiril.telegrambot.dto.StatisticDTO;
import com.github.kiiril.telegrambot.repository.entity.GroupSub;
import com.github.kiiril.telegrambot.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Collections;

@DisplayName("Unit-level testing for StatisticsService")
public class StatisticsServiceTest {
    private GroupSubService groupSubService;
    private TelegramUserService telegramUserService;

    private StatisticsService statisticsService;

    @BeforeEach
    public void init() {
        groupSubService = Mockito.mock(GroupSubService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);

        statisticsService = new StatisticsServiceImpl(groupSubService, telegramUserService);
    }

    @Test
    public void shouldProperlySendStatDTO() {
        // given
        Mockito.when(telegramUserService.findAllInActiveUsers()).thenReturn(Collections.singletonList(new TelegramUser()));
        TelegramUser activeUser = new TelegramUser();
        activeUser.setGroupSubs(Collections.singletonList(new GroupSub()));
        Mockito.when(telegramUserService.findAllActiveUsers()).thenReturn(Collections.singletonList(activeUser));

        GroupSub groupSub = new GroupSub();
        groupSub.setId(1);
        groupSub.setTitle("group1");
        groupSub.setUsers(Collections.singletonList(new TelegramUser()));
        Mockito.when(groupSubService.findAll()).thenReturn(Collections.singletonList(groupSub));

        // when
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        // then
        Assertions.assertNotNull(statisticDTO);
        Assertions.assertEquals(1, statisticDTO.getActiveUserCount());
        Assertions.assertEquals(1, statisticDTO.getInActiveUserCount());
        Assertions.assertEquals(1.0, statisticDTO.getAverageGroupCountByUser());
        Assertions.assertEquals(Collections.singletonList(new GroupStatDTO(groupSub.getId(), groupSub.getTitle(), groupSub.getUsers().size())), statisticDTO.getGroupStatDTOs());

    }
}
