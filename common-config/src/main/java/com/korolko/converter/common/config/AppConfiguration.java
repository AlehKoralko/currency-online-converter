package com.korolko.converter.common.config;

import com.korolko.converter.repository.CurrencyAPIRepository;
import com.korolko.converter.repository.CurrencyRepository;
import com.korolko.converter.service.CurrencyService;
import com.korolko.converter.service.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
public class AppConfiguration {

    @Value("${currency.api.url}")
    private String currencyApiUrl;

    @Value("${currency.api.timeout}")
    private int timeoutInMilliseconds;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(timeoutInMilliseconds)
                .setReadTimeout(timeoutInMilliseconds)
                .build();
    }

    @Bean
    public CurrencyRepository currencyRepository() {
        return new CurrencyAPIRepository(restTemplate(), currencyApiUrl);
    }

    @Bean
    public CurrencyService currencyService() {
        return new CurrencyServiceImpl(currencyRepository());
    }
}
