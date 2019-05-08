package com.korolko.converter.repository;

import com.korolko.converter.domain.Currency;

import java.io.Serializable;

class CurrencyDTO implements Serializable {

    private String Cur_ID;
    private String Cur_Abbreviation;
    private String Cur_Scale;
    private String Cur_Name;
    private String Cur_OfficialRate;

    public CurrencyDTO() {
    }

    public CurrencyDTO(String cur_ID, String cur_Abbreviation, String cur_Scale,
                       String cur_Name, String cur_OfficialRate) {
        Cur_ID = cur_ID;
        Cur_Abbreviation = cur_Abbreviation;
        Cur_Scale = cur_Scale;
        Cur_Name = cur_Name;
        Cur_OfficialRate = cur_OfficialRate;
    }

    Currency convertToBusinessCurrency() {
        Currency businessCurrency = new Currency();

        businessCurrency.setId(Long.valueOf(this.getCur_ID()));
        businessCurrency.setAbbreviation(this.getCur_Abbreviation());
        businessCurrency.setScale(Integer.valueOf(this.getCur_Scale()));
        businessCurrency.setName(this.getCur_Name());
        businessCurrency.setRate(Double.valueOf(this.getCur_OfficialRate()));

        return businessCurrency;
    }

    public String getCur_ID() {
        return Cur_ID;
    }

    public void setCur_ID(String cur_ID) {
        Cur_ID = cur_ID;
    }

    public String getCur_Abbreviation() {
        return Cur_Abbreviation;
    }

    public void setCur_Abbreviation(String cur_Abbreviation) {
        Cur_Abbreviation = cur_Abbreviation;
    }

    public String getCur_Scale() {
        return Cur_Scale;
    }

    public void setCur_Scale(String cur_Scale) {
        Cur_Scale = cur_Scale;
    }

    public String getCur_Name() {
        return Cur_Name;
    }

    public void setCur_Name(String cur_Name) {
        Cur_Name = cur_Name;
    }

    public String getCur_OfficialRate() {
        return Cur_OfficialRate;
    }

    public void setCur_OfficialRate(String cur_OfficialRate) {
        Cur_OfficialRate = cur_OfficialRate;
    }

    @Override
    public String toString() {
        return "[CurrencyDTO{" +
                "Cur_ID=" + Cur_ID +
                ", Cur_Abbreviation='" + Cur_Abbreviation + '\'' +
                ", Cur_Name='" + Cur_Name + '\'' +
                "}]";
    }
}
