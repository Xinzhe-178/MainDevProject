package com.example.networkpro.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_utils.DensityUtils;
import com.example.networkpro.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/11/23 上午 11:30
 * E-mail: User_wang_178@163.com
 * Ps: 使用时调用setIndexNum()设置数量 最后调用build()实现绘制View
 */
public class GuidePageIndicatorView extends FrameLayout {
    /**
     * 默认选中位置
     */
    private int defSelectIndex = 0;
    /**
     * 当前选中位置
     */
    private int selectIndex = 0;

    private int indexNum = 3;

    private List<View> mItemList = new ArrayList<>();

    public GuidePageIndicatorView setIndexNum(int num) {
        this.indexNum = num;
        return this;
    }

    public GuidePageIndicatorView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void build() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);

        for (int i = 0; i < indexNum; i++) {
            mItemList.add(getItemView());
            linearLayout.addView(mItemList.get(i));
        }

        setIndicatorColor(defSelectIndex);
        addView(linearLayout);
    }

    private View getItemView() {
        View view = new View(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(DensityUtils.dp(10), DensityUtils.dp(10));
        view.setBackgroundResource(R.drawable.guide_page_indicator_view_shap_no);
        layoutParams.setMargins(DensityUtils.dp(8), 0, DensityUtils.dp(8), 0);
        view.setLayoutParams(layoutParams);
        return view;
    }

    /**
     * 此处必须先设置上次选中再设置当前选中
     * 因为如果默认为0 的话 会被替换 嘿嘿
     *
     * @param pos 当前选中展示的位置
     */
    public void setIndicatorColor(int pos) {
        selectIndex = pos;
        if (mItemList != null && mItemList.size() > 0) {
            mItemList.get(defSelectIndex).setBackgroundResource(R.drawable.guide_page_indicator_view_shap_no);
            mItemList.get(pos).setBackgroundResource(R.drawable.guide_page_indicator_view_shap_on);
        }
        defSelectIndex = pos;
    }
}
