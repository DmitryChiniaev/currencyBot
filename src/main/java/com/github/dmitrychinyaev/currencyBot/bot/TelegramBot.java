package com.github.dmitrychinyaev.currencyBot.bot;


import com.github.dmitrychinyaev.currencyBot.config.TelegramBotConfiguration;
import com.github.dmitrychinyaev.currencyBot.service.TelegramBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotService service;

    private final TelegramBotConfiguration configuration;
    @Override
    public String getBotUsername() {
        return configuration.getBotname();
    }

    @Override
    public String getBotToken() {
        return configuration.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            switch (messageText) {
                case "/start" ->
                        sendMessage(chatId, String.format("Приветствую, %s! Введите число и валюту из списка.", update.getMessage().getChat().getFirstName()));
                case "/help" -> sendMessage(chatId,
                        """
                                Чтобы перевести иностранную валюту в рубли, напишите сообщение в формате ''100 USD'', где на первом месте укажите желаемое число и валюту.
                                Чтобы перевести рубли в валюту, напишите сообщение в формате ''100 RUB USD''
                                Список доступных валют можно посмотреть по команде /currency"""
                );
                case "/currency" -> {
                    try {
                        sendMessage(chatId, service.getAvailableCurrency());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> {
                    try {
                        sendMessage(chatId, service.makeCalculation(messageText.replaceAll(",",".")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    private void sendMessage(long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}