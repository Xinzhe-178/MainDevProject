package com.example.lib_utils;

import android.view.View;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * Created by 王鑫哲 on 2021/10/25 下午 06:18
 * E-mail: User_wang_178@163.com
 * Ps: 防抖动点击Utils
 */
public class AntiShakeUtils {

    private final static long INTERNAL_TIME = 500;

    public static boolean isInvalidClick(@NonNull View target) {
        return isInvalidClick(target, INTERNAL_TIME);
    }

    public static boolean isInvalidClick(@NonNull View target, @IntRange(from = 0) long internalTime) {
        long curTimeStamp = System.currentTimeMillis();
        long lastClickTimeStamp;
        Object o = target.getTag(R.id.last_click_time);
        if (o == null) {
            target.setTag(R.id.last_click_time, curTimeStamp);
            return false;
        }
        lastClickTimeStamp = (Long) o;
        boolean isInvalid = curTimeStamp - lastClickTimeStamp < internalTime;
        if (!isInvalid) {
            target.setTag(R.id.last_click_time, curTimeStamp);
        }
        return isInvalid;
    }
}
