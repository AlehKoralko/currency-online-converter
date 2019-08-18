package com.korolko.converter.service.config;

import com.korolko.converter.repository.CurrencyRepository;
import com.korolko.converter.repository.config.RepositoryConfiguration;
import com.korolko.converter.service.CurrencyService;
import com.korolko.converter.service.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RepositoryConfiguration.class})
public class ServiceConfiguration {

    @Bean
    @Autowired
    public CurrencyService currencyService(CurrencyRepository currencyRepository) {
        return new CurrencyServiceImpl(currencyRepository);
    }
}
