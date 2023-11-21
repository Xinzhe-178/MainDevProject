package com.example.lib_common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import java.util.Hashtable;
import java.util.Map;

public class AutoNextLineLinearLayout extends LinearLayout {

    private final Map<View, Position> map = new Hashtable<>();

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public AutoNextLineLinearLayout(Context context) {
        this(context, null);
    }

    public AutoNextLineLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mWidth = MeasureSpec.getSize(widthMeasureSpec);
        int mX = 0;
        int mY = 0;
        int mLeft;
        int mRight;
        int mTop = 5;
        int mBottom = 0;

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            child.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            // 此处增加onlayout中的换行判断，用于计算所需的高度
            int childw = child.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            int childh = child.getMeasuredHeight();
            mLeft = mX;
            mX += childw; // 将每次子控件宽度进行统计叠加，如果大于设定的高度则需要换行，高度即Top坐标也需重新设置

            Position position = new Position();

            mRight = mLeft + child.getMeasuredWidth();
            if (mX >= mWidth) {
                mX = childw;
                mY += childh;
                mLeft = 0;
                mRight = mLeft + child.getMeasuredWidth();
                mTop = mY + params.topMargin;
                // PS：如果发现高度还是有问题就得自己再细调了
            }
            mBottom = mTop + child.getMeasuredHeight() + params.bottomMargin;
            mY = mTop; // 每次的高度必须记录 否则控件会叠加到一起
            position.left = mLeft;
            position.top = mTop;
            position.right = mRight;
            position.bottom = mBottom;
            map.put(child, position);

            if (mOnItemClickListener != null) {
                getChildAt(i).setOnClickListener(view -> mOnItemClickListener.onClick(view));

                getChildAt(i).setOnLongClickListener(view -> {
                    mOnItemClickListener.onLongClick(view);
                    return true;
                });
            }
        }
        setMeasuredDimension(mWidth, mBottom);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(0, 0); // default of 1px spacing
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            Position pos = map.get(child);
            if (pos != null) {
                child.layout(pos.left, pos.top, pos.right, pos.bottom);
            }

        }
    }

    private static class Position {
        public int left;
        public int top;
        public int right;
        public int bottom;
    }

    public interface OnItemClickListener {
        void onClick(View view);

        void onLongClick(View view);
    }
}