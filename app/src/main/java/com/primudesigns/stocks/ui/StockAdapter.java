package com.primudesigns.stocks.ui;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.primudesigns.stocks.R;
import com.primudesigns.stocks.data.Contract;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private final Context context;
    private final DecimalFormat dollarFormatWithPlus;
    private final DecimalFormat dollarFormat;
    private final DecimalFormat percentageFormat;
    private Cursor cursor;
    private final StockAdapterOnClickHandler clickHandler;
    @ColorInt
    private int color;

    StockAdapter(Context context, StockAdapterOnClickHandler clickHandler) {
        this.context = context;
        this.clickHandler = clickHandler;

        dollarFormat = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus = (DecimalFormat) NumberFormat.getCurrencyInstance(Locale.US);
        dollarFormatWithPlus.setPositivePrefix("+$");
        percentageFormat = (DecimalFormat) NumberFormat.getPercentInstance(Locale.getDefault());
        percentageFormat.setMaximumFractionDigits(2);
        percentageFormat.setMinimumFractionDigits(2);
        percentageFormat.setPositivePrefix("+");
    }

    void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    String getSymbolAtPosition(int position) {

        cursor.moveToPosition(position);
        return cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL));
    }

    @Override
    public StockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(context).inflate(R.layout.list_item_quote, parent, false);

        return new StockViewHolder(item);
    }

    @Override
    public void onViewDetachedFromWindow(StockViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        if (holder.mainContent.getVisibility() == View.VISIBLE) {

            final View myView = holder.mainContent;

            // get the center for the clipping circle
            int cx = myView.getMeasuredWidth() / 2;
            int cy = myView.getMeasuredHeight() / 2;

            // get the initial radius for the clipping circle
            int initialRadius = myView.getWidth();

            // create the animation (the final radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);

            // make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.INVISIBLE);
                }
            });

            anim.setDuration(250);
            // start the animation
            anim.start();

        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(StockViewHolder holder, int position) {

        cursor.moveToPosition(position);

        String name = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_NAME));
        String symbol = cursor.getString(cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL)) + " ( NASDAQ )";
        String price = dollarFormat.format(cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_PRICE)));

        holder.name.setText(name);
        holder.name.setContentDescription(name);
        holder.symbol.setText(symbol);
        holder.symbol.setContentDescription(symbol);
        holder.price.setText(price);
        holder.price.setContentDescription(price);

        holder.fakeName.setText(name);
        holder.fakeName.setContentDescription(name);
        holder.fakeSymbol.setText(symbol);
        holder.fakeSymbol.setContentDescription(symbol);
        holder.fakePrice.setText(price);
        holder.fakePrice.setContentDescription(price);

        float rawAbsoluteChange = cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));
        float percentageChange = cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_PERCENTAGE_CHANGE));

        if (rawAbsoluteChange > 0) {

            holder.fakeDollar.setTextColor(ContextCompat.getColor(context, R.color.material_green_500));
            holder.fakePercentage.setTextColor(ContextCompat.getColor(context, R.color.material_green_500));

        } else {

            holder.fakeDollar.setTextColor(ContextCompat.getColor(context, R.color.material_red_500));
            holder.fakePercentage.setTextColor(ContextCompat.getColor(context, R.color.material_red_500));
        }

        String change = dollarFormatWithPlus.format(rawAbsoluteChange);
        String percentage = percentageFormat.format(percentageChange / 100);

        holder.changeDollar.setText(change);
        holder.changeDollar.setContentDescription(change);
        holder.changePercentage.setText(" ( " + percentage + " )");
        holder.changePercentage.setContentDescription(percentage);

        holder.fakeDollar.setText(change);
        holder.fakeDollar.setContentDescription(change);
        holder.fakePercentage.setText(" ( " + percentage + " )");
        holder.fakePercentage.setContentDescription(percentage);

    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (cursor != null) {
            count = cursor.getCount();
        }
        return count;
    }

    interface StockAdapterOnClickHandler {
        void onClick(String symbol, CardView cardView, RelativeLayout mainContent, @ColorInt int color, int position);
    }

    class StockViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.name)
        TextView name;

        @BindView(R.id.symbol)
        TextView symbol;

        @BindView(R.id.price)
        TextView price;

        @BindView(R.id.changeDollar)
        TextView changeDollar;

        @BindView(R.id.changePercentage)
        TextView changePercentage;

        @BindView(R.id.card)
        CardView stockCard;

        @BindView(R.id.mainContent)
        RelativeLayout mainContent;

        @BindView(R.id.fakename)
        TextView fakeName;

        @BindView(R.id.fakesymbol)
        TextView fakeSymbol;

        @BindView(R.id.fakeprice)
        TextView fakePrice;

        @BindView(R.id.fakeDollar)
        TextView fakeDollar;

        @BindView(R.id.fakePercentage)
        TextView fakePercentage;

        StockViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            cursor.moveToPosition(adapterPosition);
            int symbolColumn = cursor.getColumnIndex(Contract.Quote.COLUMN_SYMBOL);

            float rawAbsoluteChange = cursor.getFloat(cursor.getColumnIndex(Contract.Quote.COLUMN_ABSOLUTE_CHANGE));

            if (rawAbsoluteChange > 0) {
                color = ContextCompat.getColor(context, R.color.material_green_500);

            } else {
                color = ContextCompat.getColor(context, R.color.material_red_500);
            }

            clickHandler.onClick(cursor.getString(symbolColumn), stockCard, mainContent, color, adapterPosition);
        }


    }
}
