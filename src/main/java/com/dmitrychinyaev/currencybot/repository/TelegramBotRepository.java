package com.dmitrychinyaev.currencybot.repository;

import com.dmitrychinyaev.currencybot.entity.TelegramBotCommon;
import com.dmitrychinyaev.currencybot.entity.ValCurs;
import com.dmitrychinyaev.currencybot.entity.Valute;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class TelegramBotRepository {
    private List<Valute> foreignCurrencyBase;
    private String availableForeignCurrencyBase;
    private Date dateOfUpdate;
    private final XmlMapper xmlMapper = new XmlMapper();

    public void updateCurrencyBase() throws IOException, ParseException {
        URL cbrXMLurl = new URL(TelegramBotCommon.URL_DOWNLOAD_CURRENCY_BASE);
        ValCurs allCurrencyXML = xmlMapper.readValue(cbrXMLurl, ValCurs.class);

        foreignCurrencyBase = allCurrencyXML.getValute();
        availableForeignCurrencyBase = getAvailableForeignCurrencyString(foreignCurrencyBase);
        dateOfUpdate = new SimpleDateFormat("dd.MM.yyyy").parse(allCurrencyXML.getDate());
    }

    public String getAvailableForeignCurrencyString(List<Valute> valuteList){
        StringBuilder stringBuilder = new StringBuilder();
        for (Valute valute:valuteList) {
            stringBuilder.append(valute.getName()).append("\n");
        }
        return stringBuilder.toString();
    }

    public Optional<Valute> findValuteByCharCode (String charCodeToFind) throws IOException, ParseException {
        checkIfBaseIsNullOrInvalid();
        return foreignCurrencyBase.stream()
                .filter(s -> s.getCharCode().equals(charCodeToFind))
                .findFirst();
    }

    public String getStringDateOfUpdate() {
        return dateOfUpdate.toString();
    }

    public String getListOfAvailableCurrency() throws IOException, ParseException {
        checkIfBaseIsNullOrInvalid();
        return availableForeignCurrencyBase;
    }

    public void checkIfBaseIsNullOrInvalid() throws IOException, ParseException {
        if(foreignCurrencyBase == null || dateOfUpdate.before(new Date())){
            updateCurrencyBase();
        }
    }
}
