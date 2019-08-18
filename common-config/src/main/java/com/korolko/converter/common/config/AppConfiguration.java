package com.korolko.converter.common.config;

import com.korolko.converter.repository.ApiCurrencyLoader;
import com.korolko.converter.repository.CurrencyLoader;
import com.korolko.converter.repository.CurrencyRepository;
import com.korolko.converter.repository.DefaultCurrencyRepository;
import com.korolko.converter.repository.ResourceCurrencyLoader;
import com.korolko.converter.service.CurrencyService;
import com.korolko.converter.service.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@EnableScheduling
public class AppConfiguration {

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
    public CurrencyLoader apiCurrencyLoader() {
        return new ApiCurrencyLoader(restTemplate());
    }

    @Bean CurrencyLoader resourceCurrencyLoader() {
        return new ResourceCurrencyLoader(currencyResourceLocationPattern);
    }

    @Bean
    public CurrencyRepository currencyRepository() {
        return new DefaultCurrencyRepository(apiCurrencyLoader(), resourceCurrencyLoader());
    }

    @Bean
    public CurrencyService currencyService() {
        return new CurrencyServiceImpl(currencyRepository());
    }
}
