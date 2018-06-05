package com.example.kamil.currencycalcforolderpeople;

import java.util.ArrayList;

public class AllCurrencies {
    ArrayList<CurrenciesRowItem> list;


    public AllCurrencies() {
        list = createList();
    }

    private ArrayList<CurrenciesRowItem> createList() {
        ArrayList<CurrenciesRowItem> foobar = new ArrayList<CurrenciesRowItem>();
        foobar.add(new CurrenciesRowItem(R.drawable.flag_bat, "THB", "bat (Tajlandia)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_usd, "USD", "dolar amerykański"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_aud, "AUD", "dolar australijski"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_hkd, "HKD", "dolar Hongkongu"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_cad, "CAD", "dolar kanadyjski"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_nzd, "NZD", "dolar nowozelandzki"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_sgd, "SGD", "dolar singapurski"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_eur, "EUR", "euro"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_huf, "HUF", "forint (Węgry)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_chf, "CHF", "frank szwajcarski"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_gbp, "GBP", "funt szterling"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_uah, "UAH", "hrywna (Ukraina)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_jpy, "JPY", "jen (Japonia)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_czk, "CZK", "korona czeska"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_dkk, "DKK", "korona duńska"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_isk, "ISK", "korona islandzka"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_nok, "NOK", "korona norweska"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_sek, "SEK", "korona szwedzka"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_hrk, "HRK", "kuna (Chorwacja)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_ron, "RON", "lej rumuński"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_bgn, "BGN", "lew (Bułgaria)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_try, "TRY", "lira turecka"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_ils, "ILS", "nowy izraelski szekel"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_clp, "CLP", "peso chilijskie"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_php, "PHP", "peso filipińskie"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_mxn, "MXN", "peso meksykańskie"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_zar, "ZAR", "rand (RPA)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_brl, "BRL", "real (Brazylia)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_myr, "MYR", "ringgit (Malezja)"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_rub, "RUB", "rubel rosyjski"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_idr, "IDR", "rupia indonezyjska"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_inr, "INR", "rupia indyjska"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_krw, "KRW", "won południowokoreański"));
        foobar.add(new CurrenciesRowItem(R.drawable.flag_hkd, "CNY", "yuan renminbi (Chiny)"));

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

    public CurrenciesRowItem getItem(String shortcut)
    {
        if (list == null)
        {
            list = createList();
        }
        for(CurrenciesRowItem item : list)
        {
            if(item.getShortcut() == shortcut)
                return item;
        }
        return null;
    }

}
