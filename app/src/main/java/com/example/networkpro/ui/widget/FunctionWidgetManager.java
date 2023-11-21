package com.example.networkpro.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.widget.RemoteViews;

import com.example.lib_common.BaseApplication;
import com.example.lib_common.consts.Const;
import com.example.lib_common.widget.IWidgetManager;
import com.example.networkpro.R;

/**
 * Created by 王鑫哲 on 2023/5/18 9:26 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class FunctionWidgetManager implements IWidgetManager {

    private FunctionWidgetManager() {

    }

    private static class Holder {
        private static final FunctionWidgetManager h = new FunctionWidgetManager();
    }

    public static FunctionWidgetManager getInstance() {
        return Holder.h;
    }

    @Override
    public void createRemoteView(AppWidgetManager appWidgetManager) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        RemoteViews remoteView = new RemoteViews(Const.PACKAGE_NAME, R.layout.widget_function_layout);

        createPendingIntentInner(remoteView);

        ComponentName componentName = new ComponentName(context, FunctionWidgetProvider.class);
        appWidgetManager.updateAppWidget(componentName, remoteView);
    }

    private static void createPendingIntentInner(RemoteViews remoteView) {
        remoteView.setOnClickPendingIntent(R.id.ll_seek,
                getPendingIntent(R.id.ll_seek));
        remoteView.setOnClickPendingIntent(R.id.ll_beauty,
                getPendingIntent(R.id.ll_beauty));
        remoteView.setOnClickPendingIntent(R.id.ll_edit_shop,
                getPendingIntent(R.id.ll_edit_shop));
        remoteView.setOnClickPendingIntent(R.id.ll_group,
                getPendingIntent(R.id.ll_group));
        remoteView.setOnClickPendingIntent(R.id.ll_favorite,
                getPendingIntent(R.id.ll_favorite));
    }

    private static PendingIntent getPendingIntent(int resID) {
        Context context = BaseApplication.getInstance().getApplicationContext();
        String uri_content = Const.PACKAGE_NAME;

        if (resID == R.id.ll_seek) {
            uri_content = Const.PACKAGE_NAME.concat(".ui.activity.SeekInputActivity");
        } else if (resID == R.id.ll_beauty) {
            uri_content = Const.PACKAGE_NAME.concat(".ui.activity.MainActivity.HomeNewFragment.BeautyFragment");
        } else if (resID == R.id.ll_edit_shop) {
            uri_content = Const.PACKAGE_NAME.concat(".ui.activity.UpdateFoodActivity");
        } else if (resID == R.id.ll_favorite) {
            uri_content = Const.PACKAGE_NAME.concat(".ui.activity.FavoriteActivity");
        } else if (resID == R.id.ll_group) {
            uri_content = Const.PACKAGE_NAME;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(uri_content));
        intent.setComponent(new ComponentName(context, FunctionWidgetProvider.class));

        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= 31) {
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    301989888);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
        return pendingIntent;
    }

}