package com.example.kamil.currencycalcforolderpeople;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class FavoriteListActivity extends AppCompatActivity {
    private ListView favoriteListView;
    private Button saveButton, cancelButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_list);

        favoriteListView = (ListView) findViewById(R.id.favoriteListView);
        ArrayList<FavoriteChooseRowItem> tmp = new ArrayList<>();
        tmp.add(new FavoriteChooseRowItem(R.drawable.poland_flag, "PLN", "polski złoty"));
        tmp.add(new FavoriteChooseRowItem(R.drawable.poland_flag, "PLN", "polski złoty"));
        tmp.add(new FavoriteChooseRowItem(R.drawable.poland_flag, "PLN", "polski złoty"));
        tmp.add(new FavoriteChooseRowItem(R.drawable.poland_flag, "PLN", "polski złoty"));
        tmp.add(new FavoriteChooseRowItem(R.drawable.poland_flag, "PLN", "polski złoty"));

        favoriteListView.setAdapter(new FavoriteChooseListAdapter(this, tmp));
        favoriteListView.setOnItemClickListener(listViewOnItemClickListener);
        favoriteListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonOnClickListener);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(cancelButtonOnClickListener);
    }


    View.OnClickListener saveButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int count = favoriteListView.getCount();
            for (int i = 0; i < count; i++) {
                ViewGroup row = (ViewGroup) favoriteListView.getChildAt(i);
                if (row.getBackground() == null || ((ColorDrawable) row.getBackground()).getColor() == Color.WHITE) {

                } else {
                    Log.e("foobar", String.valueOf(i));
                }
            }
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
            if (adapterView.getChildAt(i).getBackground() == null || ((ColorDrawable) adapterView.getChildAt(i).getBackground()).getColor() == Color.WHITE) {
                adapterView.getChildAt(i).setBackgroundColor(Color.parseColor("#ff6600"));
            } else {
                adapterView.getChildAt(i).setBackgroundColor(Color.WHITE);
            }
        }
    };
}
