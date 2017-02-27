package com.primudesigns.stocks.widget;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.primudesigns.stocks.R;
import com.primudesigns.stocks.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by sasik on 2/27/2017.
 */

public class widgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteView();
    }

    public class ListRemoteView implements RemoteViewsService.RemoteViewsFactory {

        private Cursor data = null;

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            if (data != null) data.close();

            final long identityToken = Binder.clearCallingIdentity();

            data = getContentResolver().query(Contract.Quote.URI,
                    Contract.Quote.QUOTE_COLUMNS,
                    null,
                    null,
                    Contract.Quote.COLUMN_NAME);

            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            if (position == AdapterView.INVALID_POSITION || data == null
                    || !data.moveToPosition(position)) {
                return null;
            }

            RemoteViews remoteViews = new RemoteViews(getPackageName(),
                    R.layout.widget_item_quote);

            String name = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_NAME));
            String stockSymbol = data.getString(data.getColumnIndex(Contract.Quote.COLUMN_SYMBOL));

            Float stockPrice = data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_PRICE));
            Float absoluteChange = data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
            Float percentageChange = data.getFloat(data.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE));

            DecimalFormat dollarFormatWithPlus;
            DecimalFormat dollarFormat;
            DecimalFormat percentageFormat;

            dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
            dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
            dollarFormatWithPlus.setPositivePrefix("+$");
            percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
            percentageFormat.setMaximumFractionDigits(2);
            percentageFormat.setMinimumFractionDigits(2);
            percentageFormat.setPositivePrefix("+");

            if (absoluteChange > 0) {
                remoteViews.setTextColor(R.id.changeDollar, getResources().getColor(R.color.material_green_500));
                remoteViews.setTextColor(R.id.changePercentage, getResources().getColor(R.color.material_green_500));
            } else {
                remoteViews.setTextColor(R.id.changeDollar, getResources().getColor(R.color.material_red_500));
                remoteViews.setTextColor(R.id.changePercentage, getResources().getColor(R.color.material_red_500));            }

            remoteViews.setTextViewText(R.id.name, name);
            remoteViews.setTextViewText(R.id.symbol, stockSymbol);
            remoteViews.setTextViewText(R.id.price, dollarFormat.format(stockPrice));
            remoteViews.setTextViewText(R.id.changeDollar, dollarFormatWithPlus.format(absoluteChange));
            remoteViews.setTextViewText(R.id.changePercentage, "( " + percentageFormat.format(percentageChange / 100) + " )");


            final Intent fillInIntent = new Intent();
            Uri stockUri = Contract.Quote.makeUriForStock(stockSymbol);
            fillInIntent.setData(stockUri);
            remoteViews.setOnClickFillInIntent(R.id.card, fillInIntent);
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return data.moveToPosition(i) ? data.getLong(Contract.Quote.POSITION_ID) : i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}
