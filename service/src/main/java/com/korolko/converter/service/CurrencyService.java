package com.korolko.converter.service;

import com.korolko.converter.domain.Currency;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

public interface CurrencyService {

    Optional<Currency> getByName(String name);

    Optional<Currency> getByAbbreviation(String abbreviation);

    Set<Currency> getAll();

    BigDecimal convert(String currentCurrencyAbbr, String targetCurrencyAbbr, double amount);
}
