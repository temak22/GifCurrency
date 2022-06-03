package ru.alpha.gifcurrency.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExchangeRatesEntity {
    private String disclaimer;
    private String license;
    private int timestamp;
    private String base;
    private Map<String, Float> rates;
}