package ru.alpha.gifcurrency;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.alpha.gifcurrency.clients.OpenexCurrencyClient;
import ru.alpha.gifcurrency.controllers.CurrencyController;
import ru.alpha.gifcurrency.entities.ExchangeRatesEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
class CurrencyControllerTest {

    @Autowired
    private CurrencyController currencyController;

    @MockBean
    private OpenexCurrencyClient currencyClient;

    @Test
    void getCurrencyFromMap() {
        Map<String,Float> test = new HashMap<>();
        test.put("USD", 1f);
        test.put("RUB", 2f);
        Map<String,Float> empty = new HashMap<>();

        Assertions.assertEquals(0,currencyController.getCurrencyFromMap(test, "NotACurrency"));
        Assertions.assertEquals(1,currencyController.getCurrencyFromMap(test, "USD"));
        Assertions.assertEquals(0,currencyController.getCurrencyFromMap(empty, "USD"));
        Assertions.assertEquals(2,currencyController.getCurrencyFromMap(test, "RUB"));
    }

    @Test
    void getActualValue() {
        Map<String,Float> simpleMap = new HashMap<>();
        simpleMap.put("USD", 1f);
        simpleMap.put("RUB", 65f);

        ExchangeRatesEntity exRate = new ExchangeRatesEntity("disc","licence",11,"USD", simpleMap);
        Mockito.doReturn(exRate).when(currencyClient).getActualValue("1911c108dada4ca1b52967f3f0a9814c","USD");
        Assertions.assertEquals(String.valueOf(65f),currencyController.getActualValue("RUB"));
        Assertions.assertEquals(String.valueOf(0f),currencyController.getActualValue("NotACurrency"));
    }

    @Test
    void getYesterdayValue() {
        Map<String,Float> simpleMap = new HashMap<>();
        simpleMap.put("USD", 1f);
        simpleMap.put("RUB", 65f);

        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date date = new Date(System.currentTimeMillis() - DAY_IN_MS);
        String dataFormatted = new SimpleDateFormat("yyyy-MM-dd").format(date);

        ExchangeRatesEntity exRate = new ExchangeRatesEntity("disc","licence",11,"USD", simpleMap);
        Mockito.doReturn(exRate).when(currencyClient).getYesterdayValue("1911c108dada4ca1b52967f3f0a9814c","USD", dataFormatted);
        Assertions.assertEquals(String.valueOf(65f),currencyController.getYesterdayValue("RUB"));
        Assertions.assertEquals(String.valueOf(0f),currencyController.getYesterdayValue("NotACurrency"));
    }
}
