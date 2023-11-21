package com.example.networkpro.ui.adapter.Itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_utils.DensityUtils;

/**
 * Created by 王鑫哲 on 2022/4/7 下午 05:42
 * E-mail: User_wang_178@163.com
 * Ps: 保证左中右边距一样Decoration
 */
public class SeekItemDecoration extends RecyclerView.ItemDecoration {

    private final int mDp;

    public SeekItemDecoration(int dp) {
        mDp = DensityUtils.dp(dp);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildLayoutPosition(view) % 2 == 0) {
            //左边一列
            outRect.set(mDp, 0, 0, 0);
        } else {
            //右边一列
            outRect.set(0, 0, mDp, 0);
        }
    }
}
