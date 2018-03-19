package com.example.kamil.currencycalcforolderpeople;

public class FavoriteRowItem {
    private int imageDrawable;
    private String shortcut;
    private String fullCurrency;
    private String value;

    public FavoriteRowItem()
    {

    }

    public FavoriteRowItem(int imageDrawable, String shortcut, String fullCurrency, String value)
    {
        this.imageDrawable = imageDrawable;
        this.shortcut = shortcut;
        this.fullCurrency = fullCurrency;
        this.value = value;
    }

    public int getImageDrawable()
    {
        return imageDrawable;
    }
    public String getShortcut()
    {
        return shortcut;
    }
    public String getFullCurrency()
    {
        return fullCurrency;
    }
    public String getValue()
    {
        return value;
    }
}
