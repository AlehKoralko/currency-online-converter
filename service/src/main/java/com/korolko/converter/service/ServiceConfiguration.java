package com.korolko.converter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class ServiceConfiguration {

    @Autowired
    private CurrencyLoadingManager currencyLoadingManager;

    @Bean
    public CurrencyService currencyService() {
        return new CurrencyServiceImpl(currencyLoadingManager);
    }

    @Bean
    public CurrencyConverter currencyConverter() {
        return new CurrencyConverter(currencyService());
    }
}
