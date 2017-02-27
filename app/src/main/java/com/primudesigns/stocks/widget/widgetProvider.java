package com.primudesigns.stocks.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.widget.RemoteViews;

import com.primudesigns.stocks.R;
import com.primudesigns.stocks.sync.QuoteSyncJob;
import com.primudesigns.stocks.ui.DetailActivity;
import com.primudesigns.stocks.ui.MainActivity;


public class widgetProvider extends AppWidgetProvider {

    public static final String CLICK_ACTION = "com.primudesigns.stocks";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        for (int widgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_name, pendingIntent);

            remoteAdapter(context, remoteViews);

            Intent toastIntent = new Intent(context, widgetProvider.class);
            toastIntent.setAction(widgetProvider.CLICK_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.widget_list, toastPendingIntent);

//            Intent clickIntentTemplate = new Intent(context, DetailActivity.class);
//
//            PendingIntent pendingIntentTemplate = TaskStackBuilder.create(context)
//                    .addNextIntentWithParentStack(clickIntentTemplate)
//                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            remoteViews.setPendingIntentTemplate(R.id.widget_list, pendingIntentTemplate);

            remoteViews.setEmptyView(R.id.widget_list, R.id.widget_empty);
            remoteViews.setContentDescription(R.id.widget_list, "Stock items");
            appWidgetManager.updateAppWidget(widgetId, remoteViews);

        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (QuoteSyncJob.ACTION_DATA_UPDATED.equals(intent.getAction())) {

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(
                    new ComponentName(context, getClass()));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);

        } else if (intent.getAction().equals(CLICK_ACTION)) {
            int position = intent.getIntExtra("pos", 0);
            @ColorInt int color = intent.getIntExtra("color", ContextCompat.getColor(context, R.color.material_green_500));

            Intent i = new Intent(context, DetailActivity.class);
            i.putExtra("pos",position);
            i.putExtra("color", color);
            i.putExtra("transition", false);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    private void remoteAdapter(Context context, final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list, new Intent(context, widgetService.class));
    }

}
