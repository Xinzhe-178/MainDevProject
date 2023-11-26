package com.example.lib_common.manage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.lib_common.BaseApplication;
import com.example.lib_utils.UtilApplication;

import java.util.List;

/**
 * Created by 王鑫哲 on 2023/5/12 3:50 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ContextManager {

    public static Activity getTopActivity() {
        return getAllActivityManager().getTopActivity();
    }

    public static List<Activity> getAllActivity() {
        return getAllActivityManager().getAllActivity();
    }

    public static Context getContext() {
        return getBaseApplication().getApplicationContext();
    }

    public static AllActivityManager getAllActivityManager() {
        return getBaseApplication().getActivityManager();
    }

    public static BaseApplication getBaseApplication() {
        return (BaseApplication) getUtilApplication();
    }

    public static UtilApplication getUtilApplication() {
        return UtilApplication.getInstance();
    }

    public static void exitApp() {
        getAllActivityManager().finishAll();
        System.exit(0);
    }

    public static void restartApp() {
        Context context = ContextManager.getContext();
        Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }
}
