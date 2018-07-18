package com.korolko.converter.service;

import com.korolko.converter.entity.Currency;

import java.util.List;
import java.util.Optional;

public interface CurrencyService {

    Optional<Currency> getById(long id);

    Optional<Currency> getByName(String name);

    Optional<Currency> getByAbbreviation(String abbreviation);

    List<Currency> getAll();

    /**
     *This method is designed to convert one currency to another
     *
     * @param currentCurrency means the currency that is converted
     * @param targetCurrency means the currency into which to convert
     * @param amount this is the amount of the current currency
     * @return the converted value currentCurrency to targetCurrency
     */
    double convert(Currency currentCurrency, Currency targetCurrency, double amount);

}
