package com.korolko.converter.service;

import com.korolko.converter.domain.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

final class CurrencyConverter {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyConverter.class);

    private CurrencyConverter() {
        throw new AssertionError();
    }

    public static BigDecimal convert(Currency current, Currency target, double amountOfCurrentCurrency) {
        double currencyRateRatio = getRateOfOneUnitOfCurrency(target) / getRateOfOneUnitOfCurrency(current);
        BigDecimal result = BigDecimal.valueOf(amountOfCurrentCurrency / currencyRateRatio)
                .setScale(2, RoundingMode.HALF_UP);

        LOGGER.info("Convert '{}' of '{}' to '{}' of '{}'", amountOfCurrentCurrency, current.getAbbreviation(),
                result, target.getAbbreviation());
        return result;
    }

    private static double getRateOfOneUnitOfCurrency(Currency currency) {
        return currency.getRate() / currency.getScale();
    }
}
