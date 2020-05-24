package com.korolko.converter.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korolko.converter.domain.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ResourceCurrencyLoader extends AbstractCurrencyLoader implements ResourceLoaderAware, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceCurrencyLoader.class);

    private ResourcePatternResolver resourcePatternResolver;
    private String currencyLocationPattern;
    private ObjectMapper objectMapper = new ObjectMapper();

    private Set<Currency> currencies;

    public ResourceCurrencyLoader(String currencyLocationPattern) {
        this.currencyLocationPattern = currencyLocationPattern;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public Set<Currency> load() {
        try {
            Resource[] resources = resourcePatternResolver.getResources(currencyLocationPattern);
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
        this.resourcePatternResolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
    }
}
