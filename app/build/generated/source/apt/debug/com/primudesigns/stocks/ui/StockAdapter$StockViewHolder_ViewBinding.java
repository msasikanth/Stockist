// Generated code from Butter Knife. Do not modify!
package com.primudesigns.stocks.ui;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.primudesigns.stocks.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StockAdapter$StockViewHolder_ViewBinding<T extends StockAdapter.StockViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public StockAdapter$StockViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.name = Utils.findRequiredViewAsType(source, R.id.name, "field 'name'", TextView.class);
    target.symbol = Utils.findRequiredViewAsType(source, R.id.symbol, "field 'symbol'", TextView.class);
    target.price = Utils.findRequiredViewAsType(source, R.id.price, "field 'price'", TextView.class);
    target.changeDollar = Utils.findRequiredViewAsType(source, R.id.changeDollar, "field 'changeDollar'", TextView.class);
    target.changePercentage = Utils.findRequiredViewAsType(source, R.id.changePercentage, "field 'changePercentage'", TextView.class);
    target.stockCard = Utils.findRequiredViewAsType(source, R.id.card, "field 'stockCard'", CardView.class);
    target.mainContent = Utils.findRequiredViewAsType(source, R.id.mainContent, "field 'mainContent'", RelativeLayout.class);
    target.fakeName = Utils.findRequiredViewAsType(source, R.id.fakename, "field 'fakeName'", TextView.class);
    target.fakeSymbol = Utils.findRequiredViewAsType(source, R.id.fakesymbol, "field 'fakeSymbol'", TextView.class);
    target.fakePrice = Utils.findRequiredViewAsType(source, R.id.fakeprice, "field 'fakePrice'", TextView.class);
    target.fakeDollar = Utils.findRequiredViewAsType(source, R.id.fakeDollar, "field 'fakeDollar'", TextView.class);
    target.fakePercentage = Utils.findRequiredViewAsType(source, R.id.fakePercentage, "field 'fakePercentage'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.name = null;
    target.symbol = null;
    target.price = null;
    target.changeDollar = null;
    target.changePercentage = null;
    target.stockCard = null;
    target.mainContent = null;
    target.fakeName = null;
    target.fakeSymbol = null;
    target.fakePrice = null;
    target.fakeDollar = null;
    target.fakePercentage = null;

    this.target = null;
  }
}
