package com.korolko.converter.loader;

import com.korolko.converter.service.CurrencyLoadingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
@Configuration
public class LoadingManagerConfiguration {

    @Autowired
    private List<CurrencyLoader> loaders;

    @Bean
    public CurrencyLoadingManager defaultCurrencyLoadingManager() {
        return new DefaultCurrencyLoadingManager(loaders);
    }
}
