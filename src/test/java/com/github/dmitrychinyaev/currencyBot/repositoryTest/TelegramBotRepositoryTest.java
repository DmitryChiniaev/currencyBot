package com.github.dmitrychinyaev.currencyBot.repositoryTest;


import com.github.dmitrychinyaev.currencyBot.repository.TelegramBotRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TelegramBotRepositoryTest {
    TelegramBotRepository sut;

    @BeforeEach
    public void init(){
        sut = new TelegramBotRepository();
    }

    @AfterEach
    public void finished(){
        sut = null;
    }

    @Test
    public void findCurrencyTest() throws IOException {
        String name = "USD";
        //String expected = "1 Доллар США 79,356";
        String result = sut.findCurrency(name);

        assertNotNull(result);
        //assertEquals(expected, result);
    }
}

