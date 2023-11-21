package com.example.lib_utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * description：水印类
 * author：liminzhao
 * date：2021.2.26
 */
public class WatermarkUtils {

    private static final float TEXT_SIZE = 16;

    private static final String TEXT_COLOR = "#000000";

    private static final float TEXT_ROTATION = -45;

    private static final boolean BUILD_TYPE_DEBUG = TextUtils.equals(BuildConfig.BUILD_TYPE, "debug");

    /**
     * 显示水印，铺满整个页面
     */
    public static void show(Activity activity) {
        if (BUILD_TYPE_DEBUG) {
            WatermarkDrawable drawable = new WatermarkDrawable();
            ViewGroup rootView = activity.findViewById(android.R.id.content);
            FrameLayout layout = new FrameLayout(activity);
            layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            layout.setBackground(drawable);
            layout.setAlpha(0.08f);
            rootView.addView(layout);
        }
    }

    private static class WatermarkDrawable extends Drawable {
        @Override
        public void draw(@NonNull Canvas canvas) {
            String mText = "测试版本，严禁外传" + "厂商：" + DeviceUtil.getManufacturer();

            int width = getBounds().width();
            int height = getBounds().height();
            int diagonal = (int) Math.sqrt(width * width + height * height); // 对角线的长度

            TextPaint mPaint = new TextPaint();
            mPaint.setColor(Color.parseColor(TEXT_COLOR));
            mPaint.setTextSize(spToPx(TEXT_SIZE));
            mPaint.setAntiAlias(true);
            int watermarkWidth = (int) mPaint.measureText("测试版本，严禁外传");

            // 以对角线的长度来做高度，保证竖屏和横屏整个屏幕都能布满水印
            for (int positionY = 0; positionY <= diagonal; positionY += diagonal / 5) {
                for (float positionX = -watermarkWidth * 0.5f; positionX < width; positionX += watermarkWidth * 1.3) {
                    canvas.save();
                    StaticLayout staticLayout = new StaticLayout(mText, mPaint, watermarkWidth,
                            Layout.Alignment.ALIGN_NORMAL, 1.0f, 3.0f, false);
                    canvas.translate(positionX, positionY);
                    canvas.rotate(TEXT_ROTATION);
                    staticLayout.draw(canvas);
                    canvas.restore();
                }
            }
        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

    }

    private static int spToPx(float spValue) {
        float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
