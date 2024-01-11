package com.aditya.lokalapp.Model;

public class CryptoInfo {
    private String symbol;
    private String name;
    private String name_full;
    private String max_supply;
    private String icon_url;

    public CryptoInfo(String symbol, String name, String name_full, String max_supply, String icon_url) {
        this.symbol = symbol;
        this.name = name;
        this.name_full = name_full;
        this.max_supply = max_supply;
        this.icon_url = icon_url;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_full() {
        return name_full;
    }

    public void setName_full(String name_full) {
        this.name_full = name_full;
    }

    public String getMax_supply() {
        return max_supply;
    }

    public void setMax_supply(String max_supply) {
        this.max_supply = max_supply;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }
}
