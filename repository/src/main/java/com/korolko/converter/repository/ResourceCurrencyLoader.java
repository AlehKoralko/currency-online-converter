package com.korolko.converter.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korolko.converter.domain.Currency;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class ResourceCurrencyLoader implements CurrencyLoader, ResourceLoaderAware {

    private ResourcePatternResolver resourcePatternResolver;
    private String currencyResourceLocationPattern;
    private ObjectMapper objectMapper = new ObjectMapper();

    public ResourceCurrencyLoader(String currencyResourceLocationPattern) {
        this.currencyResourceLocationPattern = currencyResourceLocationPattern;
    }

    @Override
    public Set<Currency> loadCurrencies() throws Exception {
        Resource[] resources = resourcePatternResolver.getResources(currencyResourceLocationPattern);
        return Arrays.stream(resources)
                .map(this::readCurrencyFromResource)
                .collect(Collectors.toCollection(TreeSet::new));
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

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
