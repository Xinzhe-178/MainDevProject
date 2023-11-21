package com.example.lib_common.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by 王鑫哲 on 2022/12/15 19:26
 * E-mail: User_wang_178@163.com
 * Ps: 滚动TexView
 * xml设置以下属性
 * android:marqueeRepeatLimit="marquee_forever"
 * android:ellipsize="marquee"
 * android:singleLine="true"
 */
@SuppressLint("AppCompatCustomView")
public class RollTextView extends TextView {

    /**
     * 显示不全时 是否自动滚动
     */
    private boolean isFocused = true;

    public RollTextView(@NonNull Context context) {
        super(context);
    }

    public RollTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RollTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setIsFocused(boolean isFocused) {
        this.isFocused = isFocused;
    }

    @Override
    public boolean isFocused() {
        return isFocused;
    }
}
