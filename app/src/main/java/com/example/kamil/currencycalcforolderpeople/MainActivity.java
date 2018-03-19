package com.example.kamil.currencycalcforolderpeople;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView favoriteListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteListView = (ListView) findViewById(R.id.favoriteListView);
        ArrayList<FavoriteRowItem> tmp = new ArrayList<>();
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        favoriteListView.setAdapter(new FavoriteListAdapter(this, tmp));
    }
}
