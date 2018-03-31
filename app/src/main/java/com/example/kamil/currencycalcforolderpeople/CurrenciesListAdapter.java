package com.example.kamil.currencycalcforolderpeople;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrenciesListAdapter extends BaseAdapter {
    Context ctx;
    private ArrayList<CurrenciesRowItem> data;
    private static LayoutInflater inflater = null;
    private ImageView imageView;
    private TextView shortcutTextView, fullCurrencyTextView;
    static int position = 0;

    public CurrenciesListAdapter(Context context, ArrayList<CurrenciesRowItem> data) {
        ctx = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.indexOf(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        this.position = position;
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.my_alert_dialog, null);
        }

        imageView = (ImageView) vi.findViewById(R.id.listImageFlag);
        shortcutTextView = (TextView) vi.findViewById(R.id.listShortcutView);
        fullCurrencyTextView = (TextView) vi.findViewById(R.id.listFullNameView);
        CheckBox star = (CheckBox) vi.findViewById(R.id.star);
        star.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //Log.e("zdzisiek", buttonView.getParent().getParent() + "");
                //int position2 = .getPositionForView((View) buttonView.getParent());
                //Log.v("oko", "ra clicked, row " + position2);
            }
        }
        );
        setHeights();
        setListRow(position);

        return vi;
    }

    private void setListRow(int position) {
        imageView.setImageResource(data.get(position).getImageDrawable());
        shortcutTextView.setText(data.get(position).getShortcut());
        fullCurrencyTextView.setText(data.get(position).getFullCurrency());
    }

    public void setHeights() {
        imageView.getLayoutParams().height = Defaults.imageButtonHeight;
        imageView.requestLayout();
        int sp = (int) (Defaults.textViewTextSize / ctx.getResources().getDisplayMetrics().scaledDensity);
        int sp2 = (int) (Defaults.shortcutViewTextSize / ctx.getResources().getDisplayMetrics().scaledDensity);
        shortcutTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp2);
        fullCurrencyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }
}
