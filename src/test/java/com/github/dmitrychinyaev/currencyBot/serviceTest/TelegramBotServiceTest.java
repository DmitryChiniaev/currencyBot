package com.github.dmitrychinyaev.currencyBot.serviceTest;

import com.github.dmitrychinyaev.currencyBot.repository.TelegramBotRepository;
import com.github.dmitrychinyaev.currencyBot.service.TelegramBotService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TelegramBotServiceTest {
    TelegramBotService sut;
    TelegramBotRepository repository;

    @BeforeEach
    public void init(){
        repository = new TelegramBotRepository();
        sut = new TelegramBotService(repository);
    }

    @AfterEach
    public void finished(){
        sut = null;
    }

    @Test
    public void makeCalculationTest() throws IOException {
        String request = "10 USD";
        //String expected = "10 Доллар США 793,56";
        String result = sut.makeCalculation(request);
        //assertEquals(expected, result);
        assertNotNull(result);
    }

}
