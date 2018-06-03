package com.example.kamil.currencycalcforolderpeople;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamil.currencycalcforolderpeople.R;

import java.util.ArrayList;
import java.util.List;

class FavoriteListAdapter extends BaseAdapter {

    Context ctx;
    private ArrayList<FavoriteRowItem> data;
    private static LayoutInflater inflater = null;
    private ImageView imageView;
    private TextView shortcutTextView, fullCurrencyTextView, currencyValueTextVIew;

    public FavoriteListAdapter(Context context, ArrayList<FavoriteRowItem> data) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;

        if (vi == null) {
            vi = inflater.inflate(R.layout.favorite_row, null);

        }



        imageView = (ImageView) vi.findViewById(R.id.imageView);
        shortcutTextView = (TextView) vi.findViewById(R.id.shortcutView);
        fullCurrencyTextView = (TextView) vi.findViewById(R.id.fullCurrencyView);
        currencyValueTextVIew = (TextView) vi.findViewById(R.id.currencyValueView);
        setHeights();
        setListRow(position);
        return vi;
    }

    private void setListRow(int position) {
        imageView.setImageResource(data.get(position).getImageDrawable());
        shortcutTextView.setText(data.get(position).getShortcut());
        fullCurrencyTextView.setText(data.get(position).getFullCurrency());
        currencyValueTextVIew.setText(data.get(position).getValue());
    }

    public void setHeights() {
        imageView.getLayoutParams().height = Defaults.imageButtonHeight;
        imageView.requestLayout();
        int sp = (int) (Defaults.textViewTextSize / ctx.getResources().getDisplayMetrics().scaledDensity);
        int sp2 = (int) (Defaults.shortcutViewTextSize / ctx.getResources().getDisplayMetrics().scaledDensity);
        shortcutTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp2);
        fullCurrencyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
        currencyValueTextVIew.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp);
    }
}