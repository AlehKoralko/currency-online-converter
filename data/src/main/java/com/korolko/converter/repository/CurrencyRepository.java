package com.korolko.converter.repository;

import com.korolko.converter.entity.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyRepository {

    Optional<Currency> findById(long id);

    Optional<Currency> findByName(String name);

    Optional<Currency> findByAbbreviation(String abbreviation);

    List<Currency> findAll();

}
