package com.primudesigns.stocks.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.Pair;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.primudesigns.stocks.R;
import com.primudesigns.stocks.data.Contract;
import com.primudesigns.stocks.databinding.ActivityDetailBinding;
import com.primudesigns.stocks.utils.CustomMarker;
import com.primudesigns.stocks.utils.Parser;
import com.primudesigns.stocks.utils.ValueFormatterPrice;
import com.primudesigns.stocks.utils.ValueFormatterYear;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ActivityDetailBinding detailBinding;
    private int position;
    @ColorInt
    private int color;
    private boolean transition;
    private static final int CURSOR_LOADER = 14;
    private String dateFormat = "yyyy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        color = getIntent().getExtras().getInt("color");
        position = getIntent().getExtras().getInt("pos");
        transition = getIntent().getExtras().getBoolean("transition");

        getSupportLoaderManager().initLoader(CURSOR_LOADER, null, this);

        if (transition) {

            if (savedInstanceState != null) {

                getWindow().setStatusBarColor(manipulateColor(color, 0.8f));
                detailBinding.stockChart.setVisibility(View.VISIBLE);

            } else {

                getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {

                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        getWindow().setStatusBarColor(manipulateColor(color, 0.8f));
                        detailBinding.stockChart.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {

                    }

                    @Override
                    public void onTransitionPause(Transition transition) {

                    }

                    @Override
                    public void onTransitionResume(Transition transition) {

                    }
                });
            }

        } else {
            getWindow().setStatusBarColor(manipulateColor(color, 0.8f));
            detailBinding.stockChart.setVisibility(View.VISIBLE);
        }

        detailBinding.appBar.setBackgroundColor(color);

    }

    @Override
    public void onBackPressed() {
        detailBinding.appBar.setVisibility(View.INVISIBLE);
        detailBinding.stockChart.setVisibility(View.INVISIBLE);
        super.onBackPressed();

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                Contract.Quote.URI,
                Contract.Quote.QUOTE_COLUMNS,
                null, null, Contract.Quote.COLUMN_NAME);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (data != null) {
            data.moveToPosition(position);

            LineChart chart = detailBinding.stockChart;

            DecimalFormat dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
            DecimalFormat dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
            dollarFormatWithPlus.setPositivePrefix("+$");
            DecimalFormat percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
            percentageFormat.setMaximumFractionDigits(2);
            percentageFormat.setMinimumFractionDigits(2);
            percentageFormat.setPositivePrefix("+");

            String name = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_NAME));
            String symbol = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)) + " ( NASDAQ )";
            String price = dollarFormat.format(data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_PRICE)));


            detailBinding.name.setText(name);
            detailBinding.name.setContentDescription(name);
            detailBinding.symbol.setText(symbol);
            detailBinding.symbol.setContentDescription(symbol);
            detailBinding.price.setText(price);
            detailBinding.price.setContentDescription(price);

            float rawAbsoluteChange = data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
            float percentageChange = data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE));

            String change = dollarFormatWithPlus.format(rawAbsoluteChange);
            String percentage = percentageFormat.format(percentageChange / 100);

            detailBinding.changeDollar.setText(change);
            detailBinding.changeDollar.setContentDescription(change);
            detailBinding.changePercentage.setText(" ( " + percentage + " )");
            detailBinding.changePercentage.setContentDescription(percentage);

            Pair<Float, List<Entry>> result = Parser.getFormattedStockHistory(data.getString(data.getColumnIndex(Contract.Quote.COLUMN_HISTORY_YEARS)));

            List<Entry> entries = result.second;

            LineDataSet dataSet = new LineDataSet(entries, "label");
            dataSet.setDrawCircles(false);
            dataSet.setLineWidth(0f);
            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            dataSet.setDrawFilled(true);
            dataSet.setDrawValues(false);
            dataSet.setFillColor(color);
            dataSet.setLabel(null);
            dataSet.setHighLightColor(Color.TRANSPARENT);

            LineData lineData = new LineData(dataSet);
            chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            chart.getXAxis().setDrawAxisLine(true);
            chart.getXAxis().setDrawGridLines(false);
            chart.getXAxis().setValueFormatter(new ValueFormatterYear(dateFormat));
            chart.getAxis(YAxis.AxisDependency.RIGHT).setDrawLabels(false);
            chart.getAxis(YAxis.AxisDependency.RIGHT).setDrawGridLines(false);
            chart.getAxis(YAxis.AxisDependency.RIGHT).setDrawAxisLine(false);
            chart.getAxis(YAxis.AxisDependency.LEFT).setDrawGridLines(true);
            chart.getAxis(YAxis.AxisDependency.LEFT).setDrawAxisLine(false);
            chart.getAxis(YAxis.AxisDependency.LEFT).setValueFormatter(new ValueFormatterPrice());

            //disable all interactions with the graph
            chart.setDragEnabled(false);
            chart.setScaleEnabled(false);
            chart.setDragDecelerationEnabled(false);
            chart.setPinchZoom(false);
            chart.setDoubleTapToZoomEnabled(false);
            Description description = new Description();
            description.setText(" ");
            chart.setDescription(description);

            CustomMarker customMarkerView = new CustomMarker(getBaseContext(),
                    R.layout.mark_view, getLastButOneData(entries));

            chart.setMarker(customMarkerView);
            chart.setData(lineData);
            chart.invalidate();


        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


    private Entry getLastButOneData(List<Entry> entries) {
        if (entries.size() > 2) {
            return entries.get(entries.size() - 2);
        } else {
            return entries.get(entries.size() - 1);
        }
    }


    private int manipulateColor(int color, float factor) {
        int a = Color.alpha(color);
        int r = Math.round(Color.red(color) * factor);
        int g = Math.round(Color.green(color) * factor);
        int b = Math.round(Color.blue(color) * factor);
        return Color.argb(a,
                Math.min(r, 255),
                Math.min(g, 255),
                Math.min(b, 255));
    }


}
