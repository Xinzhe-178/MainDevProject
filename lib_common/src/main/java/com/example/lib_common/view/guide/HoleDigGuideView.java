/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.example.lib_common.view.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.lib_utils.DensityUtils;

import java.util.ArrayList;

public class HoleDigGuideView extends RelativeLayout {
    private Rect backgroundRect;
    private Paint backgroundPaint;
    private Xfermode drawMode;
    private Xfermode clearMode;

    private final ArrayList<Hole> holes = new ArrayList<>();

    public HoleDigGuideView(Context context) {
        super(context, null);
        init();
    }

    public HoleDigGuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HoleDigGuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);

        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setStrokeCap(Paint.Cap.ROUND);
        backgroundPaint.setColor(Color.parseColor("#CC000000"));
        drawMode = backgroundPaint.getXfermode();
        clearMode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
        backgroundRect = new Rect(0, 0, DensityUtils.getWidth(), DensityUtils.getHeight() * 2);
    }

    public void addHole(Hole hole) {
        hole.rectF = new RectF(hole.left, hole.top, hole.right, hole.bottom);
        holes.add(hole);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        backgroundPaint.setXfermode(drawMode);
        canvas.drawRect(backgroundRect, backgroundPaint);

        backgroundPaint.setXfermode(clearMode);
        for (Hole hole : holes) {
            canvas.drawRoundRect(hole.rectF, hole.roundX, hole.roundY, backgroundPaint);
        }
    }

    public static class Hole {
        private final int left;
        private final int top;
        private final int right;
        private final int bottom;
        private final int roundX;
        private final int roundY;
        private RectF rectF;

        public Hole(int left, int top, int right, int bottom, int roundX, int roundY) {
            this.left = left;
            this.top = top;
            this.right = right;
            this.bottom = bottom;
            this.roundX = roundX;
            this.roundY = roundY;
        }
    }
}
