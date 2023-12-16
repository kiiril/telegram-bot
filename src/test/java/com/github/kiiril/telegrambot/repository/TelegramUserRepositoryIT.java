package com.github.kiiril.telegrambot.repository;

import com.github.kiiril.telegrambot.repository.entity.GroupSub;
import com.github.kiiril.telegrambot.repository.entity.TelegramUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@ActiveProfiles("test") // take application-test.properties for database configuration
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class TelegramUserRepositoryIT {
    @Autowired
    private TelegramUserRepository telegramUserRepository;
    @Sql(scripts = {"classpath:/sql/clearDbs.sql", "classpath:/sql/telegram_users.sql"})
    @Test
    public void shouldProperlyFindAllActiveUsers() {
        // when
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        // then
        Assertions.assertEquals(5, users.size());
    }

    @Sql(scripts = {"classpath:/sql/clearDbs.sql"})
    @Test
    public void shouldProperlySaveTelegramUser() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(1234567890L);
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);

        // when
        Optional<TelegramUser> savedUser = telegramUserRepository.findById(telegramUser.getChatId());

        // then
        Assertions.assertTrue(savedUser.isPresent());
        Assertions.assertEquals(telegramUser, savedUser.get());

    }

    @Sql(scripts = {"classpath:/sql/fiveGroupSubsForUser.sql"})
    @Test
    public void shouldProperlyGetAllGroupSubsForUser() {
        // when
        Optional<TelegramUser> userFromDB = telegramUserRepository.findById(1L);

        // then
        Assertions.assertTrue(userFromDB.isPresent());
        List<GroupSub> groupSubs = userFromDB.get().getGroupSubs();
        for (int i = 0; i < groupSubs.size(); i++) {
            Assertions.assertEquals(String.format("g%s", (i+1)), groupSubs.get(i).getTitle());
            Assertions.assertEquals((i + 1), groupSubs.get(i).getId());
            Assertions.assertEquals((i + 1), groupSubs.get(i).getLastPostId());
        }
    }
}
