package ru.alpha.gifcurrency.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alpha.gifcurrency.clients.OpenexCurrencyClient;
import ru.alpha.gifcurrency.entities.ExchangeRatesEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired
    private OpenexCurrencyClient currencyClient;

    @Value("${openex.apiKey}")
    private String apiKey;

    @Value("${openex.base}")
    private String base;

    public float getCurrencyFromMap(Map<String, Float> map, String currency){
        if(!map.isEmpty()) {
            return map.getOrDefault(currency, 0f);
        }
        return 0f;
    }

    @RequestMapping("/actualValue/{currency}")
    public String getActualValue(@PathVariable String currency){
        ExchangeRatesEntity exchangeRatesEntity = currencyClient.getActualValue(apiKey, base);
        return String.valueOf(getCurrencyFromMap(exchangeRatesEntity.getRates(), currency));
    }

    @RequestMapping("/yesterdayValue/{currency}")
    public String getYesterdayValue(@PathVariable String currency){
        long DAY_IN_MS = 1000 * 60 * 60 * 24;

        Date date = new Date(System.currentTimeMillis() - DAY_IN_MS);
        String dataFormatted = new SimpleDateFormat("yyyy-MM-dd").format(date);

        ExchangeRatesEntity exchangeRatesEntity = currencyClient.getYesterdayValue(apiKey, base, dataFormatted);
        return String.valueOf(getCurrencyFromMap(exchangeRatesEntity.getRates(), currency));
    }
}