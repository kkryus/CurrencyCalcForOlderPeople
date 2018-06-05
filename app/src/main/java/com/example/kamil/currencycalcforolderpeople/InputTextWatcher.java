package com.example.kamil.currencycalcforolderpeople;


import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InputTextWatcher implements TextWatcher {
    private ViewPresenter presenter;
    private Activity activity;

    public InputTextWatcher(ViewPresenter presenter, Activity activity) {
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (presenter == null) {
            presenter = new ViewPresenter(activity);
        }
        String json = presenter.readFromFile();
        Button firstShortcutButton = (Button) activity.findViewById(R.id.firstShortcutButton);
        Button secondShortcutButton = (Button) activity.findViewById(R.id.secondShortcutButton);
        EditText inputEditText = (EditText) activity.findViewById(R.id.inputEditText);
        EditText outputEditText = (EditText) activity.findViewById(R.id.outputEditText);

        try {
            JSONArray foo = JSONHandling.getRates(json);
            if (!firstShortcutButton.getText().toString().equals("PLN") && !inputEditText.getText().toString().equals("")) {
                double value = 0;
                for (int i = 0; i < foo.length(); i++) {
                    JSONObject tmp = foo.getJSONObject(i);
                    if (tmp.getString("code").equals(firstShortcutButton.getText().toString().replaceAll(",","."))) {
                        double id = tmp.getDouble("mid");
                        value = Double.parseDouble(inputEditText.getText().toString().replaceAll(",",".")) * id;

                        outputEditText.setText(String.format("%." + Settings.numberPrecision +"f",value));
                        break;
                    }
                }
                for (int i = 0; i < foo.length(); i++) {
                    JSONObject tmp = foo.getJSONObject(i);
                    if (tmp.getString("code").equals(secondShortcutButton.getText().toString().replaceAll(",","."))) {
                        double id = tmp.getDouble("mid");
                        value = value / id;
                        outputEditText.setText(String.format("%." + Settings.numberPrecision + "f", value).replaceAll(",","."));
                        break;
                    }
                }

            }
            if (firstShortcutButton.getText().toString().equals("PLN") && !inputEditText.getText().toString().equals("")) {
                for (int i = 0; i < foo.length(); i++) {
                    JSONObject tmp = foo.getJSONObject(i);
                    if (tmp.getString("code").equals(secondShortcutButton.getText().toString().replaceAll(",","."))) {
                        double id = tmp.getDouble("mid");
                        double value = Double.parseDouble(inputEditText.getText().toString()) / id;
                        outputEditText.setText(String.format("%." + Settings.numberPrecision + "f", value).replaceAll(",","."));
                        break;
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
