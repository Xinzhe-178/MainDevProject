package com.example.lib_utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.WindowManager;

import androidx.fragment.app.FragmentActivity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: dp、px、sp相互转换方法工具类
 */
public class DensityUtils {

    /**
     * 屏幕密度,系统源码注释不推荐使用
     */
    public static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    /**
     * 简化版的dip2px，仿kotlin的Anko写法
     * 多次使用的分辨率采用静态初始化，加快点运行速度
     *
     * @return dp转化的px值
     */
    public static int dp(float dp) {
        return (int) (dp * DENSITY + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    @Deprecated
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        return (int) (pxValue / DENSITY + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static float div(int v1, int v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }


    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    @SuppressLint("PrivateApi")
    public static int getStatusHeight() {
        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(Objects.requireNonNull(clazz.getField("status_bar_height")
                    .get(object)).toString());
            statusHeight = UtilApplication.getInstance().getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogUtils.PrintE("statusHeight--->" + statusHeight);
        return statusHeight < 0 ? getStateBar2() : statusHeight;
    }

    /**
     * 针对某些特殊机型获取不到状态栏高度第二种方式
     *
     * @return 状态栏高度
     */
    private static int getStateBar2() {
        int statusBarHeight = -1;
        int resourceId = UtilApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = UtilApplication.getInstance().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 隐藏状态栏
     */
    public static void hideStatusBar(FragmentActivity activity) {
        if (activity != null) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 获取屏幕宽度
     */
    public static int getWidth() {
        return UtilApplication.getInstance().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getHeight() {
        return UtilApplication.getInstance().getResources().getDisplayMetrics().heightPixels;
    }
}
