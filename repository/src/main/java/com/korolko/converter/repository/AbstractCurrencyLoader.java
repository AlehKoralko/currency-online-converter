package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

/**
 * Date: 04.18.2020
 *
 * @author Aleh Karalko
 */
public abstract class AbstractCurrencyLoader implements CurrencyLoader {

    @Override
    public final Set<Currency> loadCurrencies() {
        Set<Currency> currencies = load();
        fillRateOfOne(currencies);
        return currencies;
    }

    private void fillRateOfOne(Set<Currency> currencies) {
        currencies.forEach(currency -> currency.setRateOfOne(getRateOfOne(currency)));
    }

    private BigDecimal getRateOfOne(Currency currency) {
        return currency.getScale() == 1
                ? currency.getRate()
                : currency.getRate().divide(BigDecimal.valueOf(currency.getScale()), 4, RoundingMode.HALF_UP);
    }

    protected abstract Set<Currency> load();
}
