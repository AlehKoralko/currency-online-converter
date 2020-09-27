package com.korolko.converter.service;

import java.util.Collection;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
@FunctionalInterface
public interface CurrencyLoadingManager {

    Collection<Currency> load();
}
