package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;

public class Currencies {
    List<CurrencyData> currencyDataList;

    public Currencies(List<CurrencyData> currencyDataList) {
        this.currencyDataList = currencyDataList;
    }

    public Currencies() {
    }

    public List<CurrencyData> getCurrencyDataList() {
        return currencyDataList;
    }

    public void setCurrencyDataList(List<CurrencyData> currencyDataList) {
        this.currencyDataList = currencyDataList;
    }

}
