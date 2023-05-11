package com.dmitrychinyaev.currencybot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ForeignCurrency {
    private String charCode;
    private int nominal;
    private String name;
    private double value;
}
