package com.korolko.converter.repository;

import com.google.gson.Gson;
import com.korolko.converter.entity.Currency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Component
class APIProvider {

    private List<CurrencyDTO> currencyDTOs;

    private CurrencyConverter converter;

    private final String nationalBankUrl = "http://www.nbrb.by/API/ExRates/Rates?Periodicity=0";

    private Logger logger = LoggerFactory.getLogger(APIProvider.class);

    private HttpURLConnection connection;

    @Autowired
    public APIProvider(CurrencyConverter converter) throws IOException {
        openConnection();
        currencyDTOs = initCurrenciesFromJson();
        this.converter = converter;
    }

    List<Currency> getAllCurrencies() {
        return converter.convertAll(currencyDTOs);
    }

    private void openConnection() throws IOException {
        try {
            URL url = new URL(nationalBankUrl);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10 * 1000);
            connection.setDoOutput(true);
            connection.connect();
            logger.info(connection.getContent().toString());

        } catch (IOException e) {
            logger.error("Failed to open connection. - " + e);
            e.printStackTrace();
            throw new IOException();
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
}
