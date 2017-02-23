
package android.databinding;
import com.primudesigns.stocks.BR;
class DataBinderMapper {
    final static int TARGET_MIN_SDK = 21;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.primudesigns.stocks.R.layout.activity_main:
                    return com.primudesigns.stocks.databinding.ActivityMainBinding.bind(view, bindingComponent);
                case com.primudesigns.stocks.R.layout.activity_detail:
                    return com.primudesigns.stocks.databinding.ActivityDetailBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case 423753077: {
                if(tag.equals("layout/activity_main_0")) {
                    return com.primudesigns.stocks.R.layout.activity_main;
                }
                break;
            }
            case 257710925: {
                if(tag.equals("layout/activity_detail_0")) {
                    return com.primudesigns.stocks.R.layout.activity_detail;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}