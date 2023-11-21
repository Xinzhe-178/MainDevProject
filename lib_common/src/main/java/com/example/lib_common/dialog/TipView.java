package com.example.lib_common.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.example.lib_common.BaseApplication;
import com.example.lib_common.R;
import com.example.lib_common.databinding.CommonTipDialogLayoutBinding;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/12/15 20:49
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class TipView {

    @SuppressLint("StaticFieldLeak")
    private static final TipView Instance;

    /**
     * 显示时长为5s
     */
    private static final int DURATION = 4000;
    /**
     * 解决弹出重复问题标志
     */
    private boolean flag = false;
    /**
     * 显示View集合
     */
    private final List<View> mViewList = new ArrayList<>();
    /**
     * tip滑动事件处理参数
     */
    private float mPosX;
    private float mPosY;
    private float mCurPosX;
    private float mCurPosY;

    static {
        Instance = new TipView();
    }

    public static TipView getInstance() {
        return Instance;
    }

    private View initView(String title, String desc) {
        BaseApplication instance = (BaseApplication) BaseApplication.getInstance();
        Activity activity = instance.getActivityManager().getTopActivity();
        CommonTipDialogLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.common_tip_dialog_layout, null, false);
        binding.tvTitle.setText(title);
        binding.tvDesc.setText(desc);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = DensityUtils.getStatusHeight() - DensityUtils.dp(10);
        binding.getRoot().setLayoutParams(layoutParams);
        View view = binding.getRoot();
        distributeEvent(view);
        return view;
    }

    /**
     * 处理Tip滑动事件
     * 如上滑关闭
     * 需要注意 当前的滑动时间处理待完善 和点击时间冲突 所以还没有点击事件
     * 处理完滑动事件后 要将这几个参数清空，否则下次点击时 还会走上次滑动的逻辑
     * 当前处理方式 点击后也回走其中一个滑动逻辑 但不是上滑
     *
     * @param view
     */
    @SuppressLint("ClickableViewAccessibility")
    private void distributeEvent(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPosX = event.getX();
                    mPosY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCurPosX = event.getX();
                    mCurPosY = event.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    float Y = mCurPosY - mPosY;
                    float X = mCurPosX - mPosX;
                    if (Math.abs(Y) > Math.abs(X)) {
                        if (Y > 0) {
                            LogUtils.PrintE("------> slideDown");
                        } else {
                            LogUtils.PrintE("------> slideUp");

                            mPosX = 0;
                            mPosY = 0;
                            mCurPosX = 0;
                            mCurPosY = 0;

                            exitAnim(view);
                        }
                    } else {
                        if (X > 0) {
                            LogUtils.PrintE("------> slideRight");
                        } else {
                            LogUtils.PrintE("------> slideLeft");
                        }
                    }
                    break;
            }
            return true;
        });
    }

    public void start(String title, String desc) {
        mViewList.add(initView(title, desc));
        if (!flag) {
            startShow(mViewList.get(mViewList.size() > 1 ? mViewList.size() - 1 : 0));
        }
    }

    /**
     * 退出动画 用于手动上滑关闭tip
     * 实现：先清除当前tip的动画 再添加一个新的退出动画
     * 不可以直接删除tipView 这样会突然消失 效果很差
     *
     * @param view
     */
    private void exitAnim(View view) {
        view.clearAnimation();

        /*创建属性动画,从下到上*/
        ObjectAnimator bottomToTop = ObjectAnimator.ofFloat(view, "translationY", 0, -dp2px(80)).setDuration(500);
        /*初始化动画组合器*/
        AnimatorSet animator = new AnimatorSet();
        /*组合动画*/
        animator.play(bottomToTop).after(0);
        /*添加动画结束回调*/
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                closeTipView(view);
            }
        });
        /*启动动画*/
        animator.start();
    }

    /**
     * 默认View添加一个完整动画(显示&隐藏)
     *
     * @param view
     */
    private void startShow(@NonNull View view) {
        flag = true;
        /*创建属性动画,从下到上*/
        ObjectAnimator bottomToTop = ObjectAnimator.ofFloat(view, "translationY", 0, -dp2px(80)).setDuration(500);
        /*创建属性动画,从上到下*/
        ObjectAnimator topToBottom = ObjectAnimator.ofFloat(view, "translationY", -dp2px(80), 0).setDuration(500);
        /*初始化动画组合器*/
        AnimatorSet animator = new AnimatorSet();
        /*组合动画*/
        animator.play(bottomToTop).after(topToBottom).after(DURATION);
        /*添加动画结束回调*/
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                closeTipView(view);
            }
        });
        /*添加View到当前显示的Activity*/
        show(view);
        /*启动动画*/
        animator.start();
    }

    private void closeTipView(View view) {
        /*删除View*/
        hide(view);

        // 在当前关闭后 展示下个弹窗
        flag = false;
        mViewList.remove(view);
        if (mViewList.size() > 0) {
            startShow(mViewList.get(mViewList.size() > 1 ? mViewList.size() - 1 : 0));
        }
    }

    private void show(@NonNull View view) {
        BaseApplication instance = (BaseApplication) BaseApplication.getInstance();
        Activity activity = instance.getActivityManager().getTopActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        try {
            // 这里必须要try catch  IllegalStateException: The specified child already has a parent. You must call removeView() on the child's parent first.
            decorView.addView(view);
        } catch (Throwable throwable) {

        }
    }

    public void hide() {
        if (mViewList.size() > 0) {
            for (View view : mViewList) {
                hide(view);
            }
        }
    }

    public void hide(@NonNull View view) {
        BaseApplication instance = (BaseApplication) BaseApplication.getInstance();
        Activity activity = instance.getActivityManager().getTopActivity();
        if (activity == null || activity.isFinishing()) {
            return;
        }
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (decorView.indexOfChild(view) != -1) {
            decorView.removeView(view);
        }
    }

    private int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}