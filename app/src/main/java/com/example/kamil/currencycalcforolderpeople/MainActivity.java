package com.example.kamil.currencycalcforolderpeople;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView favoriteListView;
    private Button firstShortcutButton, secondShortcutButton;
    private EditText inputEditText, outputEditText;
    private Button settingsButton, updateButton, customButton;
    private ImageView firstFlagButton, secondFlagButton;
    private ImageButton replaceButton, clearButton;
    private Context ctx;
    private boolean didConnect = true, firstTimeInput = true;
    private DrawerLayout navBar;
    private NavigationView navigationView;
    private boolean which = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        firstFlagButton = (ImageView) findViewById(R.id.firstFlagButton);
        secondFlagButton = (ImageView) findViewById(R.id.secondFlagButton);

        replaceButton = (ImageButton) findViewById(R.id.replaceButton);
        clearButton = (ImageButton) findViewById(R.id.cleanButton);

        inputEditText = (EditText) findViewById(R.id.inputEditText);
        outputEditText = (EditText) findViewById(R.id.outputEditText);

        settingsButton = (Button) findViewById(R.id.settingsButton);
        updateButton = (Button) findViewById(R.id.updateButton);
        customButton = (Button) findViewById(R.id.customButton);
    }

    private void setListeners() {
        firstFlagButton.setOnTouchListener(currencyOnTouchListener);
        secondFlagButton.setOnTouchListener(currencyOnTouchListener);
        firstShortcutButton.setOnTouchListener(currencyOnTouchListener);
        secondShortcutButton.setOnTouchListener(currencyOnTouchListener);
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

    private String readFromFile() {
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
            return sb.toString();
        } catch (Exception e) {
            Log.e("foo", e.getMessage());
        }
        return null;
    }

    View.OnTouchListener currencyOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                if (view.getId() == R.id.firstFlagButton || view.getId() == R.id.firstShortcutButton) {
                    which = false;
                } else {
                    which = true;
                }
                Intent intent = new Intent(ctx, CurrenciesActivity.class);
                startActivityForResult(intent, 0);
                //startActivity(intent);
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String shortcut = data.getStringExtra("shortcut");
            if (!which) {
                firstShortcutButton.setText(shortcut);
                //firstFlagButton
            } else {
                secondShortcutButton.setText(shortcut);
                //secondFlagButton
            }
        }
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
            inputEditText.setText(inputEditText.getText());
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

    TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String json = readFromFile();

            if (editable.toString().contains(".")) {
                inputEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
            } else {
                inputEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789."));
            }

            try {
                JSONArray arr = new JSONArray(json);
                JSONObject jsonobject = arr.getJSONObject(0);
                JSONArray foo = jsonobject.getJSONArray("rates");
                if (!firstShortcutButton.getText().toString().equals("PLN") && !inputEditText.getText().toString().equals("")) {
                    double value = 0;
                    for (int i = 0; i < foo.length(); i++) {
                        JSONObject tmp = foo.getJSONObject(i);
                        if (tmp.getString("code").equals(firstShortcutButton.getText().toString())) {
                            double id = tmp.getDouble("mid");
                            value = Double.parseDouble(inputEditText.getText().toString()) * id;

                            outputEditText.setText(String.format("%." + Settings.numberPrecision + "f", value));
                            break;
                        }
                    }
                    for (int i = 0; i < foo.length(); i++) {
                        JSONObject tmp = foo.getJSONObject(i);
                        if (tmp.getString("code").equals(secondShortcutButton.getText().toString())) {
                            double id = tmp.getDouble("mid");
                            value = value / id;
                            outputEditText.setText(String.format("%." + Settings.numberPrecision + "f", value));
                            break;
                        }
                    }

                }
                if (firstShortcutButton.getText().toString().equals("PLN") && !inputEditText.getText().toString().equals("")) {
                    for (int i = 0; i < foo.length(); i++) {
                        JSONObject tmp = foo.getJSONObject(i);
                        if (tmp.getString("code").equals(secondShortcutButton.getText().toString())) {
                            double id = tmp.getDouble("mid");
                            double value = Double.parseDouble(inputEditText.getText().toString()) / id;
                            outputEditText.setText(String.format("%." + Settings.numberPrecision + "f", value));
                            break;
                        }
                    }
                }
                if (firstShortcutButton.getText().toString().equals(secondShortcutButton.getText().toString())) {
                    outputEditText.setText(inputEditText.getText());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    };

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

    AdapterView.OnItemClickListener favoriteOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            ImageView flag = (ImageView) view.findViewById(R.id.imageView);
            TextView shortcut = (TextView) view.findViewById(R.id.shortcutView);
            firstFlagButton.setImageDrawable(flag.getDrawable());
            firstShortcutButton.setText(shortcut.getText());
            inputEditText.setText(inputEditText.getText());
        }
    };

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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
