package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;
import com.korolko.converter.repository.exception.CurrencyNotFoundRuntimeException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CurrencyAPIRepository implements CurrencyRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyAPIRepository.class);

    private RestTemplate restTemplate;
    private List<Currency> currencies;

    public CurrencyAPIRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostConstruct
    @Scheduled(fixedDelayString = "${currency.api.delay}")
    public void init() {
        currencies = loadCurrencies();
        LOGGER.info("Loaded '{}' currency rates", currencies.size());
    }

    @Override
    public Optional<Currency> findByName(String name) {
        return currencies.stream()
                .filter(currency -> currency.getName().equals(name))
                .findFirst();
    }

    @Override
    public Optional<Currency> findByAbbreviation(String abbreviation) {
        return currencies.stream()
                .filter(currency -> currency.getAbbreviation().equals(abbreviation))
                .findFirst();
    }

    @Override
    public List<Currency> findAll() {
        return currencies;
    }

    private List<Currency> loadCurrencies() {
        try {
            ResponseEntity<Currency[]> response = restTemplate.getForEntity(Strings.EMPTY, Currency[].class);
            if (response.getStatusCode() == HttpStatus.OK && Objects.nonNull(response.getBody())) {
                return Stream.of(response.getBody()).collect(Collectors.toCollection(ArrayList::new));
            }
            throw new CurrencyNotFoundRuntimeException();
        } catch (RuntimeException e) {
            LOGGER.error("Failed to load currency rates.", e);
            return Collections.emptyList();
        }
    }
}
