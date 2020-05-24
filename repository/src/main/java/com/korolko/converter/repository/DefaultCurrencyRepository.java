package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class DefaultCurrencyRepository implements CurrencyRepository, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultCurrencyRepository.class);

    private Set<Currency> currencies;
    private CurrencyLoader currencyLoader;

    public DefaultCurrencyRepository(CurrencyLoader currencyLoader) {
        this.currencyLoader = currencyLoader;
    }

    @Override
    @Scheduled(fixedDelayString = "${currency.api.scheduler.delay}")
    public void afterPropertiesSet() throws Exception {
        currencies = new TreeSet<>(currencyLoader.loadCurrencies());
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
    public Set<Currency> findAll() {
        return currencies;
    }
}
