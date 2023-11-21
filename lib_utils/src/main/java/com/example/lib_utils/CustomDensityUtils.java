package com.example.lib_utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

/**
 * Created by 王鑫哲 on 2022/2/12 下午 03:09
 * E-mail: User_wang_178@163.com
 * Ps: 今日头条适配Utils
 * 链接：https://juejin.cn/post/7054388007373111310
 */
public class CustomDensityUtils {
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;

    /**
     * 今日头条适配方案
     *
     * @param activity
     * @param application
     */
    public static void setCustomDensity(Activity activity, final Application application) {
        //通过资源文件getResources类获取DisplayMetrics
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            //保存之前density值
            sNoncompatDensity = appDisplayMetrics.density;
            //保存之前scaledDensity值，scaledDensity为字体的缩放因子，正常情况下和density相等，但是调节系统字体大小后会改变这个值
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            //监听设备系统字体切换
            application.registerComponentCallbacks(new ComponentCallbacks() {

                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        //调节系统字体大小后改变的值
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                public void onLowMemory() {

                }
            });
        }

        //获取以设计图总宽度360dp下的density值
//        float targetDensity = appDisplayMetrics.widthPixels / 360;
        float targetDensity = appDisplayMetrics.widthPixels / 420;
        //通过计算之前scaledDensity和density的比获得scaledDensity值
        float targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        //获取以设计图总宽度360dp下的dpi值
//        int targetDensityDpi = (int) (160 * targetDensity);
        int targetDensityDpi = (int) (180 * targetDensity);
        //设置系统density值
        appDisplayMetrics.density = targetDensity;
        //设置系统scaledDensity值
        appDisplayMetrics.scaledDensity = targetScaleDensity;
        //设置系统densityDpi值
        appDisplayMetrics.densityDpi = targetDensityDpi;

        //获取当前activity的DisplayMetrics
        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        //设置当前activity的density值
        activityDisplayMetrics.density = targetDensity;
        //设置当前activity的scaledDensity值
        activityDisplayMetrics.scaledDensity = targetScaleDensity;
        //设置当前activity的densityDpi值
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
