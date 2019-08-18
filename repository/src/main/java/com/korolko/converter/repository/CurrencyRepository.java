package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;

import java.util.Optional;
import java.util.Set;

public interface CurrencyRepository {

    Optional<Currency> findByName(String name);

    Optional<Currency> findByAbbreviation(String abbreviation);

    Set<Currency> findAll();
}
