package com.example.lib_common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by 王鑫哲 on 2022/2/21 下午 08:54
 * E-mail: User_wang_178@163.com
 * Ps: 仿IOS开关View 有动画
 */
public class SwitchButton extends View {
    private final Paint mPaint = new Paint();
    private static final double MBTNHEIGHT = 0.55;
    private static final int OFFSET = 3;
    private int mHeight;
    private float mAnimate = 0L;
    //此处命名不规范，目的和Android自带的switch有相同的用法
    private boolean checked = false;
    private float mScale;
    private int mSelectColor;
    private OnCheckedChangeListener mOnCheckedChangeListener;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchButton);
//
//        mSelectColor = typedArray.getColor(R.styleable.SwitchButton_buttonColor, Color.parseColor("#2eaa57"));
//        typedArray.recycle();
        mSelectColor = Color.parseColor("#2eaa57");
    }

    /**
     * @param widthMeasureSpec
     * @param heightMeasureSpec 高度是是宽度的0.55倍
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = (int) (MBTNHEIGHT * width);
        setMeasuredDimension(width, mHeight);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mSelectColor);
        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        RectF rectf = new RectF(rect);
        //绘制圆角矩形
        canvas.drawRoundRect(rectf, mHeight / 2, mHeight / 2, mPaint);

//以下save和restore很重要，确保动画在中间一层 ，如果大家不明白，可以去搜下用法

        canvas.save();
        mPaint.setColor(Color.parseColor("#e6e6e6"));
        mAnimate = mAnimate - 0.1f > 0 ? mAnimate - 0.1f : 0; // 动画标示 ，重绘10次，借鉴被人的动画
        mScale = (!checked ? 1 - mAnimate : mAnimate);
        canvas.scale(mScale, mScale, getWidth() - getHeight() / 2, rect.centerY());
        //绘制缩放的灰色圆角矩形
        canvas.drawRoundRect(rectf, mHeight / 2, mHeight / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        Rect rect_inner = new Rect(OFFSET, OFFSET, getWidth() - OFFSET, getHeight() - OFFSET);
        RectF rect_f_inner = new RectF(rect_inner);
        //绘制缩放的白色圆角矩形，和上边的重叠实现灰色边框效果
        canvas.drawRoundRect(rect_f_inner, (mHeight - 8) / 2, (mHeight - 8) / 2, mPaint);
        canvas.restore();

        //中间圆形平移
        int sWidth = getWidth();
        int bTranslateX = sWidth - getHeight();
        final float translate = bTranslateX * (!checked ? mAnimate : 1 - mAnimate);
        canvas.translate(translate, 0);

        //以下两个圆带灰色边框
        mPaint.setColor(Color.parseColor("#e6e6e6"));
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2 - OFFSET / 2, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(getHeight() / 2, getHeight() / 2, getHeight() / 2 - OFFSET, mPaint);

        if (mScale > 0) {
            mPaint.reset();
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mAnimate = 1;
                checked = !checked;

                if (mOnCheckedChangeListener != null) {
                    mOnCheckedChangeListener.OnCheckedChanged(checked);
                }
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    /**
     * 自加方法 setChecked只为初始化状态
     * 该方法 为刷新选中状态
     *
     * @param checked
     * @param isCall  是否需要回调 true 刷新后会走OnCheckedChangeListener回调 反之
     */
    public void refreshChecked(boolean checked, boolean isCall) {
        setChecked(checked);

        mAnimate = 1;
        checked = !checked;

        if (mOnCheckedChangeListener != null && isCall) {
            mOnCheckedChangeListener.OnCheckedChanged(checked);
        }
        invalidate();
    }

    public OnCheckedChangeListener getmOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    public void setmOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    public interface OnCheckedChangeListener {
        void OnCheckedChanged(boolean isChecked);
    }

//    作者：酷似约德尔
//    链接：https://www.jianshu.com/p/3ca92351cb21
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
