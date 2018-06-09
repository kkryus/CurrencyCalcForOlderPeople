package com.example.kamil.currencycalcforolderpeople;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ListView favoriteListView;
    private Button firstShortcutButton, secondShortcutButton;
    private EditText inputEditText, outputEditText;
    private Button settingsButton, updateButton, customButton;
    private ImageView firstFlagButton, secondFlagButton;
    private ImageButton replaceButton, clearButton;
    private Context ctx;
    private boolean didConnect = true;
    private DrawerLayout navBar;
    private NavigationView navigationView;
    private boolean which = false;

    public ViewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new ViewPresenter(this);
        ctx = this;

        String currenciesFilePath = this.getFilesDir() + "/" + "currencies";

        File fileCurrencies = new File(currenciesFilePath);
        if (!fileCurrencies.exists()) {
            try {
                fileCurrencies.createNewFile();
                saveToFile("[{\"table\":\"A\",\"no\":\"109/A/NBP/2018\",\"effectiveDate\":\"2018-06-07\",\"rates\":\n" +
                        "[{\"currency\":\"bat (Tajlandia)\",\"code\":\"THB\",\"mid\":0.1129},\n" +
                        "{\"currency\":\"dolar amerykański\",\"code\":\"USD\",\"mid\":3.6062},\n" +
                        "{\"currency\":\"dolar australijski\",\"code\":\"AUD\",\"mid\":2.7592},\n" +
                        "{\"currency\":\"dolar Hongkongu\",\"code\":\"HKD\",\"mid\":0.4597},\n" +
                        "{\"currency\":\"dolar kanadyjski\",\"code\":\"CAD\",\"mid\":2.7828},\n" +
                        "{\"currency\":\"dolar nowozelandzki\",\"code\":\"NZD\",\"mid\":2.5414},\n" +
                        "{\"currency\":\"dolar singapurski\",\"code\":\"SGD\",\"mid\":2.7080},\n" +
                        "{\"currency\":\"euro\",\"code\":\"EUR\",\"mid\":4.1634},\n" +
                        "{\"currency\":\"forint (Węgry)\",\"code\":\"HUF\",\"mid\":0.013437},\n" +
                        "{\"currency\":\"frank szwajcarski\",\"code\":\"CHF\",\"mid\":3.6717},\n" +
                        "{\"currency\":\"funt szterling\",\"code\":\"GBP\",\"mid\":4.8542},\n" +
                        "{\"currency\":\"hrywna (Ukraina)\",\"code\":\"UAH\",\"mid\":0.1378},\n" +
                        "{\"currency\":\"jen (Japonia)\",\"code\":\"JPY\",\"mid\":0.032803},\n" +
                        "{\"currency\":\"korona czeska\",\"code\":\"CZK\",\"mid\":0.1663},\n" +
                        "{\"currency\":\"korona duńska\",\"code\":\"DKK\",\"mid\":0.5725},\n" +
                        "{\"currency\":\"korona islandzka\",\"code\":\"ISK\",\"mid\":0.034355},\n" +
                        "{\"currency\":\"korona norweska\",\"code\":\"NOK\",\"mid\":0.4490},\n" +
                        "{\"currency\":\"korona szwedzka\",\"code\":\"SEK\",\"mid\":0.4163},\n" +
                        "{\"currency\":\"kuna (Chorwacja)\",\"code\":\"HRK\",\"mid\":0.5773},\n" +
                        "{\"currency\":\"lej rumuński\",\"code\":\"RON\",\"mid\":0.9155},\n" +
                        "{\"currency\":\"lew (Bułgaria)\",\"code\":\"BGN\",\"mid\":2.1798},\n" +
                        "{\"currency\":\"lira turecka\",\"code\":\"TRY\",\"mid\":0.7888}," +
                        "{\"currency\":\"nowy izraelski szekel\",\"code\":\"ILS\",\"mid\":1.0090}," +
                        "{\"currency\":\"peso chilijskie\",\"code\":\"CLP\",\"mid\":0.005744}," +
                        "{\"currency\":\"peso meksykańskie\",\"code\":\"MXN\",\"mid\":0.1769}," +
                        "{\"currency\":\"piso filipińskie\",\"code\":\"PHP\",\"mid\":0.0687}," +
                        "{\"currency\":\"rand (Republika Południowej Afryki)\",\"code\":\"ZAR\",\"mid\":0.2834}," +
                        "{\"currency\":\"real (Brazylia)\",\"code\":\"BRL\",\"mid\":0.9374}," +
                        "{\"currency\":\"ringgit (Malezja)\",\"code\":\"MYR\",\"mid\":0.9071}," +
                        "{\"currency\":\"rubel rosyjski\",\"code\":\"RUB\",\"mid\":0.0582}," +
                        "{\"currency\":\"rupia indonezyjska\",\"code\":\"IDR\",\"mid\":0.00026}," +
                        "{\"currency\":\"rupia indyjska\",\"code\":\"INR\",\"mid\":0.053797}," +
                        "{\"currency\":\"won południowokoreański\",\"code\":\"KRW\",\"mid\":0.003372}," +
                        "{\"currency\":\"yuan renminbi (Chiny)\",\"code\":\"CNY\",\"mid\":0.5640}," +
                        "{\"currency\":\"SDR (MFW)\",\"code\":\"XDR\",\"mid\":5.1467}]}]");
            } catch (Exception e) {
                Log.e("exp", e.getMessage());
            }
        }




        loadControls();
        ArrayList<FavoriteRowItem> tmp = new ArrayList<>();
        AllCurrencies allCurrencies = new AllCurrencies();
        String favPath = this.getFilesDir() + "/" + "favorites";

        File fileFav = new File(favPath);
        if (!fileFav.exists()) {
            try {
                fileFav.createNewFile();
                FileHandling fileHandling = new FileHandling(this);
                fileHandling.saveStringToFile("0,2,8,14", "favorites");
            } catch (Exception e) {
                Log.e("exp", e.getMessage());
            }
        }
        readFavoritesFromFile();

        inputEditText.setKeyListener(DigitsKeyListener.getInstance(true, true));
        setListeners();
        navigationView.bringToFront();
        setSettingsHeights();
        inputEditText.setText(inputEditText.getText());
    }

    public void readFavoritesFromFile() {
        ArrayList<FavoriteRowItem> tmp = new ArrayList<>();
        AllCurrencies allCurrencies = new AllCurrencies();
        FileHandling fh = new FileHandling(ctx);
        String foobar = fh.readFavoritesFromFile();
        String[] foobar2 = foobar.split(",");
        for (String item : foobar2) {
            try {
                CurrenciesRowItem favoriteRow = allCurrencies.getItem(Integer.parseInt(item));
                if (favoriteRow.getShortcut() == "PLN") {
                    tmp.add(new FavoriteRowItem(favoriteRow.getImageDrawable(), favoriteRow.getShortcut(), favoriteRow.getFullCurrency(), "1"));
                } else {
                    String json = presenter.readFromFile();
                    JSONArray foo = JSONHandling.getRates(json);
                    JSONObject tmp2 = foo.getJSONObject(allCurrencies.getIndex(favoriteRow.getShortcut()) - 1);
                    double id = tmp2.getDouble("mid");
                    tmp.add(new FavoriteRowItem(favoriteRow.getImageDrawable(), favoriteRow.getShortcut(), favoriteRow.getFullCurrency(), String.valueOf(id)));
                }
            } catch (Exception e) {

            }
        }
        favoriteListView.setAdapter(new FavoriteListAdapter(this, tmp));
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

    private void setSettingsHeights() {
        View headerView = navigationView.getHeaderView(0);
        TextView textView = (TextView) headerView.findViewById(R.id.navHeaderTextView);
        textView.setTextSize(Defaults.textViewTextSize);

        Menu menu = (Menu) navigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString s = new SpannableString(item.getTitle());
            s.setSpan(new RelativeSizeSpan((float) Defaults.settingsItemMenu), 0, s.length(), 0);
            item.setTitle(s);
        }
    }

    private String getJSON() {
        return presenter.getJSON();
    }

    private void saveToFile(String content) {
        presenter.saveToFile(content);
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
            }
            return false;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String shortcut = data.getStringExtra("shortcut");
            AllCurrencies allCurrencies = new AllCurrencies();
            if (!which) {
                firstShortcutButton.setText(shortcut);

                int id = getResources().getIdentifier("flag_" + shortcut.toLowerCase(), "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(id);
                firstFlagButton.setImageDrawable(drawable);
                inputEditText.setText(inputEditText.getText());
            } else {
                secondShortcutButton.setText(shortcut);
                int id = getResources().getIdentifier("flag_" + shortcut.toLowerCase(), "drawable", getPackageName());
                Drawable drawable = getResources().getDrawable(id);
                secondFlagButton.setImageDrawable(drawable);
                inputEditText.setText(inputEditText.getText());
            }
        }
        if (requestCode == 1003 && resultCode == Activity.RESULT_OK) {
            readFavoritesFromFile();
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
            Intent intent = new Intent(ctx, FavoriteListActivity.class);
            startActivityForResult(intent, 1003);
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
