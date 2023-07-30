package com.dmitrychinyaev.currencybot.service;

import com.dmitrychinyaev.currencybot.entity.TelegramBotCommon;
import com.dmitrychinyaev.currencybot.repository.TelegramBotRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes= {TelegramBotService.class,TelegramBotRepository.class} )
class TelegramBotServiceTest {
    @Autowired
    private TelegramBotService telegramBotServiceTest;
    @MockBean
    private TelegramBotRepository telegramBotRepositoryTest;
    @Test
    void makeCalculation() throws IOException, ParserConfigurationException, SAXException, ParseException {

        Mockito.when(telegramBotRepositoryTest.findValuteByCharCode(ArgumentMatchers.anyString())).
                thenReturn(Optional.of(TelegramBotCommon.TEST_CURRENCY_USD_100));
        Mockito.when(telegramBotRepositoryTest.getStringDateOfUpdate()).
                thenReturn(TelegramBotCommon.TEST_DATE_TO_RETURN);

        assertEquals(telegramBotServiceTest.makeCalculation(TelegramBotCommon.TEST_SMC_ERROR_MESSAGE),
                TelegramBotCommon.TEXT_INCORRECT_REQUEST);
        assertEquals(telegramBotServiceTest.makeCalculation(TelegramBotCommon.TEST_SMC_RUB_TO_USD),
                TelegramBotCommon.TEST_SMC_RESULT_RUB_TO_USD);
        assertEquals(telegramBotServiceTest.makeCalculation(TelegramBotCommon.TEST_SMC_USD),
                TelegramBotCommon.TEST_SMC_RESULT_USD);
        assertEquals(telegramBotServiceTest.makeCalculation(TelegramBotCommon.TEST_SMC_ZERO_NUMBER),
                TelegramBotCommon.TEXT_NOT_POSITIVE);
    }

    @Test
    void currencyConversion() throws IOException, ParseException {
        Mockito.when(telegramBotRepositoryTest.findValuteByCharCode(ArgumentMatchers.anyString())).
                thenReturn(Optional.of(TelegramBotCommon.TEST_CURRENCY_USD_100));

        assertEquals(telegramBotServiceTest.currencyConversion
                (TelegramBotCommon.TEST_NUMBER_TO_CONVERT,TelegramBotCommon.TEST_CHARCODE_TO_CONVERT,false),
                TelegramBotCommon.TEST_CONVERSION_RESULT_TO_CURRENCY);

        assertEquals(telegramBotServiceTest.currencyConversion
                        (TelegramBotCommon.TEST_NUMBER_TO_CONVERT,TelegramBotCommon.TEST_CHARCODE_TO_CONVERT,true),
                TelegramBotCommon.TEST_CONVERSION_RESULT_TO_RUB);
    }
}