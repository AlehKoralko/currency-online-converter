package com.korolko.converter.controller;

import com.korolko.converter.service.ConversionContainer;
import com.korolko.converter.service.Currency;
import com.korolko.converter.service.CurrencyConverter;
import com.korolko.converter.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collection;

@RestController
@RequestMapping(value = "/api")
public class CurrencyRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyRestController.class);

    private final CurrencyConverter converter;
    private final CurrencyService currencyService;

    @Autowired
    public CurrencyRestController(CurrencyConverter converter, CurrencyService currencyService) {
        this.converter = converter;
        this.currencyService = currencyService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<Currency>> getAllCurrencies() {
        Collection<Currency> currencies = currencyService.getAll();

        if (currencies.isEmpty()) {
            LOGGER.info("Currencies are not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(currencies, HttpStatus.OK);
    }

    @GetMapping(value = "/convert", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BigDecimal> convert(@RequestParam("current") String currentAbbr,
                                              @RequestParam("target") String targetAbbr,
                                              @RequestParam("amount") BigDecimal amount) {
        ConversionContainer container = new ConversionContainer(currentAbbr, targetAbbr, amount);
        BigDecimal convertedValue = converter.convert(container);
        return new ResponseEntity<>(convertedValue, HttpStatus.OK);
    }
}
