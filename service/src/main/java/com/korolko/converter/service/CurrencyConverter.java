package com.korolko.converter.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverter.class);

    private static final String CONVERT_LOG_TEMPLATE = "Convert '{}' of '{}' to '{}' of '{}'";

    private final CurrencyService currencyService;

    public CurrencyConverter(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public BigDecimal convert(ConversionContainer container) {
        BigDecimal amount = container.getAmount();
        String sourceAbbreviation = container.getSourceAbbreviation();
        String targetAbbreviation = container.getTargetAbbreviation();

        Currency source = getCurrency(sourceAbbreviation);
        Currency target = getCurrency(targetAbbreviation);
        BigDecimal result = amount.multiply(getCurrenciesRation(source, target));

        LOGGER.info(CONVERT_LOG_TEMPLATE, amount, sourceAbbreviation, result, targetAbbreviation);
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
