package com.dmitrychinyaev.currencybot.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(value = {"ID","NumCode"})
public class Valute {
    private String charCode;
    private int nominal;
    private String name;
    private double value;
}

