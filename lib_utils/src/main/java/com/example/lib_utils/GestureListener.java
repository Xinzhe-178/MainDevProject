package com.example.lib_utils;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by 王鑫哲 on 2023/6/25 4:42 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    /**
     * 定义滑动的最小距离
     */
    private final int MIN_DISTANCE;

    private final GestureListenerCall mGestureListenerCall;

    public GestureListener(int minDistance, GestureListenerCall gestureListenerCall) {
        MIN_DISTANCE = minDistance;
        mGestureListenerCall = gestureListenerCall;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (mGestureListenerCall == null) {
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        if (e1.getX() - e2.getX() > MIN_DISTANCE) {
            // 左滑
            mGestureListenerCall.onLeftSlide();
        } else if (e2.getX() - e1.getX() > MIN_DISTANCE) {
            // 右滑
            mGestureListenerCall.onRightSlide();
        } else if (e1.getY() - e2.getY() > MIN_DISTANCE) {
            // 上滑
            mGestureListenerCall.onTopSlide();
        } else if (e2.getY() - e1.getY() > MIN_DISTANCE) {
            // 下滑
            mGestureListenerCall.onBottomSlide();
        }
        return true;
    }

    public interface GestureListenerCall {
        default void onLeftSlide() {
        }

        default void onTopSlide() {
        }

        default void onRightSlide() {
        }

        default void onBottomSlide() {
        }
    }
}
