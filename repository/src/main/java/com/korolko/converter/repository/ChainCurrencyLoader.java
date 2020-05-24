package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Date: 04.18.2020
 *
 * @author Aleh Karalko
 */
public class ChainCurrencyLoader implements CurrencyLoader {

    private List<CurrencyLoader> loaders;

    public ChainCurrencyLoader(CurrencyLoader... loaders) {
        this.loaders = Arrays.asList(loaders);
    }

    @Override
    public Set<Currency> loadCurrencies() {
        Set<Currency> currencies = new HashSet<>();
        loaders.forEach(loader -> currencies.addAll(loader.loadCurrencies()));
        return currencies;
    }
}
