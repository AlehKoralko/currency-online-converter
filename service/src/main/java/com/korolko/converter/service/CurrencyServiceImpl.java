package com.korolko.converter.service;

import com.korolko.converter.domain.Currency;
import com.korolko.converter.repository.CurrencyRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
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
    public BigDecimal convert(String currentCurrencyAbbr, String targetCurrencyAbbr, double amountOfCurrentCurrency) {
        Optional<Currency> currentCurrency = currencyRepository.findByAbbreviation(currentCurrencyAbbr);
        Optional<Currency> targetCurrency = currencyRepository.findByAbbreviation(targetCurrencyAbbr);

        if (currentCurrency.isPresent() && targetCurrency.isPresent()) {
            return CurrencyConverter.convert(currentCurrency.get(), targetCurrency.get(), amountOfCurrentCurrency);
        }
        return BigDecimal.ZERO;
    }
}
