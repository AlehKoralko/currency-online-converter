package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class CurrencyAPIRepository implements CurrencyRepository {

    private List<Currency> currencies;

    @Autowired
    public CurrencyAPIRepository(APIProvider provider) {
        currencies = provider.getAllCurrencies();
    }

    @Override
    public Optional<Currency> findById(long id) {
        return currencies.stream()
                .filter(currency -> currency.getId() == id)
                .findFirst();
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

    @Override
    public boolean isPresentById(Long id) {
        return this.findById(id).isPresent();
    }

    @Override
    public boolean isPresentByName(String name) {
        return this.findByName(name).isPresent();
    }

    @Override
    public boolean isPresentByAbbreviation(String abbreviation) {
        return this.findByAbbreviation(abbreviation).isPresent();
    }
}
