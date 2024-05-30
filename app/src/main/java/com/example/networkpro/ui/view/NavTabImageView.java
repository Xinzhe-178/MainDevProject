package com.example.networkpro.ui.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_utils.ToastUtils;

/**
 * Created by 王鑫哲 on 2022/8/6 12:10
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class NavTabImageView extends LinearLayout {

    private int[] tabDefIcons = {};

    private int[] tabSelIcons = {};

    private final Context mContext;

    private final int iconsDefWidth = dp(40);

    private final int iconsDefHeight = dp(40);

    private int defSelIndex;

    private int lastIndex;

    private OnNavTabClickListener mOnNavTabClickListener;

    public void setOnNavTabClickListener(OnNavTabClickListener onNavTabClickListener) {
        mOnNavTabClickListener = onNavTabClickListener;
    }

    public NavTabImageView(Context context) {
        this(context, null);
    }

    public NavTabImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public NavTabImageView setDefTabIcons(int... defIcons) {
        this.tabDefIcons = defIcons;
        return this;
    }

    public NavTabImageView setSelTabIcons(int... selIcons) {
        this.tabSelIcons = selIcons;
        return this;
    }

    public NavTabImageView setDefSelIndex(int index) {
        this.defSelIndex = index;
        return this;
    }

    public NavTabImageView getGroupView() {
        return this;
    }

    public void build() {
        drawTab();
    }

    private void drawTab() {
        if (tabDefIcons == null || tabDefIcons.length == 0 || tabSelIcons == null || tabSelIcons.length == 0) {
            ToastUtils.show("tab初始化有误");
            return;
        }
        for (int i = 0; i < tabDefIcons.length; i++) {
            addView(getView(i));
        }
        lastIndex = defSelIndex;
        refreshViewState(getChildAt(defSelIndex), defSelIndex, true);
    }

    private View getView(int i) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(mContext);
        LayoutParams imageViewParams = new LayoutParams(iconsDefWidth, iconsDefHeight);
        imageView.setLayoutParams(imageViewParams);
        imageView.setImageResource(tabDefIcons[i]);

        LayoutParams linearLayoutParams = new LayoutParams(0, GridLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.addView(imageView);
        clickListener(linearLayout, i);
        return linearLayout;
    }

    private void setTitleStyle(TextView textView, String text, int size, boolean isBold, @ColorRes int color) {
        textView.setText(text);
        textView.setTextSize(size);
        textView.getPaint().setFakeBoldText(isBold);
        textView.setTextColor(mContext.getColor(color));
    }

    private void clickListener(@NonNull View view, int i) {
        view.setOnClickListener(view1 -> extractedSelectClick(i));
    }

    /**
     * 代码设置选中Tab 模拟点击切换逻辑
     */
    public void setSelectTab(int i) {
        if (i < 0 || i > getChildCount()) {
            ToastUtils.show("下标越界，无效");
            return;
        }
        extractedSelectClick(i);
    }

    /**
     * 点击逻辑提取
     *
     * @param i
     */
    private void extractedSelectClick(int i) {
        if (mOnNavTabClickListener != null) {
            mOnNavTabClickListener.onTabClick(lastIndex, i);
        }
        refreshViewState(getChildAt(lastIndex), i, false);
        refreshViewState(getChildAt(i), i, true);
        lastIndex = i;
    }

    private void refreshViewState(View view, int index, boolean isSel) {
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;

            ImageView icon = (ImageView) linearLayout.getChildAt(0);
            icon.setImageResource(isSel ? tabSelIcons[index] : tabDefIcons[lastIndex]);
        }
    }

    public int dp(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    public interface OnNavTabClickListener {
        void onTabClick(int lastIndex, int currentIndex);
    }
}
