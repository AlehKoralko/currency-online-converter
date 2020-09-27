package com.korolko.converter.loader;

import com.korolko.converter.service.Currency;

import java.util.Collection;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
@FunctionalInterface
public interface CurrencyLoader {

    Collection<Currency> load();
}
