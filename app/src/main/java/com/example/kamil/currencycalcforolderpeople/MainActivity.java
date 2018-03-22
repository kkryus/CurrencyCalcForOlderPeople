package com.example.kamil.currencycalcforolderpeople;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.Console;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView favoriteListView;
    private Button firstShortcutButton, secondShortcutButton;
    private EditText inputEditText, outputEditText;
    private Button settingsButton, updateButton, customButton;
    private Context ctx;
    private boolean didConnect = true;
    private DrawerLayout navBar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        loadControls();


        ArrayList<FavoriteRowItem> tmp = new ArrayList<>();
        tmp.add(new FavoriteRowItem(R.drawable.poland_flag, "PLN", "polski złoty", "1"));
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

        setOnClickListeners();
        navigationView.bringToFront();

        setSettingsHeights();
    }

    private void setConnectionAlertHeights(AlertDialog alert) {
        alert.getWindow().getAttributes();

        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize((float) (Defaults.alertTextSize));

        Button buttonNeutral = alert.getButton(Dialog.BUTTON_NEUTRAL);
        buttonNeutral.setTextSize(Defaults.buttonTextSize);
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

    private void setOnClickListeners() {
        settingsButton.setOnClickListener(settingsButtonOnClickListener);
        updateButton.setOnClickListener(updateButtonOnClickListener);
        customButton.setOnClickListener(customButtomOnClickListener);

        navigationView.setNavigationItemSelectedListener(this);
    }

    private void loadControls() {
        navBar = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        favoriteListView = (ListView) findViewById(R.id.favoriteListView);

        firstShortcutButton = (Button) findViewById(R.id.firstShortcutButton);
        secondShortcutButton = (Button) findViewById(R.id.secondShortcutButton);

        inputEditText = (EditText) findViewById(R.id.inputEditText);
        outputEditText = (EditText) findViewById(R.id.outputEditText);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        customButton = (Button) findViewById(R.id.customButton);
    }

    private String getJSONCurrencies() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        try {
            if (isNetworkAvailable()) {
                URL url = new URL(getString(R.string.urlLink));
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

    private void saveToFile(String content) {
        FileOutputStream outputStream;
        try {
            outputStream = openFileOutput(getString(R.string.currenciesFileName), Context.MODE_PRIVATE);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAlertSettings(AlertDialog.Builder builder) {
        builder.setTitle(getString(R.string.connectionTitle));
        builder.setMessage(getString(R.string.connectionMessage));
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setIcon(android.R.drawable.ic_dialog_alert);
    }

    private void readFromFile() {
        try {
            FileInputStream fis = ctx.openFileInput(getString(R.string.currenciesFileName));
            ;
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            Log.e("Response: ", sb.toString());
        } catch (Exception e) {
            Log.e("foo", e.getMessage());
        }
    }

    View.OnClickListener settingsButtonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            navBar.openDrawer(Gravity.LEFT);
        }
    };

    View.OnClickListener updateButtonOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (Settings.internetConnection == "all") {
                            if (isNetworkAvailable()) {
                                String buffer = getJSONCurrencies();

                                saveToFile(buffer);
                                didConnect = true;
                            } else {
                                didConnect = false;
                            }
                        } else {
                            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                            if (mWifi.isConnected()) {
                                String buffer = getJSONCurrencies();

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
                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ctx);
                }
                setAlertSettings(builder);

                AlertDialog alert = builder.create();
                alert.show();
                setConnectionAlertHeights(alert);

                didConnect = true;
            }
        }
    };

    View.OnClickListener customButtomOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        }

    };

    @Override
    public void onBackPressed() {
        if (navBar.isDrawerOpen(Gravity.LEFT)) {
            navBar.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.number_precision: {
                CharSequence numberPrecisions[] = new CharSequence[]{"0.1", "0.12", "0.123", "0.1234"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.numberPrecision));
                builder.setItems(numberPrecisions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Settings.numberPrecision = which + 1;
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }

            case R.id.language: {
                CharSequence numberPrecisions[] = new CharSequence[]{"English", "Polski"};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getString(R.string.chooseLanguage));
                builder.setItems(numberPrecisions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Settings.language = "en";
                        } else {
                            Settings.language = "pl";
                        }

                        changeLanguage();
                        Intent myIntent = new Intent(ctx, MainActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            }

            case R.id.connection: {
                CharSequence numberPrecisions[] = new CharSequence[]{getString(R.string.justWifi), getString(R.string.all)};

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.connection));
                builder.setItems(numberPrecisions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Settings.internetConnection = "wifi";
                        } else {
                            Settings.internetConnection = "all";
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

                break;
            }

            case R.id.informations: {
                AlertDialog.Builder builder;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(ctx, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(ctx);
                }
                setInformationSettings(builder);

                AlertDialog alert = builder.create();
                alert.show();
                setConnectionAlertHeights(alert);

                break;
            }

        }
        //navBar.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setInformationSettings(AlertDialog.Builder builder) {
        builder.setTitle(getString(R.string.information));
        builder.setMessage(getString(R.string.information_message));
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setIcon(android.R.drawable.ic_dialog_info);
    }

    private void changeLanguage() {
        String languageToLoad = Settings.language; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());
    }
}
