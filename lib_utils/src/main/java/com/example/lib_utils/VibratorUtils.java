package com.example.lib_utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by 王鑫哲 on 2022/2/4 下午 02:46
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class VibratorUtils {
    /**
     * 触感震动
     */
    private static Vibrator mVibrator;

    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    public static void start(Context context) {
        mContext = context;
        if (mVibrator != null) {
            mVibrator.vibrate(new long[]{0, 10, 0, 0}, -1);
        } else {
            init();
            start(context);
        }
    }

    private static void init() {
        mVibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * 停止震动
     */
    public static void stopVibrator() {
        if (mVibrator != null) {
            mVibrator.cancel();
            mVibrator = null;
        }
    }
}
