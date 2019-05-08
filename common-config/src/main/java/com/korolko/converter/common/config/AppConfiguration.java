package com.korolko.converter.common.config;

import com.korolko.converter.repository.APIProvider;
import com.korolko.converter.repository.CurrencyAPIRepository;
import com.korolko.converter.repository.CurrencyConverter;
import com.korolko.converter.repository.CurrencyRepository;
import com.korolko.converter.service.CurrencyService;
import com.korolko.converter.service.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    @Value("${currency.api.url}")
    private String currencyApiUrl;

    @Bean
    public CurrencyConverter currencyConverter() {
        return new CurrencyConverter();
    }

    @Bean
    public APIProvider apiProvider() {
        return new APIProvider(currencyConverter(), currencyApiUrl);
    }

    @Bean
    public CurrencyRepository currencyRepository() {
        return new CurrencyAPIRepository(apiProvider());
    }

    @Bean
    public CurrencyService currencyService() {
        return new CurrencyServiceImpl(currencyRepository());
    }
}
