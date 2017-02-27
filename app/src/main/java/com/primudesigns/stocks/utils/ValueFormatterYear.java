package com.primudesigns.stocks.utils;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sasik on 2/23/2017.
 */

public class ValueFormatterYear implements IAxisValueFormatter {

    private final SimpleDateFormat dateFormat;
    private final Date date;

    public ValueFormatterYear(String dateFormat) {
        this.dateFormat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        this.date = new Date();
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        date.setTime((long) value);
        return dateFormat.format(date);
    }
}
