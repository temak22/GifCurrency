package ru.alpha.gifcurrency.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.alpha.gifcurrency.clients.GiphyClient;
import ru.alpha.gifcurrency.entities.GiphyDataEntity;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/giphy")
public class GiphyController {

    @Autowired
    private GiphyClient giphyClient;

    @Autowired
    private CurrencyController currencyController;

    @Value("${giphy.apiKey}")
    private String apiKey;

    @Value("${giphy.answer.bigger}")
    private String bigger;

    @Value("${giphy.answer.less}")
    private String less;

    @Value("${giphy.answer.same}")
    private String same;

    @RequestMapping("/{currency}")
    public String getGifPage(@PathVariable String currency){
        float actualValue = Float.parseFloat(currencyController.getActualValue(currency));
        float yesterdayValue = Float.parseFloat(currencyController.getYesterdayValue(currency));

        String request = "";
        if (actualValue > yesterdayValue){
            request = bigger;
        } else if (actualValue < yesterdayValue){
            request = less;
        } else {
            request = same;
        }

        //получаем данные рандомно выбранной gif
        GiphyDataEntity giphyDataEntity = giphyClient.getGiphy(
                apiKey,
                request,
                new Random().nextInt(100));

        //data->images->original->url : получаем url этой gif
        Map images = (Map) giphyDataEntity.getData()[0].get("images");
        Map original = (Map) images.get("original");
        String gifUrl = (String) original.get("url");

        return "<html><head><title>" +
                request +
                "</title></head><body><img src=\"" +
                gifUrl +
                "\"></body></html>";
    }
}
