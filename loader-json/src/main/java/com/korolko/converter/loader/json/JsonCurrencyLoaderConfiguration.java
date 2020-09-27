package com.korolko.converter.loader.json;

import com.korolko.converter.loader.CurrencyLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
@Configuration
public class JsonCurrencyLoaderConfiguration {

    @Value("${currency.resource.location.pattern}")
    private String currencyResourceLocationPattern;

    @Bean
    public CurrencyLoader jsonCurrencyLoader() {
        return new JsonCurrencyLoader(currencyResourceLocationPattern);
    }
}
