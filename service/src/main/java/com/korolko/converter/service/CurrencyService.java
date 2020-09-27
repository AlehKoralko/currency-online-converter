package com.korolko.converter.service;

import java.util.Collection;
import java.util.Optional;

public interface CurrencyService {

    Optional<Currency> getByAbbreviation(String abbreviation);

    Collection<Currency> getAll();
}
