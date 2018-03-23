package com.example.kamil.currencycalcforolderpeople;

public class CurrencyObject {
    private String fullCurrency;
    private String shortcutCurrency;
    private float value;

    public CurrencyObject(String currency, String shortcut, float value) {
        this.fullCurrency = currency;
        this.shortcutCurrency = shortcut;
        this.value = value;
    }

    public String getFullCurrency() {
        return fullCurrency;
    }

    public String getShortcutCurrency() {
        return shortcutCurrency;
    }

    public float getValue() {
        return value;
    }
}
