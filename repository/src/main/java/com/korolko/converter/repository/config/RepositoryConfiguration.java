package com.korolko.converter.repository.config;

import com.korolko.converter.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableScheduling
public class RepositoryConfiguration {

    @Value("${currency.api.url}")
    private String currencyApiUrl;

    @Value("${currency.resource.location.pattern}")
    private String currencyResourceLocationPattern;

    @Value("${currency.api.timeout}")
    private int timeoutInMilliseconds;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(timeoutInMilliseconds)
                .setReadTimeout(timeoutInMilliseconds)
                .uriTemplateHandler(new DefaultUriBuilderFactory(currencyApiUrl))
                .build();
    }

    @Bean
    public CurrencyLoader chainCurrencyLoader() {
        return new ChainCurrencyLoader(
                apiCurrencyLoader(),
                resourceCurrencyLoader()
        );
    }

    @Bean
    public CurrencyLoader apiCurrencyLoader() {
        return new ApiCurrencyLoader(restTemplate());
    }

    @Bean CurrencyLoader resourceCurrencyLoader() {
        return new ResourceCurrencyLoader(currencyResourceLocationPattern);
    }

    @Bean
    public CurrencyRepository currencyRepository() {
        return new DefaultCurrencyRepository(chainCurrencyLoader());
    }
}
