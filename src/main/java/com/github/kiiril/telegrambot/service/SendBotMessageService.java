package com.github.kiiril.telegrambot.service;

public interface SendBotMessageService {
    void sendMessage(String chatId, String message);
}
