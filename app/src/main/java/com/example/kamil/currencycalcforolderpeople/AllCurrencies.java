package com.example.kamil.currencycalcforolderpeople;

import java.util.ArrayList;

public class AllCurrencies {
    ArrayList<CurrenciesRowItem> list;


    public AllCurrencies() {
        list = createList();
    }

    private ArrayList<CurrenciesRowItem> createList() {
        ArrayList<CurrenciesRowItem> foobar = new ArrayList<CurrenciesRowItem>();
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "THB", "bat (Tajlandia)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "USD", "dolar amerykański"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "AUD", "dolar australijski"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "HKD", "dolar Hongkongu"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "CAD", "dolar kanadyjski"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "NZD", "dolar nowozelandzki"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "SGD", "dolar singapurski"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "EUR", "euro"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "HUF", "forint (Węgry)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "CHF", "frank szwajcarski"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "GBP", "funt szterling"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "UAH", "hrywna (Ukraina)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "JPY", "jen (Japonia)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "CZK", "korona czeska"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "DKK", "korona duńska"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "ISK", "korona islandzka"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "NOK", "korona norweska"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "SEK", "korona szwedzka"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "HRK", "kuna (Chorwacja)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "RON", "lej rumuński"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "BGN", "lew (Bułgaria)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "TRY", "lira turecka"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "ILS", "nowy izraelski szekel"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "CLP", "peso chilijskie"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "PHP", "peso filipińskie"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "MXN", "peso meksykańskie"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "ZAR", "rand (RPA)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "BRL", "real (Brazylia)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "MYR", "ringgit (Malezja)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "RUB", "rubel rosyjski"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "IDR", "rupia indonezyjska"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "INR", "rupia indyjska"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "KRW", "won południowokoreański"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "CNY", "yuan renminbi (Chiny)"));
        foobar.add(new CurrenciesRowItem(R.drawable.poland_flag, "XDR", "SDR (MFW)"));

        return foobar;
    }

    public ArrayList<CurrenciesRowItem> getList() {
        return list;
    }

    public CurrenciesRowItem getItem(int index) {
        if (list == null)
            list = createList();
        return list.get(index);
    }

}
