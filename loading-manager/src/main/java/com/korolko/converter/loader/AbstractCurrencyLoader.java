package com.korolko.converter.loader;

import com.korolko.converter.service.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
public abstract class AbstractCurrencyLoader implements CurrencyLoader {

    @Override
    public final Collection<Currency> load() {
        Collection<Currency> currencies = doLoad();
        fillRateOfOne(currencies);
        return currencies;
    }

    private void fillRateOfOne(Collection<Currency> currencies) {
        currencies.forEach(currency -> currency.setRateOfOne(getRateOfOne(currency)));
    }

    private BigDecimal getRateOfOne(Currency currency) {
        return currency.getScale() == 1
                ? currency.getRate()
                : currency.getRate().divide(BigDecimal.valueOf(currency.getScale()), 4, RoundingMode.HALF_UP);
    }

    protected abstract Collection<Currency> doLoad();
}
