package com.dmitrychinyaev.currencybot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"ID","NumCode"})
@AllArgsConstructor
public class Valute {
    @JacksonXmlProperty(localName = "CharCode")
    private String charCode;
    @JacksonXmlProperty(localName = "Nominal")
    private int nominal;
    @JacksonXmlProperty(localName = "Name")
    private String name;
    @JacksonXmlProperty(localName = "Value")
    private String value;
}

