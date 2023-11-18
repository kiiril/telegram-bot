package com.github.kiiril.telegrambot.command;

import com.github.kiiril.telegrambot.bot.JavarushTelegramBot;
import com.github.kiiril.telegrambot.service.SendBotMessageService;
import com.github.kiiril.telegrambot.service.SendBotMessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

abstract class AbstractCommandTest {
    protected JavarushTelegramBot telegramBot = Mockito.mock(JavarushTelegramBot.class);
    protected SendBotMessageService sendBotMessageService = new SendBotMessageServiceImpl(telegramBot);

    abstract String getCommandName();
    abstract String getCommandMessage();
    abstract Command getCommand();

    @Test
    public void shouldProperlyExecuteCommand() throws TelegramApiException {
        // given
        Long chatId = 123456783456L;

        // imitating sending a message throw telegram
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(chatId);
        Mockito.when(message.getText()).thenReturn(getCommandName());
        update.setMessage(message);

        // message that we are return to the user
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId.toString());
        sendMessage.setText(getCommandMessage());
        sendMessage.enableHtml(true);

        // when
        getCommand().execute(update);

        // then
        Mockito.verify(telegramBot).execute(sendMessage);


    }

}
