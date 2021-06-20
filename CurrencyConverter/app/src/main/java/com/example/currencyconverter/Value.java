package com.example.currencyconverter;

public class Value {
    String currencyFrom;
    String currencyTo;
    String key;

    public Value(String currencyFrom, String currencyTo, String key) {
        this.currencyFrom = currencyFrom;
        this.currencyTo = currencyTo;
        this.key = key;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
