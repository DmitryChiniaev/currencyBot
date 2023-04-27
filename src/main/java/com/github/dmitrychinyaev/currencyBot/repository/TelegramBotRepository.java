package com.github.dmitrychinyaev.currencyBot.repository;

import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
public class TelegramBotRepository {
    private String currencyBase;

    private Date dateOfUpdate;
    private final String urlUpdate = "https://cbr.ru/currency_base/daily/";
    private final String cssQueryUpdate = "td";
    @Scheduled(cron = "@hourly")
    public void updateCurrencyBase() throws IOException {
        currencyBase = Jsoup.connect(urlUpdate).get().select(cssQueryUpdate).text();
        dateOfUpdate = new Date();
    }

    public String findCurrency (String currencyTreeSigns) throws IOException {
        if(currencyBase == null){
            updateCurrencyBase();
        }
        int start = currencyBase.indexOf(currencyTreeSigns.toUpperCase()) + 4;
        int stop = start;
        char comma = ',';
        while (!(currencyBase.charAt(stop) == comma)){
            stop++;
        }
        return currencyBase.substring(start, stop + 4);
    }

    public String getStringDateOfUpdate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(dateOfUpdate);
    }

    public String getListOfAvailableCurrency() throws IOException {
        if(currencyBase == null){
            updateCurrencyBase();
        }
        return currencyBase.replaceAll("[^a-zA-Zа-яёА-ЯЁ ]", "").replaceAll("   ", "\n")
                .replaceAll("их","ий").replaceAll("ов","")
                .replaceAll("Ный т", "Новый т").trim();
    }
}
