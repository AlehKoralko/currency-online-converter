package com.korolko.converter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

class CurrencyServiceImpl implements CurrencyService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    private final CurrencyLoadingManager currencyLoadingManager;
    private Set<Currency> currencies;

    CurrencyServiceImpl(CurrencyLoadingManager currencyLoadingManager) {
        this.currencyLoadingManager = currencyLoadingManager;
    }

    @Override
    @Scheduled(fixedDelayString = "${currency.api.scheduler.delay}")
    public void afterPropertiesSet() throws Exception {
        currencies = new TreeSet<>(currencyLoadingManager.load());
        LOGGER.info("Loaded '{}' currency rates", currencies.size());
    }

    @Override
    public Optional<Currency> getByAbbreviation(String abbreviation) {
        return currencies.stream()
                .filter(currency -> currency.getAbbreviation().equals(abbreviation))
                .findFirst();
    }

    @Override
    public Collection<Currency> getAll() {
        return currencies;
    }
}
