package com.example.kamil.currencycalcforolderpeople;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView favoriteListView;
    private Button firstShortcutButton, secondShortcutButton;
    private EditText inputEditText, outputEditText;
    private Button settingsButton, updateButton, customButton;
    private ImageButton firstFlagButton, secondFlagButton;
    private ImageButton replaceButton, clearButton;
    private Context ctx;
    private boolean didConnect = true, firstTimeInput = true;
    private DrawerLayout navBar;
    private NavigationView navigationView;

    public ViewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ViewPresenter(this);
        ctx = this;
        loadControls();


        ArrayList<FavoriteRowItem> tmp = new ArrayList<>();
        tmp.add(new FavoriteRowItem(R.drawable.flag_euro, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "5000.3746"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "0.3746"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "0.3746"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "0.3746"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));

        favoriteListView.setAdapter(new FavoriteListAdapter(this, tmp));

        setListeners();
        navigationView.bringToFront();

        setSettingsHeights();
    }

    private void loadControls() {
        navBar = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        favoriteListView = (ListView) findViewById(R.id.favoriteListView);

        firstShortcutButton = (Button) findViewById(R.id.firstShortcutButton);
        secondShortcutButton = (Button) findViewById(R.id.secondShortcutButton);

        firstFlagButton = (ImageButton) findViewById(R.id.firstFlagButton);
        secondFlagButton = (ImageButton) findViewById(R.id.secondFlagButton);

        replaceButton = (ImageButton) findViewById(R.id.replaceButton);
        clearButton = (ImageButton) findViewById(R.id.cleanButton);

        inputEditText = (EditText) findViewById(R.id.inputEditText);
        outputEditText = (EditText) findViewById(R.id.outputEditText);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        customButton = (Button) findViewById(R.id.customButton);

    }

    private void setListeners() {
        replaceButton.setOnClickListener(replaceButtonOnClickListener);
        inputEditText.addTextChangedListener(inputTextWatcher);
        inputEditText.setOnTouchListener(inputOnTouchListener);
        clearButton.setOnTouchListener(clearButtonOnTouchListener);


        settingsButton.setOnClickListener(settingsButtonOnClickListener);
        updateButton.setOnClickListener(updateButtonOnClickListener);
        customButton.setOnClickListener(customButtomOnClickListener);

        navigationView.setNavigationItemSelectedListener(this);
        favoriteListView.setOnItemClickListener(favoriteOnItemClickListener);
    }

    private void setSettingsHeights() {
        View headerView = navigationView.getHeaderView(0);
        TextView textView = (TextView) headerView.findViewById(R.id.navHeaderTextView);
        textView.setTextSize(Defaults.textViewTextSize);

        Menu menu = (Menu) navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i); //here we are getting our menu item.
            SpannableString s = new SpannableString(item.getTitle()); //get text from our menu item.
            s.setSpan(new RelativeSizeSpan((float) Defaults.settingsItemMenu), 0, s.length(), 0); //here is where we are actually setting the size with a float (proportion).
            item.setTitle(s); //Then just set the menu item to our SpannableString.
        }
    }

    private String getJSON() {
        return presenter.getJSON();
    }

    private void saveToFile(String content) {
        presenter.saveToFile(content);
    }

    View.OnClickListener replaceButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String tmpShortcut = firstShortcutButton.getText().toString();
            firstShortcutButton.setText(secondShortcutButton.getText());
            secondShortcutButton.setText(tmpShortcut);

            Drawable tmpFlag = firstFlagButton.getDrawable();
            firstFlagButton.setImageDrawable(secondFlagButton.getDrawable());
            secondFlagButton.setImageDrawable(tmpFlag);
        }
    };


    View.OnTouchListener inputOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            inputEditText.setText("");
            return false;
        }
    };

    View.OnTouchListener clearButtonOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            inputEditText.setText("1");
            return false;
        }
    };

    TextWatcher inputTextWatcher = new InputTextWatcher(presenter, this);


    View.OnClickListener settingsButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            navBar.openDrawer(Gravity.LEFT);
            InputMethodManager inputMethodManager = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(inputEditText.getWindowToken(), 0);
        }
    };

    View.OnClickListener updateButtonOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Settings.internetConnection == "all") {
                            if (presenter.isNetworkAvailable()) {
                                String buffer = getJSON();

                                saveToFile(buffer);
                                didConnect = true;
                            } else {
                                didConnect = false;
                            }
                        } else {
                            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                            if (mWifi.isConnected()) {
                                String buffer = getJSON();

                                saveToFile(buffer);
                                didConnect = true;
                            } else {
                                didConnect = false;
                            }
                        }
                    } catch (Exception e) {

                    }
                }
            });
            thread.start();

            try {
                thread.join();
            } catch (Exception e) {
            }

            if (!didConnect) {
                AlertDialog alert = SettingsAlertDialogs.getNoConnectionAlertDialog(ctx);
                alert.show();

                didConnect = true;
            }
        }
    };

    View.OnClickListener customButtomOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }

    };

    AdapterView.OnItemClickListener favoriteOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ImageView flag = (ImageView) view.findViewById(R.id.imageView);
            TextView shortcut = (TextView) view.findViewById(R.id.shortcutView);
            firstFlagButton.setImageDrawable(flag.getDrawable());
            firstShortcutButton.setText(shortcut.getText());
        }
    };

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.number_precision: {
                SettingsAlertDialogs.getNumberPrecisionAlertDialog(this).show();
                break;
            }

            case R.id.language: {
                SettingsAlertDialogs.getLanguageAlertDialog(this, presenter).show();
                break;
            }

            case R.id.connection: {
                SettingsAlertDialogs.getConnectionAlertDialog(this).show();
                break;
            }

            case R.id.informations: {
                SettingsAlertDialogs.getInformationAlertDialog(this).show();
                break;
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (navBar.isDrawerOpen(Gravity.LEFT)) {
            navBar.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }
}
