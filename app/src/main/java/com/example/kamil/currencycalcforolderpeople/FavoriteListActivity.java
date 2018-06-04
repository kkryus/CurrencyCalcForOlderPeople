package com.example.kamil.currencycalcforolderpeople;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteListActivity extends AppCompatActivity {
    private ListView favoriteListView;
    private Button saveButton, cancelButton;
    private Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);
        ctx = this;

        favoriteListView = (ListView) findViewById(R.id.favoriteListView);
        AllCurrencies allCurrencies = new AllCurrencies();
        ArrayList<CurrenciesRowItem> tmpCurrencies = allCurrencies.getList();
        ArrayList<FavoriteChooseRowItem> tmp = new ArrayList<>();
        for(CurrenciesRowItem item : tmpCurrencies)
        {
            tmp.add(new FavoriteChooseRowItem(item.getImageDrawable(), item.getShortcut(), item.getFullCurrency()));
        }
        favoriteListView.setAdapter(new FavoriteChooseListAdapter(this, tmp));
        favoriteListView.setOnItemClickListener(listViewOnItemClickListener);
        favoriteListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        FileHandling fh = new FileHandling(ctx);
        String foobar = fh.readFavoritesFromFile();
        String[] foobar2 = foobar.split(",");
        for(String item : foobar2)
        {
            FavoriteChooseListAdapter.mCheckedState[Integer.parseInt(item)] = true;
        }

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonOnClickListener);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonOnClickListener);
    }


    View.OnClickListener saveButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String favorites = "";

            for(int j = 0;j<FavoriteChooseListAdapter.mCheckedState.length;j++)
            {
                if(FavoriteChooseListAdapter.mCheckedState[j])
                    favorites+=j+",";
            }
            FileHandling fh = new FileHandling(ctx);
            fh.saveStringToFile(favorites, "favorites");
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    };

    View.OnClickListener cancelButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    AdapterView.OnItemClickListener listViewOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            FavoriteChooseListAdapter.mCheckedState[i] = !FavoriteChooseListAdapter.mCheckedState[i];
            CheckBox chkTxt = (CheckBox) view.findViewById(R.id.checkBox);
            chkTxt.setChecked(FavoriteChooseListAdapter.mCheckedState[i]);
        }
    };
}
