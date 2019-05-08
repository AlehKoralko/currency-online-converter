package com.korolko.converter.repository;

import com.google.gson.Gson;
import com.korolko.converter.domain.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class APIProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(APIProvider.class);

    private List<CurrencyDTO> currencyDTOs;
    private CurrencyConverter converter;
    private String currencyApiUrl;
    private HttpURLConnection connection;

    public APIProvider(CurrencyConverter converter, String currencyApiUrl) {
        this.currencyApiUrl = currencyApiUrl;
        this.converter = converter;
        openConnection();
        currencyDTOs = initCurrenciesFromJson();
    }

    List<Currency> getAllCurrencies() {
        List<Currency> currencies = converter.convertAll(currencyDTOs);

        //This is necessary because the National Bank doesn't provide
        //data about BYN currency
        currencies.add(getBYNCurrency());

        return currencies.stream()
                .sorted(Comparator.comparing(Currency::getName))
                .collect(Collectors.toList());
    }

    private void openConnection() {
        try {
            URL url = new URL(currencyApiUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10 * 1000);
            connection.setDoOutput(true);
            connection.connect();
        } catch (IOException e) {
            LOGGER.error("Failed to open connection. - " + e);
            e.printStackTrace();
        }
    }

    private List<CurrencyDTO> initCurrenciesFromJson() {
        Gson gson = new Gson();
        CurrencyDTO[] currencyDTOs = gson.fromJson(getJsonFromUrl(), CurrencyDTO[].class);
        return Arrays.asList(currencyDTOs);
    }

    private String getJsonFromUrl() {
        StringBuilder stringBuilder = null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            stringBuilder = new StringBuilder();
            reader.lines().forEach(stringBuilder::append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder != null ? stringBuilder.toString() : "";
    }

    private Currency getBYNCurrency() {
        return new Currency(1L,
                "Белорусский рубль",
                "BYN",
                1d ,
                1);
    }
}
