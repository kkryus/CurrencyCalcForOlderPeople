package com.example.kamil.currencycalcforolderpeople;

public class CurrenciesRowItem {
        private int imageDrawable;
        private String shortcut;
        private String fullCurrency;

        public CurrenciesRowItem() {

        }

        public CurrenciesRowItem(int imageDrawable, String shortcut, String fullCurrency) {
            this.imageDrawable = imageDrawable;
            this.shortcut = shortcut;
            this.fullCurrency = fullCurrency;
        }

        public int getImageDrawable() {
            return imageDrawable;
        }

        public String getShortcut() {
            return shortcut;
        }

        public String getFullCurrency() {
            return fullCurrency;
        }
}


