package com.primudesigns.stocks.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.primudesigns.stocks.R;
import com.primudesigns.stocks.sync.QuoteSyncJob;
import com.primudesigns.stocks.ui.DetailActivity;
import com.primudesigns.stocks.ui.MainActivity;

/**
 * Created by sasik on 2/27/2017.
 */

public class widgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {


        for (int widgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_list);

            Intent intent = new Intent(context, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            remoteViews.setOnClickPendingIntent(R.id.widget_name, pendingIntent);

            remoteAdapter(context, remoteViews);

            Intent clickIntentTemplate = new Intent(context, DetailActivity.class);

            PendingIntent pendingIntentTemplate = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(clickIntentTemplate)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

            remoteViews.setPendingIntentTemplate(R.id.widget_list, pendingIntentTemplate);
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

        }
    }

    private void remoteAdapter(Context context, final RemoteViews views) {
        views.setRemoteAdapter(R.id.widget_list, new Intent(context, widgetService.class));
    }

}
