package com.example.kamil.currencycalcforolderpeople;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class FavoriteChooseListAdapter extends BaseAdapter {
    private Context ctx;
    private static LayoutInflater inflater = null;
    private ArrayList<FavoriteChooseRowItem> data;
    private ImageView imageView;
    private TextView shortcutTextView, fullCurrencyTextView;
    public static boolean[] mCheckedState;

    public FavoriteChooseListAdapter(Context context, ArrayList<FavoriteChooseRowItem> data) {
        ctx = context;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        mCheckedState = new boolean[data.size()];
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.indexOf(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vi = view;
        if (vi == null) {
            vi = inflater.inflate(R.layout.favorite_choose_row, null);
        }
        imageView = (ImageView) vi.findViewById(R.id.imageView2);
        shortcutTextView = (TextView) vi.findViewById(R.id.shortcutView2);
        fullCurrencyTextView = (TextView) vi.findViewById(R.id.fullCurrencyView2);
        setHeights();
        setListRow(i);
        CheckBox result = (CheckBox)vi.findViewById(R.id.checkBox);
        if (result == null) {
            result = new CheckBox(ctx);
        }
        result.setChecked(mCheckedState[i]);

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
