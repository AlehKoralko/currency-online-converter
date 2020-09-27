package com.korolko.converter.loader.api;

import com.korolko.converter.loader.AbstractCurrencyLoader;
import com.korolko.converter.service.Currency;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
class ApiCurrencyLoader extends AbstractCurrencyLoader {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiCurrencyLoader.class);

    private final RestTemplate restTemplate;

    public ApiCurrencyLoader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Collection<Currency> doLoad() {
        try {
            ResponseEntity<Currency[]> response = restTemplate.getForEntity(StringUtils.EMPTY, Currency[].class);
            Currency[] currencies = getCurrenciesFromResponse(response);
            return new HashSet<>(Arrays.asList(currencies));
        } catch (RuntimeException e) {
            LOGGER.warn("Failed to load currencies from API.");
        }
        return Collections.emptySet();
    }

    private Currency[] getCurrenciesFromResponse(ResponseEntity<Currency[]> response) {
        checkResponseStatus(response.getStatusCode());
        return extractCurrencies(response);
    }

    private void checkResponseStatus(HttpStatus responseStatus) {
        if (HttpStatus.OK != responseStatus) {
            String errorMessage = String.format("Attempt to load currencies from API has a [%s] status", responseStatus);
            throw new RuntimeException(errorMessage);
        }
    }

    private Currency[] extractCurrencies(ResponseEntity<Currency[]> response) {
        Currency[] currencies = response.getBody();
        if (ArrayUtils.isEmpty(currencies)) {
            throw new RuntimeException("Currency API has no currencies");
        }
        return currencies;
    }
}
