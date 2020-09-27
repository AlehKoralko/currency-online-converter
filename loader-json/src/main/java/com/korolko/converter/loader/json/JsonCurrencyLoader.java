package com.korolko.converter.loader.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korolko.converter.loader.AbstractCurrencyLoader;
import com.korolko.converter.service.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Date: 08.09.2020
 *
 * @author Aleh Karalko
 */
class JsonCurrencyLoader extends AbstractCurrencyLoader implements ResourceLoaderAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonCurrencyLoader.class);

    private final String currencyLocationPattern;
    private final ObjectMapper objectMapper;
    private ResourcePatternResolver resourceResolver;

    JsonCurrencyLoader(String currencyLocationPattern) {
        this.currencyLocationPattern = currencyLocationPattern;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected Collection<Currency> doLoad() {
        try {
            Resource[] resources = resourceResolver.getResources(currencyLocationPattern);
            return Arrays.stream(resources)
                    .map(this::readCurrencyFromResource)
                    .collect(Collectors.toSet());
        } catch (IOException e) {
            LOGGER.warn("Failed to load currencies from {}", currencyLocationPattern);
        }
        return Collections.emptySet();
    }

    private Currency readCurrencyFromResource(Resource resource) {
        try {
            return objectMapper.readValue(resource.getURL(), Currency.class);
        } catch (IOException e) {
            String errorMessage = MessageFormat.format("Failed to load currency from resource: {0}", resource);
            throw new RuntimeException(errorMessage, e);
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    }
}
