package com.example.lib_utils;

import android.app.Application;

/**
 * Created by 王鑫哲 on 2022/4/2 下午 02:16
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class UtilApplication extends Application {

    private static UtilApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init();
    }

    public static UtilApplication getInstance() {
        return application;
    }

    public abstract void init();
}
