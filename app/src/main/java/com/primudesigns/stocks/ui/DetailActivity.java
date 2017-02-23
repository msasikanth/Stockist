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
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.primudesigns.stocks.R;
import com.primudesigns.stocks.data.Contract;
import com.primudesigns.stocks.databinding.ActivityDetailBinding;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import timber.log.Timber;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private ActivityDetailBinding detailBinding;
    private int position;
    private static final int CURSOR_LOADER = 14;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        @ColorInt final int color = getIntent().getExtras().getInt("color");
        position = getIntent().getExtras().getInt("pos");

        getSupportLoaderManager().initLoader(CURSOR_LOADER, null, this);
        getWindow().getSharedElementEnterTransition().addListener(new Transition.TransitionListener() {
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
                Contract.Quote.QUOTE_COLUMNS.toArray(new String[]{}),
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

            detailBinding.name.setText(data.getString(data.getColumnIndex(Contract.Quote.COLUMN_NAME)));
            detailBinding.symbol.setText(data.getString(data.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)) + " ( NASDAQ )");
            detailBinding.price.setText(dollarFormat.format(data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_PRICE))));

            float rawAbsoluteChange = data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
            float percentageChange = data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE));

            String change = dollarFormatWithPlus.format(rawAbsoluteChange);
            String percentage = percentageFormat.format(percentageChange / 100);

            detailBinding.changeDollar.setText(change);
            detailBinding.changePercentage.setText(" ( " + percentage + " )");

            Timber.d(data.getString(data.getColumnIndex(Contract.Quote.COLUMN_HISTORY_PRICE)));

            String years = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_HISTORY_YEARS));
            String price = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_HISTORY_PRICE));

            String[] yearsArray = years.split(",");
            String[] priceArray = price.split(",");

            Log.d("Array", String.valueOf(priceArray.length));

            XAxis xAxis = chart.getXAxis();
            xAxis.setDrawAxisLine(true);
            xAxis.setDrawGridLines(false);
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setValueFormatter(new ValueFormatter(yearsArray));

            YAxis yAxis = chart.getAxis(YAxis.AxisDependency.LEFT);
            yAxis.setValueFormatter(new ValueFormatter(priceArray));

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
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
