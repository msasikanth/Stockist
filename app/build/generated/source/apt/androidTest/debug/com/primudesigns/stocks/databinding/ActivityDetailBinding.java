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
        sViewsWithIds.put(R.id.name, 2);
        sViewsWithIds.put(R.id.symbol, 3);
        sViewsWithIds.put(R.id.values, 4);
        sViewsWithIds.put(R.id.price, 5);
        sViewsWithIds.put(R.id.changeDollar, 6);
        sViewsWithIds.put(R.id.changePercentage, 7);
    }
    // views
    public final android.support.design.widget.CoordinatorLayout activityDetail;
    public final android.support.design.widget.AppBarLayout appBar;
    public final android.widget.TextView changeDollar;
    public final android.widget.TextView changePercentage;
    public final android.widget.TextView name;
    public final android.widget.TextView price;
    public final android.widget.TextView symbol;
    public final android.widget.RelativeLayout values;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDetailBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.activityDetail = (android.support.design.widget.CoordinatorLayout) bindings[0];
        this.activityDetail.setTag(null);
        this.appBar = (android.support.design.widget.AppBarLayout) bindings[1];
        this.changeDollar = (android.widget.TextView) bindings[6];
        this.changePercentage = (android.widget.TextView) bindings[7];
        this.name = (android.widget.TextView) bindings[2];
        this.price = (android.widget.TextView) bindings[5];
        this.symbol = (android.widget.TextView) bindings[3];
        this.values = (android.widget.RelativeLayout) bindings[4];
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