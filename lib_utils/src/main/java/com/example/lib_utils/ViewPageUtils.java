package com.example.lib_utils;

import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import java.lang.reflect.Field;

/**
 * Created by 王鑫哲 on 2022/8/7 11:32
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ViewPageUtils {
    /**
     * 设置ViewPage动画时长 未进行设置会导致setCurrentItem 切换时效果不好(太快)
     */
    public static void setAnimDuration(@NonNull ViewPager viewPager, int duration) {
        try {
            Field mField = viewPager.getClass().getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller s = new FixedSpeedScroller(viewPager.getContext(), new AccelerateInterpolator());
            s.setDuration(duration);
            mField.set(viewPager, s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
