package com.dmitrychinyaev.currencybot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(value = {"name"})
public final class ValCurs {
    @JacksonXmlProperty(localName = "Date")
    private String date;
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<Valute> Valute = new ArrayList<>();
}

