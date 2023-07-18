package com.dmitrychinyaev.currencybot.repository;

import com.dmitrychinyaev.currencybot.entity.ForeignCurrency;
import com.dmitrychinyaev.currencybot.entity.TelegramBotCommon;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class TelegramBotRepository {
    private Map<String,ForeignCurrency> foreignCurrencyBase;
    private String availableForeignCurrencyBase;
    private LocalDate dateOfUpdate;
    @Scheduled(cron = "@hourly")
    public void updateCurrencyBase() throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new URL(TelegramBotCommon.URL_DOWNLOAD_CURRENCY_BASE).openStream());

        Node root = doc.getDocumentElement();
        NodeList nodeList = root.getChildNodes();

        Map<String,ForeignCurrency> foreignCurrencyMap = new HashMap<>();
        StringBuilder availableCurrencies = new StringBuilder();

        for (int i = 0; i < nodeList.getLength(); i++){
            Node nodeForeignCurrency = nodeList.item(i);
            if (Node.ELEMENT_NODE == nodeForeignCurrency.getNodeType()){
                Element element = (Element) nodeForeignCurrency;

                String charcodeExtracted = element.getElementsByTagName(TelegramBotCommon.ATTRIBUTE_FOREIGN_CURRENCY_CHARCODE)
                        .item(0).getTextContent();
                int nominalExtracted = Integer.parseInt(element.getElementsByTagName(TelegramBotCommon.ATTRIBUTE_FOREIGN_CURRENCY_NOMINAL)
                        .item(0).getTextContent());
                String nameExtracted = element.getElementsByTagName(TelegramBotCommon.ATTRIBUTE_FOREIGN_CURRENCY_NAME)
                        .item(0).getTextContent();
                double valueExtracted = Double.parseDouble(element.getElementsByTagName(TelegramBotCommon.ATTRIBUTE_FOREIGN_CURRENCY_VALUE)
                        .item(0).getTextContent().replace(",","."));

                ForeignCurrency foreignCurrencyExtracted = new ForeignCurrency(charcodeExtracted, nominalExtracted, nameExtracted, valueExtracted);
                foreignCurrencyMap.put(charcodeExtracted, foreignCurrencyExtracted);
                availableCurrencies.append(TelegramBotCommon.FORMAT_CONSTRUCT_CURRENCY_LIST.formatted(nameExtracted, charcodeExtracted));
            }
        }
        foreignCurrencyBase = foreignCurrencyMap;
        availableForeignCurrencyBase = availableCurrencies.toString();
        dateOfUpdate = LocalDate.now();
    }

    public ForeignCurrency findForeignCurrencyByCharCode (String charCodeToFind) throws IOException, ParserConfigurationException, SAXException {
        checkIfBaseIsNullOrInvalid();
        return foreignCurrencyBase.get(charCodeToFind);
    }

    public String getStringDateOfUpdate() {
        return dateOfUpdate.toString();
    }

    public String getListOfAvailableCurrency() throws IOException, ParserConfigurationException, SAXException {
        checkIfBaseIsNullOrInvalid();
        return availableForeignCurrencyBase;
    }

    public void checkIfBaseIsNullOrInvalid() throws IOException, ParserConfigurationException, SAXException {
        if(foreignCurrencyBase == null || dateOfUpdate.isBefore(LocalDate.now())){
            updateCurrencyBase();
        }
    }
}
