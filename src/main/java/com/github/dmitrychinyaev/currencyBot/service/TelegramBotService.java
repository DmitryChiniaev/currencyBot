package com.github.dmitrychinyaev.currencyBot.service;

import com.github.dmitrychinyaev.currencyBot.repository.TelegramBotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Component
public class TelegramBotService {
    private final TelegramBotRepository repository;

    public String makeCalculation(String request) throws IOException {
        if (Pattern.matches("\\d+(\\.\\d+)?\\s\\w{3}", request)) {
            return String.format("%s\n%s", calculateToChosenCurrency(request), getUpdateOfRep());
        }
        if (Pattern.matches("\\d+(\\.\\d+)?\\sRUB\\s\\w{3}", request.toUpperCase())) {
            return String.format("%s\n%s", calculateToRub(request), getUpdateOfRep());
        }
        return "Неверно выполнен запрос. Попробуйте снова.";
    }

    public String calculateToChosenCurrency(String toRep) throws IOException {
        String[] request = toRep.split(" ");
        List<String> list = new ArrayList<>(List.of(repository.findCurrency(request[1]).split(" ")));
        BigDecimal result = BigDecimal.valueOf(Double.parseDouble(request[0].replace(",", ".")))
                .multiply(BigDecimal.valueOf(Double.parseDouble(list.get(list.size() - 1).replace(",", "."))))
                .divide(BigDecimal.valueOf(Double.parseDouble(list.get(0))), MathContext.DECIMAL128);
        list.set(0, request[0]);
        list.set(list.size() - 1, String.format("= %.2f", result));
        return makeResponse(list);
    }

    public String calculateToRub(String toRep) throws IOException {
        List<String> request = new ArrayList<>(Arrays.stream(toRep.toUpperCase().split(" ")).toList());
        List<String> list = new ArrayList<>(List.of(repository.findCurrency(request.get(2)).split(" ")));
        BigDecimal result = BigDecimal.valueOf(Double.parseDouble(request.get(0).replace(",", ".")))
                .multiply(BigDecimal.valueOf(Double.parseDouble(list.get(0))))
                .divide(BigDecimal.valueOf(Double.parseDouble(list.get(list.size() - 1).replace(",", "."))), MathContext.DECIMAL128);
        request.add(2,String.format("= %.2f", result));
        return makeResponse(request);
    }

    public String getUpdateOfRep() {
        return String.format("Согласно ЦБ РФ на %s", repository.getStringDateOfUpdate());
    }

    public String makeResponse(List<String> list){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : list) {
            stringBuilder.append(s).append(" ");
        }
        if (!list.get(1).equals("RUB")){
            stringBuilder.append("RUB.");
        }
        return stringBuilder.toString();
    }

    public String getAvailableCurrency() throws IOException {
        return repository.getListOfAvailableCurrency();
    }
}
