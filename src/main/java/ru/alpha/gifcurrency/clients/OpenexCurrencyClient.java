package ru.alpha.gifcurrency.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.alpha.gifcurrency.entities.ExchangeRatesEntity;

@FeignClient(value = "currency", url = "${openex.url}")
public interface OpenexCurrencyClient {
    @GetMapping("latest.json?app_id={appID}&base={base}")
    ExchangeRatesEntity getActualValue(@PathVariable String appID, @PathVariable String base);

    @GetMapping("historical/{date}.json?app_id={appID}&base={base}")
    ExchangeRatesEntity getYesterdayValue(@PathVariable String appID, @PathVariable String base, @PathVariable String date);
}
