package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;

import java.util.Set;

@FunctionalInterface
public interface CurrencyLoader {

    Set<Currency> loadCurrencies();
}
