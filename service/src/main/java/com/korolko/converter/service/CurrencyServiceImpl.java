package com.korolko.converter.service;

import com.korolko.converter.domain.Currency;
import com.korolko.converter.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Optional<Currency> getById(long id) {
        return currencyRepository.findById(id);
    }

    @Override
    public Optional<Currency> getByName(String name) {
        return currencyRepository.findByName(name);
    }

    @Override
    public Optional<Currency> getByAbbreviation(String abbreviation) {
        return currencyRepository.findByAbbreviation(abbreviation);
    }

    @Override
    public List<Currency> getAll() {
        return currencyRepository.findAll();
    }

    @Override
    public BigDecimal convert(String currentCurrencyAbbr, String targetCurrencyAbbr, double amount) {
        Optional<Currency> currentCurrency = currencyRepository.findByAbbreviation(currentCurrencyAbbr);
        Optional<Currency> targetCurrency = currencyRepository.findByAbbreviation(targetCurrencyAbbr);

        if (currentCurrency.isPresent() && targetCurrency.isPresent()) {
            LOGGER.info("Convert " + amount + " " + currentCurrencyAbbr + " to " + targetCurrencyAbbr);
            return currentCurrency.get().convertTo(targetCurrency.get(), amount);
        }
        return BigDecimal.ZERO;
    }
}
