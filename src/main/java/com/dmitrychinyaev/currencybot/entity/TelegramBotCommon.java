package com.dmitrychinyaev.currencybot.entity;

import org.springframework.stereotype.Component;

@Component
public class TelegramBotCommon {
    //bot commands
    public static final String COMMAND_START = "/start";
    public static final String COMMAND_HELP = "/help";
    public static final String COMMAND_SHOW_ALL_CURRENCY = "/currency";

    //regex
    public static final String REGEX_CONVERT_TO_RUB = "\\d+(\\.\\d+)?\\s\\w{3}";
    public static final String REGEX_CONVERT_RUB_TO_CURRENCY = "\\d+(\\.\\d+)?\\sRUB\\s\\w{3}";

    //text
    public static final String TEXT_HELP =
            """
                    Чтобы перевести иностранную валюту в рубли, напишите сообщение в формате ''100 USD'', где на первом месте укажите желаемое число и валюту.
                    Чтобы перевести рубли в валюту, напишите сообщение в формате ''100 RUB USD''
                    Список доступных валют можно посмотреть по команде /currency""";

    public static final String TEXT_GREETING = "Приветствую, %s! Введите число и валюту из списка.";
    public static final String TEXT_INCORRECT_REQUEST = "Неверно выполнен запрос. Попробуйте снова.";
    public static final String TEXT_NOT_POSITIVE = "Было введено не положительное число. Попробуйте снова";
    public static final String TEXT_NOT_FOUND_CURRENCY = "Запрашиваемая валюта не найдена. Попробуйте снова";

    //format
    public static final String FORMAT_RESULT_MESSAGE = "%s\n%s";
    public static final String FORMAT_CONVERT_TO_RUB = "%s %s = %.2f RUB";
    public static final String FORMAT_CONVERT_RUB_TO_CURRENCY = "%s RUB = %.2f %s";
    public static final String FORMAT_MESSAGE_DATE_OF_UPGRADE = "Согласно ЦБ РФ на %s";
    public static final String FORMAT_DATE_OF_BASE_UPGRADE = "dd.MM.yyyy";
    public static final String FORMAT_CONSTRUCT_CURRENCY_LIST = "%s %s \n";

    //attribute
    public static final String ATTRIBUTE_FOREIGN_CURRENCY_CHARCODE = "CharCode";
    public static final String ATTRIBUTE_FOREIGN_CURRENCY_NOMINAL = "Nominal";
    public static final String ATTRIBUTE_FOREIGN_CURRENCY_NAME = "Name";
    public static final String ATTRIBUTE_FOREIGN_CURRENCY_VALUE = "Value";
    public static final String ATTRIBUTE_CRON_BASE_UPDATE = "@hourly";

    //test parameters SMC = SERVICE MAKE CALCULATION
    public static final String TEST_SMC_USD = "100 USD";
    public static final String TEST_SMC_ERROR_MESSAGE = "some shit";
    public static final String TEST_SMC_RUB_TO_USD = "1000 RUB USD";
    public static final String TEST_SMC_ZERO_NUMBER = "0 USD";

    public static final String TEST_SMC_RESULT_USD = "100 USD = 10000,00 RUB\n" +
            "Согласно ЦБ РФ на 01.01.1999";
    public static final String TEST_SMC_RESULT_RUB_TO_USD = "1000 RUB = 10,00 USD\n" +
            "Согласно ЦБ РФ на 01.01.1999";
    public static final ForeignCurrency TEST_CURRENCY_USD_100 = new ForeignCurrency("USD", 1, "DOLLAR", 100);
    public static final String TEST_DATE_TO_RETURN = "01.01.1999";
    public static final String TEST_NUMBER_TO_CONVERT = "1000";
    public static final String TEST_CHARCODE_TO_CONVERT = "EUR";
    public static final String TEST_CONVERSION_RESULT_TO_CURRENCY = "1000 USD = 100000,00 RUB";
    public static final String TEST_CONVERSION_RESULT_TO_RUB ="1000 RUB = 10,00 USD";
    //URL
    public final static String URL_DOWNLOAD_CURRENCY_BASE = "https://www.cbr-xml-daily.ru/daily_utf8.xml";
}
