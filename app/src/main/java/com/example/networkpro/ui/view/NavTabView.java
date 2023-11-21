package com.example.networkpro.ui.view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;

/**
 * Created by 王鑫哲 on 2022/8/6 12:10
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class NavTabView extends LinearLayout {

    private int[] tabDefIcons = {};

    private int[] tabSelIcons = {};

    private String[] tabDefTitles = {};

    private String[] tabSelTitles = {};

    private final Context mContext;

    private int titleDefSize = 16;

    private int titleSelSize = 16;

    @ColorRes
    private int titleDefColor = R.color.black;

    @ColorRes
    private int titleSelColor = R.color.black;

    private final int iconsDefWidth = dp(30);

    private final int iconsDefHeight = dp(30);

    private boolean selIsBold;

    private int defSelIndex;

    private int lastIndex;

    private boolean clickIsShowAnim = true;

    private OnNavTabClickListener mOnNavTabClickListener;

    public void setOnNavTabClickListener(OnNavTabClickListener onNavTabClickListener) {
        mOnNavTabClickListener = onNavTabClickListener;
    }

    public NavTabView(Context context) {
        this(context, null);
    }

    public NavTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setOrientation(LinearLayout.HORIZONTAL);
    }

    public NavTabView setDefTabIcons(int... defIcons) {
        this.tabDefIcons = defIcons;
        return this;
    }

    public NavTabView setSelTabIcons(int... selIcons) {
        this.tabSelIcons = selIcons;
        return this;
    }

    public NavTabView setTabDefTitles(String... defTitles) {
        this.tabDefTitles = defTitles;
        return this;
    }

    public NavTabView setTabSelTitles(String... selTitles) {
        this.tabSelTitles = selTitles;
        return this;
    }

    public NavTabView setDefTitleSize(int defSize) {
        this.titleDefSize = defSize;
        return this;
    }

    public NavTabView setSelTitleSize(int selSize) {
        this.titleSelSize = selSize;
        return this;
    }

    public NavTabView setTitleDefColor(@ColorRes int defColor) {
        this.titleDefColor = defColor;
        return this;
    }

    public NavTabView setTitleSelColor(@ColorRes int selColor) {
        this.titleSelColor = selColor;
        return this;
    }

    public NavTabView setSelIsBold(boolean isBold) {
        this.selIsBold = isBold;
        return this;
    }

    public NavTabView setDefSelIndex(int index) {
        this.defSelIndex = index;
        return this;
    }

    public NavTabView getGroupView() {
        return this;
    }

    public void build() {
        drawTab();
    }

    private void drawTab() {
        if (tabDefIcons == null || tabDefIcons.length == 0 || tabDefTitles == null || tabDefTitles.length == 0 || (tabDefIcons.length != tabDefTitles.length) || tabSelIcons == null || tabSelIcons.length == 0) {
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

        TextView textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER);
        setTitleStyle(textView, tabDefTitles[i], titleDefSize, false, titleDefColor);

        LayoutParams linearLayoutParams = new LayoutParams(0, GridLayout.LayoutParams.WRAP_CONTENT, 1.0f);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.addView(imageView);
        linearLayout.addView(textView);
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
        if (clickIsShowAnim) {
            startAnimator(getChildAt(i));
        }
    }

    private void refreshViewState(View view, int index, boolean isSel) {
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;

            ImageView icon = (ImageView) linearLayout.getChildAt(0);
            icon.setImageResource(isSel ? tabSelIcons[index] : tabDefIcons[lastIndex]);

            TextView textView = (TextView) linearLayout.getChildAt(1);
            if (tabSelTitles != null && tabSelTitles.length == tabDefIcons.length) {
                if (isSel) {
                    setTitleStyle(textView, tabSelTitles[index], titleSelSize, selIsBold, titleSelColor);
                } else {
                    setTitleStyle(textView, tabDefTitles[lastIndex], titleDefSize, false, titleDefColor);
                }
            }
        }
    }

    private void startAnimator(View imageView) {
        AnimatorSet animatorSetZoom = new AnimatorSet();//组合动画
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 0.8f, 1.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 0.8f, 1.2f, 1f);
        animatorSetZoom.setDuration(150);
        animatorSetZoom.setInterpolator(new AccelerateInterpolator());
        animatorSetZoom.play(scaleX).with(scaleY);//两个动画同时开始
        animatorSetZoom.start();
    }

    public int dp(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    public interface OnNavTabClickListener {
        void onTabClick(int lastIndex, int currentIndex);
    }
}
