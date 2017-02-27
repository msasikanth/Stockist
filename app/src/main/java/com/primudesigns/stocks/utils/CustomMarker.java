package com.primudesigns.stocks.utils;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.primudesigns.stocks.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sasik on 2/25/2017.
 */

public class CustomMarker extends MarkerView {

    private final Date date;
    private final SimpleDateFormat dateFormat;
    private final TextView textView;
    private final Entry finalEntry;
    private final DecimalFormat dollarFormat;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public CustomMarker(Context context, int layoutResource, Entry finalEntry) {
        super(context, layoutResource);

        textView = (TextView) findViewById(R.id.marker_text);
        dateFormat = new SimpleDateFormat("d MMM y", Locale.getDefault());
        date = new Date();
        this.finalEntry = finalEntry;
        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.getDefault());

    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        super.refreshContent(e, highlight);
        Float stockValue = e.getY();
        date.setTime((long) (e.getX()));
        String formattedDate = dateFormat.format(date);
        textView.setText(String.format(getContext().getString(R.string.marker_text), dollarFormat.format(stockValue), formattedDate));

        if (e.getX() >= finalEntry.getX()) {
            setOffset(-256, -32);
        } else {
            setOffset(-128, -32);
        }
    }


}
