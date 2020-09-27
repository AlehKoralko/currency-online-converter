package com.korolko.converter.loader;

import com.korolko.converter.service.Currency;
import com.korolko.converter.service.CurrencyLoadingManager;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
public class DefaultCurrencyLoadingManager implements CurrencyLoadingManager {

    private final List<CurrencyLoader> loaders;

    public DefaultCurrencyLoadingManager(List<CurrencyLoader> loaders) {
        this.loaders = loaders;
    }

    @Override
    public Collection<Currency> load() {
        return loaders.stream()
                .flatMap(loader -> loader.load().stream())
                .collect(Collectors.toCollection(HashSet::new));
    }
}
