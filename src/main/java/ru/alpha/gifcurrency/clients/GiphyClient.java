package ru.alpha.gifcurrency.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.alpha.gifcurrency.entities.GiphyDataEntity;

@FeignClient(value = "giphy", url = "${giphy.url}")
public interface GiphyClient {
    @GetMapping("?api_key={apiKey}&q={query}&limit=1&offset={offset}&bundle=messaging_non_clips")
    GiphyDataEntity getGiphy(@PathVariable String apiKey, @PathVariable String query, @PathVariable Integer offset);
}
