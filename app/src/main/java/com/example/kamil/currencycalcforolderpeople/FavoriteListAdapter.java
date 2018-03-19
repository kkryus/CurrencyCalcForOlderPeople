package com.example.kamil.currencycalcforolderpeople;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kamil.currencycalcforolderpeople.R;

import java.util.ArrayList;
import java.util.List;

class FavoriteListAdapter extends BaseAdapter {

    private ArrayList<FavoriteRowItem> data;
    private static LayoutInflater inflater = null;
    ImageButton imageButton;
    TextView shortcutTextView, fullCurrencyTextView, currencyValueTextVIew;

    public FavoriteListAdapter(Context context, ArrayList<FavoriteRowItem> data) {
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
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.favorite_row, null);
        }

        imageButton = (ImageButton) vi.findViewById(R.id.flagButton);
        shortcutTextView = (TextView) vi.findViewById(R.id.shortcutView);
        fullCurrencyTextView = (TextView) vi.findViewById(R.id.fullCurrencyView);
        currencyValueTextVIew = (TextView) vi.findViewById(R.id.currencyValueView);

        setListRow(position);

        return vi;
    }

    private void setListRow(int position) {
        imageButton.setImageResource(data.get(position).getImageDrawable());//*/
        shortcutTextView.setText(data.get(position).getShortcut());
        fullCurrencyTextView.setText(data.get(position).getFullCurrency());
        currencyValueTextVIew.setText(data.get(position).getValue());
    }
}