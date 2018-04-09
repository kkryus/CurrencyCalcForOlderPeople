package com.example.kamil.currencycalcforolderpeople;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CurrenciesActivity extends AppCompatActivity {
    ListView currenciesList;
    AllCurrencies allCurrencies;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currencies);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        currenciesList = (ListView) findViewById(R.id.currenciesListView);
        allCurrencies = new AllCurrencies();

        currenciesList.setAdapter(new CurrenciesListAdapter(this, allCurrencies.getList()));

        currenciesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setResult(Activity.RESULT_OK,
                        new Intent().putExtra("shortcut", allCurrencies.getItem(i).getShortcut()));
                finish();
            }
        });
    }
}
