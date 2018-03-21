package com.example.kamil.currencycalcforolderpeople;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

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
        setHeights();
    }

    public void setHeights() {
        firstShortcutButton.setTextSize(Defaults.buttonTextSize);
        secondShortcutButton.setTextSize(Defaults.buttonTextSize);

        inputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        outputEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);

        settingsButton.setTextSize(Defaults.buttonTextSize);
        updateButton.setTextSize(Defaults.buttonTextSize);
        customButton.setTextSize(Defaults.buttonTextSize);

    }

    private void setAlertHeights(AlertDialog alert) {
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
        for (int i = 0; i < 4; i++) {
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
                        if (isNetworkAvailable()) {
                            String buffer = getJSONCurrencies();

                            saveToFile(buffer);
                            didConnect = true;
                        } else {
                            didConnect = false;
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
                setAlertHeights(alert);

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
                //do somthing
                break;
            }
            case R.id.font_size: {
                //do smth
                break;
            }
            case R.id.language: {
                //do smth
                break;
            }
            case R.id.informations: {
                //do smth
                break;
            }
        }
        //navBar.closeDrawer(GravityCompat.START);
        return true;
    }
}
