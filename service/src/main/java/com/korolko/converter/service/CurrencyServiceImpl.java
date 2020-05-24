package com.korolko.converter.service;

import com.korolko.converter.domain.Currency;
import com.korolko.converter.repository.CurrencyRepository;

import java.util.Optional;
import java.util.Set;

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
    public Set<Currency> getAll() {
        return currencyRepository.findAll();
    }
}
