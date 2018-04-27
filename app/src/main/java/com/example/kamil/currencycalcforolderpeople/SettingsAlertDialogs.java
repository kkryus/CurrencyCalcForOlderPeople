package com.example.kamil.currencycalcforolderpeople;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

public class SettingsAlertDialogs {

    public static AlertDialog getNumberPrecisionAlertDialog(Context context)
    {
        CharSequence numberPrecisions[] = new CharSequence[]{"0.1", "0.12", "0.123", "0.1234"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.numberPrecision));
        builder.setItems(numberPrecisions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Settings.numberPrecision = which + 1;
            }
        });
        AlertDialog alert = builder.create();
        return alert;
    }

    public static AlertDialog getLanguageAlertDialog(final Context context, final ViewPresenter presenter)
    {
        CharSequence numberPrecisions[] = new CharSequence[]{"English", "Polski"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.chooseLanguage));
        builder.setItems(numberPrecisions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Settings.language = "en";
                } else {
                    Settings.language = "pl";
                }

                presenter.changeLanguage();
                Intent myIntent = new Intent(context, MainActivity.class);
                context.startActivity(myIntent);
                ((Activity)context).finish();
            }
        });
        AlertDialog alert = builder.create();
        return alert;
    }

    public static AlertDialog getConnectionAlertDialog(Context context)
    {
        CharSequence numberPrecisions[] = new CharSequence[]{context.getString(R.string.justWifi), context.getString(R.string.all)};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.connection));
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
        return alert;
    }

    public static AlertDialog getInformationAlertDialog(Context context)
    {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        setInformationSettings(builder, context);

        AlertDialog alert = builder.create();
        alert.show();
        setConnectionAlertHeights(alert);
        return alert;
    }

    public static AlertDialog getNoConnectionAlertDialog(Context context)
    {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        setAlertSettings(builder, context);

        AlertDialog alert = builder.create();
        return alert;
    }

    private static void setInformationSettings(AlertDialog.Builder builder, Context context) {
        builder.setTitle(context.getString(R.string.information));
        builder.setMessage(context.getString(R.string.information_message));
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setIcon(android.R.drawable.ic_dialog_info);
    }

    private static void setConnectionAlertHeights(AlertDialog alert) {
        alert.getWindow().getAttributes();

        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize((float) (Defaults.alertTextSize));

        Button buttonNeutral = alert.getButton(Dialog.BUTTON_NEUTRAL);
        buttonNeutral.setTextSize(Defaults.buttonTextSize);
    }


    private static void setAlertSettings(AlertDialog.Builder builder, Context context) {
        builder.setTitle(context.getString(R.string.connectionTitle));
        builder.setMessage(context.getString(R.string.connectionMessage));
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setIcon(android.R.drawable.ic_dialog_alert);
    }


}
