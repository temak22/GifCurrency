package ru.alpha.gifcurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GifcurrencyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GifcurrencyApplication.class, args);
    }

}
