package com.github.kiiril.telegrambot.service;

import com.github.kiiril.telegrambot.repository.entity.TelegramUser;

import java.util.List;
import java.util.Optional;

public interface TelegramUserService {
    void save(TelegramUser telegramUser);
    List<TelegramUser> retrieveAllActiveUsers();
    Optional<TelegramUser> findByChatId(String chatId);
}
