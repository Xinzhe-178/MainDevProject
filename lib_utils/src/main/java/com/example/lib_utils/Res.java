package com.example.lib_utils;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;

/**
 * Created by 王鑫哲 on 2022/5/13 下午 10:28
 * E-mail: User_wang_178@163.com
 * Ps:
 */
@SuppressLint("UseCompatLoadingForDrawables")
public final class Res {

    public static int color(int color) {
        return UtilApplication.getInstance().getResources().getColor(color);
    }

    public static Drawable Drawable(int drawable) {
        return UtilApplication.getInstance().getResources().getDrawable(drawable);
    }

    public static View getView(@LayoutRes int layout) {
        return LayoutInflater.from(UtilApplication.getInstance()).inflate(layout, null, false);
    }
}
