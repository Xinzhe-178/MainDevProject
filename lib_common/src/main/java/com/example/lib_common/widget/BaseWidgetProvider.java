package com.example.lib_common.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.lib_utils.LogUtils;

public abstract class BaseWidgetProvider extends AppWidgetProvider {

    protected Context mContext;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        if (getWidgetManager() != null) {
            getWidgetManager().createRemoteView(appWidgetManager);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        LogUtils.PrintE("BaseWidgetProvider-> onUpdate");
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        if (context == null || intent == null || TextUtils.isEmpty(intent.getAction())) {
            return;
        }
        onReceive(context, intent, intent.getAction(), intent.getDataString());
        super.onReceive(context, intent);

        LogUtils.PrintE("BaseWidgetProvider-> onReceive");
    }

    /**
     * onReceive 子类实现不需要做判空 只会在参数不为空的情况下 会调用该方法
     *
     * @param intent
     * @param action
     * @param intentData
     */
    protected abstract void onReceive(Context context, Intent intent, String action, String intentData);

    /**
     * widgetManager
     *
     * @return
     */
    protected abstract IWidgetManager getWidgetManager();

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
        LogUtils.PrintE("BaseWidgetProvider-> onAppWidgetOptionsChanged");
    }
}
