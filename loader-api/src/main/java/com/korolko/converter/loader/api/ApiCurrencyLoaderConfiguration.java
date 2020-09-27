package com.korolko.converter.loader.api;

import com.korolko.converter.loader.CurrencyLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
@Configuration
public class ApiCurrencyLoaderConfiguration {

    @Value("${currency.api.url}")
    private String currencyApiUrl;

    @Value("${currency.api.timeout}")
    private int timeoutInMilliseconds;

    @Bean
    public CurrencyLoader apiCurrencyLoader() {
        return new ApiCurrencyLoader(createRestTemplate());
    }

    private RestTemplate createRestTemplate() {
        return new RestTemplateBuilder()
                .setConnectTimeout(timeoutInMilliseconds)
                .setReadTimeout(timeoutInMilliseconds)
                .uriTemplateHandler(new DefaultUriBuilderFactory(currencyApiUrl))
                .build();
    }
}
