package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;

import java.util.List;
import java.util.stream.Collectors;

public class CurrencyConverter {

    public CurrencyConverter() {}

    List<Currency> convertAll(List<CurrencyDTO> list) {
        return list.stream()
                .map(CurrencyDTO::convertToBusinessCurrency)
                .collect(Collectors.toList());
    }
}
