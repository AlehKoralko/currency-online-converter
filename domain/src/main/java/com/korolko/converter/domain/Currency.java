package com.korolko.converter.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Currency {

    private long id;
    private String name;
    private String abbreviation;
    private double rate;
    private int scale;

    public Currency() {}

    public Currency(long id, String name, String abbreviation, double rate, int scale) {
        this.id = id;
        this.name = name;
        this.abbreviation = abbreviation;
        this.rate = rate;
        this.scale = scale;
    }

    public BigDecimal convertTo(Currency targetCurrency, double amount) {
        BigDecimal result = BigDecimal
                .valueOf(amount / (targetCurrency.getRateAgainstScale() / this.getRateAgainstScale()));

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    private double getRateAgainstScale() {
        return this.rate / this.scale;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbreviation='" + abbreviation + '\'' +
                ", rate=" + rate +
                ", scale=" + scale +
                "}]";
    }
}
