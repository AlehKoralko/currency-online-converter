package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
class CurrencyConverter {

    public CurrencyConverter() {}

    List<Currency> convertAll(List<CurrencyDTO> list) {
        return list.stream()
                .map(CurrencyDTO::convertToBusinessCurrency)
                .collect(Collectors.toList());
    }
}
