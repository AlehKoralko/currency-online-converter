package com.korolko.converter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverter.class);

    private final CurrencyService currencyService;

    public CurrencyConverter(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public BigDecimal convert(ConversionContainer container) {
        BigDecimal amount = container.getAmount();
        Currency source = getCurrency(container.getSourceAbbreviation());
        Currency target = getCurrency(container.getTargetAbbreviation());
        BigDecimal result = amount.multiply(getCurrenciesRation(source, target));
        LOGGER.info("Convert '{}' of '{}' to '{}' of '{}'", amount, source.getAbbreviation(),
                result, target.getAbbreviation());
        return result;
    }

    private Currency getCurrency(String abbreviation) {
        return currencyService
                .getByAbbreviation(abbreviation)
                .orElseThrow(() -> new RuntimeException(String.format("Currency [%s] not found.", abbreviation)));
    }

    private BigDecimal getCurrenciesRation(Currency source, Currency target) {
        BigDecimal rateOfOneOfSource = source.getRateOfOne();
        BigDecimal rateOfOneOfTarget = target.getRateOfOne();
        return rateOfOneOfSource.divide(rateOfOneOfTarget, 4, RoundingMode.HALF_UP);
    }
}
