package com.example.lib_utils;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by 王鑫哲 on 2022/4/20 下午 06:52
 * E-mail: User_wang_178@163.com
 * Ps: 设置这个类 以达到控制ViewPage动画时长
 */
public class FixedSpeedScroller extends Scroller {

//    private int mDuration = 175;
    private int mDuration = 235;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    /**
     * 设置切换时间
     */
    public void setDuration(int time) {
        mDuration = time;
    }

    public int getDurationX() {
        return mDuration;
    }
}
