package com.example.lib_common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_common.R;
import com.example.lib_utils.Res;

/**
 * Created by 王鑫哲 on 2022/10/15 8:16
 * E-mail: User_wang_178@163.com
 * Ps: 渐变View
 */
public class GradientView extends View {

    private final int mDirection;

    public GradientView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientView);
        int startColor = typedArray.getColor(R.styleable.GradientView_gv_start_color, Res.color(R.color.black));
        int endColor = typedArray.getColor(R.styleable.GradientView_gv_end_color, Res.color(R.color.white));
        mDirection = typedArray.getInt(R.styleable.GradientView_gv_direction, 0);
        typedArray.recycle();

        GradientDrawable grad = new GradientDrawable(getDirection(), new int[]{startColor, endColor});
        setBackground(grad);
    }

    private GradientDrawable.Orientation getDirection() {
        switch (mDirection) {
            case 1:
                return GradientDrawable.Orientation.TR_BL;
            case 2:
                return GradientDrawable.Orientation.RIGHT_LEFT;
            case 3:
                return GradientDrawable.Orientation.BR_TL;
            case 4:
                return GradientDrawable.Orientation.BOTTOM_TOP;
            case 5:
                return GradientDrawable.Orientation.BL_TR;
            case 6:
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case 7:
                return GradientDrawable.Orientation.TL_BR;
            default:
                return GradientDrawable.Orientation.TOP_BOTTOM;
        }
    }
}
