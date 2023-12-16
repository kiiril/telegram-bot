package com.github.kiiril.telegrambot.service;

import java.util.List;

public interface SendBotMessageService {
    void sendMessage(Long chatId, String message);
    /**
     * Send messages via telegram bot.
     *
     * @param chatId  provided chatId in which would be sent.
     * @param message collection of provided messages to be sent.
     */
    void sendMessage(Long chatId, List<String> message);
}
