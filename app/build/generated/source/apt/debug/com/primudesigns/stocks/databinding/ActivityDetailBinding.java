package com.primudesigns.stocks.databinding;
import com.primudesigns.stocks.R;
import com.primudesigns.stocks.BR;
import android.view.View;
public class ActivityDetailBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.appBar, 1);
        sViewsWithIds.put(R.id.mainContent, 2);
        sViewsWithIds.put(R.id.name, 3);
        sViewsWithIds.put(R.id.symbol, 4);
        sViewsWithIds.put(R.id.values, 5);
        sViewsWithIds.put(R.id.price, 6);
        sViewsWithIds.put(R.id.changeDollar, 7);
        sViewsWithIds.put(R.id.changePercentage, 8);
        sViewsWithIds.put(R.id.stockChart, 9);
    }
    // views
    public final android.support.design.widget.CoordinatorLayout activityDetail;
    public final android.support.design.widget.AppBarLayout appBar;
    public final com.pddstudio.plaid.components.BaselineGridTextView changeDollar;
    public final com.pddstudio.plaid.components.BaselineGridTextView changePercentage;
    public final android.widget.RelativeLayout mainContent;
    public final com.pddstudio.plaid.components.BaselineGridTextView name;
    public final com.pddstudio.plaid.components.BaselineGridTextView price;
    public final com.github.mikephil.charting.charts.LineChart stockChart;
    public final com.pddstudio.plaid.components.BaselineGridTextView symbol;
    public final android.widget.RelativeLayout values;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDetailBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.activityDetail = (android.support.design.widget.CoordinatorLayout) bindings[0];
        this.activityDetail.setTag(null);
        this.appBar = (android.support.design.widget.AppBarLayout) bindings[1];
        this.changeDollar = (com.pddstudio.plaid.components.BaselineGridTextView) bindings[7];
        this.changePercentage = (com.pddstudio.plaid.components.BaselineGridTextView) bindings[8];
        this.mainContent = (android.widget.RelativeLayout) bindings[2];
        this.name = (com.pddstudio.plaid.components.BaselineGridTextView) bindings[3];
        this.price = (com.pddstudio.plaid.components.BaselineGridTextView) bindings[6];
        this.stockChart = (com.github.mikephil.charting.charts.LineChart) bindings[9];
        this.symbol = (com.pddstudio.plaid.components.BaselineGridTextView) bindings[4];
        this.values = (android.widget.RelativeLayout) bindings[5];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
        }
        return false;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDetailBinding>inflate(inflater, com.primudesigns.stocks.R.layout.activity_detail, root, attachToRoot, bindingComponent);
    }
    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.primudesigns.stocks.R.layout.activity_detail, null, false), bindingComponent);
    }
    public static ActivityDetailBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityDetailBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}