package com.korolko.converter.controller;

import com.korolko.converter.entity.Currency;
import com.korolko.converter.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class CurrencyRestController {

    private final CurrencyService currencyService;

    private final Logger logger = LoggerFactory.getLogger(CurrencyRestController.class);

    @Autowired
    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Currency>> getAllCurrencies() {
        List<Currency> currencies = currencyService.getAll();

        if (currencies == null) {
            logger.info("Currencies are not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Get all currencies.");
        return new ResponseEntity<>(currencyService.getAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Currency> getCurrencyById(@PathVariable("id") long id) {
        Optional<Currency> currency = currencyService.getById(id);

        if (!currency.isPresent()) {
            logger.info("Currency with id - " + id + " not found.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Get currency with id - " + id);
        return new ResponseEntity<>(currency.get(), HttpStatus.OK);
    }

    @GetMapping(value = "/convert", params = {"current", "target", "amount"},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<BigDecimal> convert(@RequestParam("current") String currentAbbr,
                                              @RequestParam("target") String targetAbbr,
                                              @RequestParam("amount") double amount) {
        BigDecimal convertValue = currencyService.convert(currentAbbr, targetAbbr, amount);

        return new ResponseEntity<>(convertValue, HttpStatus.OK);
    }
}
