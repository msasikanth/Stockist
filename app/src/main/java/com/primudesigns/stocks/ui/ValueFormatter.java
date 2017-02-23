package com.primudesigns.stocks.ui;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

/**
 * Created by sasik on 2/23/2017.
 */

public class ValueFormatter implements IAxisValueFormatter {

    private String[] mValues;

    public ValueFormatter(String[] mValues) {
        this.mValues = mValues;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        // "value" represents the position of the label on the axis (x or y)
        return mValues[(int) value];
    }
}
