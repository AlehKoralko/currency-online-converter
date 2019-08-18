package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class ApiCurrencyLoader implements CurrencyLoader {

    private RestTemplate restTemplate;

    public ApiCurrencyLoader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Set<Currency> loadCurrencies() throws RuntimeException {
        ResponseEntity<Currency[]> response = restTemplate.getForEntity(StringUtils.EMPTY, Currency[].class);
        Currency[] currencies = getCurrenciesFromResponse(response);
        return new TreeSet<>(Arrays.asList(currencies));
    }

    private Currency[] getCurrenciesFromResponse(ResponseEntity<Currency[]> response) {
        checkResponseStatus(response.getStatusCode());
        return checkCurrencies(response.getBody());
    }

    private void checkResponseStatus(HttpStatus responseStatus) {
        if (responseStatus != HttpStatus.OK) {
            String errorMessage = String.format("Attempt to load currencies from API has a {%s} status", responseStatus);
            throw new RuntimeException(errorMessage);
        }
    }

    private Currency[] checkCurrencies(Currency[] currencies) {
        if (ArrayUtils.isEmpty(currencies)) {
            throw new RuntimeException("Currency API has no currencies");
        }
        return currencies;
    }
}
