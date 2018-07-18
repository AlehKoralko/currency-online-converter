package com.korolko.converter.service;

import com.korolko.converter.entity.Currency;
import com.korolko.converter.repository.CurrencyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository currencyRepo;

    private Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepo) {
        this.currencyRepo = currencyRepo;
    }

    @Override
    public Optional<Currency> getById(long id) {
        return currencyRepo.findById(id);
    }

    @Override
    public Optional<Currency> getByName(String name) {
        return currencyRepo.findByName(name);
    }

    @Override
    public Optional<Currency> getByAbbreviation(String abbreviation) {
        return currencyRepo.findByAbbreviation(abbreviation);
    }

    @Override
    public List<Currency> getAll() {
        return currencyRepo.findAll();
    }

    @Override
    public double convert(Currency currentCurrency, Currency targetCurrency, double amount) {
        logger.info("Convert %d %d to %d", amount, currentCurrency, targetCurrency);
        return currentCurrency.convertTo(targetCurrency, amount);
    }
}
