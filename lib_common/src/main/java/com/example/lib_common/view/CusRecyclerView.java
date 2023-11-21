package com.example.lib_common.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 王鑫哲 on 2022/5/27 下午 08:43
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CusRecyclerView extends RecyclerView {

    public CusRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CusRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
