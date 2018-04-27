package com.example.kamil.currencycalcforolderpeople;


import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class ViewPresenter {

    private Context context;
    FileHandling fileHandling;

    public ViewPresenter(Context ctx) {
        this.context = ctx;
    }

    public String getJSON() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            if (isNetworkAvailable()) {
                URL url = new URL(context.getString(R.string.urlLink));
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));

                String buffer = "";
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer += line;
                }
                return buffer;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void saveToFile(String content) {
        if (fileHandling == null) {
            fileHandling = new FileHandling(context);
        }
        fileHandling.saveToFile(content);
    }

    public String readFromFile() {
        if (fileHandling == null) {
            fileHandling = new FileHandling(context);
        }
        return fileHandling.readFromFile();
    }

    public void changeLanguage() {
        String languageToLoad = Settings.language; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
