package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {

    Optional<Currency> findByName(String name);

    Optional<Currency> findByAbbreviation(String abbreviation);

    List<Currency> findAll();
}