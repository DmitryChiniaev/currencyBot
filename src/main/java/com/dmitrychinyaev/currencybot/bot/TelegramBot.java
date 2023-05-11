package com.dmitrychinyaev.currencybot.bot;

import com.dmitrychinyaev.currencybot.config.TelegramBotConfiguration;
import com.dmitrychinyaev.currencybot.entity.TelegramBotCommon;
import com.dmitrychinyaev.currencybot.service.TelegramBotService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
@RequiredArgsConstructor
@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final TelegramBotService telegramBotService;

    private final TelegramBotConfiguration telegramBotConfiguration;

    @Override
    public String getBotUsername() {
        return telegramBotConfiguration.getBotname();
    }

    @Override
    public String getBotToken() {
        return telegramBotConfiguration.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getChat().getUserName();
            switch (messageText) {
                case TelegramBotCommon.COMMAND_START -> {
                    sendMessage(chatId, String.format(TelegramBotCommon.TEXT_GREETING, username));
                    log.info("Пользователь {} отправил команду start", username);
                }
                case TelegramBotCommon.COMMAND_HELP -> {
                    sendMessage(chatId, TelegramBotCommon.TEXT_HELP);
                    log.info("Пользователь {} запросил список доступных валют", username);
                }
                case TelegramBotCommon.COMMAND_SHOW_ALL_CURRENCY -> sendMessage(chatId, telegramBotService.getAvailableCurrency());
                default -> {
                    sendMessage(chatId, telegramBotService.makeCalculation(messageText
                                .replaceAll(",", ".")
                                .toUpperCase())
                                .trim());
                    log.info("Пользователь {} отправил запрос перевод валют {}", username, messageText);
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