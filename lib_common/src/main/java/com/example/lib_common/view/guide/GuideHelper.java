package com.example.lib_common.view.guide;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.lib_common.R;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.RxUtils;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.ToastUtils;
import com.jeremyliao.liveeventbus.LiveEventBus;

public class GuideHelper {

    private static GuideHelper instance = null;
    private Dialog dialog;
    private DialogInterface.OnDismissListener dismissListener;
    /**
     * 类似于 点击【我知道了】View后回调处理相关逻辑
     * 可自由使用
     */
    private OnBindingClickCall onEndViewClick;
    private View mView;

    private onShowInitCall mOnShowInitCall;

    private GuideHelper() {
    }

    public static GuideHelper getInstance() {
        if (instance == null) {
            instance = new GuideHelper();
        }
        return instance;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDismissListener(DialogInterface.OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    public void setOnEndViewClick(OnBindingClickCall onEndViewClick) {
        this.onEndViewClick = onEndViewClick;
    }

    public GuideHelper showGuide(GuideHelperType type, View ufoView, onShowInitCall onShowInitCall) {
        mOnShowInitCall = onShowInitCall;
        return showGuide(type, ufoView);
    }

    public GuideHelper showGuide(GuideHelperType type, View ufoView) {
        mView = ufoView;
        RxUtils.timedTask(dialog == null ? 200 : 0, () -> {
            showGuide(type);
        });
        return instance;
    }

    private void showGuide(GuideHelperType type) {
        switch (type) {
            case HOME_BOTTOM:
                showBottomGuide();
                break;
            case HOM_BOTTOM_ORDER:
                showBottomOrderGuide();
                break;
            case CUS_VIEW_FAVORITE:
                showCusFavoriteGuide();
                break;
            case UPDATE_USER_AVATAR:
                showUserAvatarGuide();
                break;
        }
    }

    private void showUserAvatarGuide() {
        try {
            int[] location = new int[2];
            mView.getLocationOnScreen(location);

            if (location[1] <= 0) {
                ToastUtils.show("location");
                return;
            }
            Activity activity = ContextManager.getTopActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (dialog == null) {
                dialog = new Dialog(activity, R.style.FullDialogTheme);
            }
            if (dismissListener != null) {
                dialog.setOnDismissListener(dismissListener);
            }
            //设置物理返回键触发时不关闭
            dialog.setCancelable(false);
            // 旁百View 可设置点击事件
            final HoleDigGuideView holeDigGuideView = new HoleDigGuideView(activity);
            int margin = 0;
            // 左边距离屏幕位置
            int left = location[0];
            int top = location[1] + margin;
            int right = location[0] + mView.getMeasuredWidth();
            int bottom = top + mView.getMeasuredHeight() - margin;
            // 设置圆角
            int cornerRadius = DensityUtils.dp(40);
            holeDigGuideView.addHole(new HoleDigGuideView.Hole(left, top, right, bottom, cornerRadius, cornerRadius));

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            View view1 = GuideViewController.getView(GuideHelperType.UPDATE_USER_AVATAR);
            // 设置View位置
            int topMargin = top - getViewHeight(view1);
            view1.setY(topMargin);
            holeDigGuideView.addView(view1, layoutParams);
            dialog.setContentView(holeDigGuideView);
            setPromptWin(dialog);
            show(dialog, mOnShowInitCall);
            dismiss(view1.findViewById(R.id.tv_end), () -> {
                ShareData.setShareBooleanData(Const.GuideViewShowConst.MASTER_PAGE_UPDATE_USER_AVATAR, true);
            });
        } catch (Exception e) {
        }
    }

    private void showBottomOrderGuide() {
        try {
            int[] location = new int[2];
            mView.getLocationOnScreen(location);

            if (location[1] <= 0) {
                return;
            }
            Activity activity = ContextManager.getTopActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (dialog == null) {
                dialog = new Dialog(activity, R.style.FullDialogTheme);
            }
            if (dismissListener != null) {
                dialog.setOnDismissListener(dismissListener);
            }
            //设置物理返回键触发时不关闭
            dialog.setCancelable(false);
            final HoleDigGuideView holeDigGuideView = new HoleDigGuideView(activity);
            int margin = 0;
            // 左边距离屏幕位置
            int left = location[0];
            int top = location[1] + margin;
            int right = location[0] + mView.getMeasuredWidth();
            int bottom = top + mView.getMeasuredHeight() - margin;
            // 设置圆角
            int cornerRadius = DensityUtils.dp(18);
            holeDigGuideView.addHole(new HoleDigGuideView.Hole(left, top, right, bottom, cornerRadius, cornerRadius));

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.rightMargin = DensityUtils.dp(20);

            // 设置指向线的位置
            View view1 = GuideViewController.getView(GuideHelperType.HOM_BOTTOM_ORDER);
            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view1.findViewById(R.id.fl_line).getLayoutParams();
            // 将指向线的位置设置到view的中间
            layoutParams1.rightMargin = (right - left) / 2;

            // 设置View位置
            int topMargin = top - getViewHeight(view1);
            view1.setY(topMargin);
            holeDigGuideView.addView(view1, layoutParams);
            dialog.setContentView(holeDigGuideView);
            setPromptWin(dialog);
            show(dialog);
            dismiss(view1.findViewById(R.id.tv_end), () -> {
                // 设置已经弹出并用户已知晓
                ShareData.setShareBooleanData(Const.GuideViewShowConst.HOM_BOTTOM_ORDER, true);
                // 首页最后一个浮层引导关闭
                LiveEventBus.get(EventPath.HOME_BOTTOM_GUIDE_DISMISS).post(true);
            });
        } catch (Exception e) {
        }
    }

    private void showBottomGuide() {
        try {
            int[] location = new int[2];
            mView.getLocationOnScreen(location);

            if (location[1] <= 0) {
                return;
            }
            Activity activity = ContextManager.getTopActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (dialog == null) {
                dialog = new Dialog(activity, R.style.FullDialogTheme);
            }
            if (dismissListener != null) {
                dialog.setOnDismissListener(dismissListener);
            }
            //设置物理返回键触发时不关闭
            dialog.setCancelable(false);
            // 旁百View 可设置点击事件
            final HoleDigGuideView holeDigGuideView = new HoleDigGuideView(activity);
            int margin = 0;
            // 左边距离屏幕位置
            int left = 0;
            int top = location[1] + margin;
            int right = DensityUtils.getWidth();
            int bottom = top + mView.getMeasuredHeight() - margin;
            // 设置圆角
            int cornerRadius = DensityUtils.dp(18);
            holeDigGuideView.addHole(new HoleDigGuideView.Hole(left, top, right, bottom, cornerRadius, cornerRadius));

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

            View view1 = GuideViewController.getView(GuideHelperType.HOME_BOTTOM);
            // 设置View位置
            int topMargin = top - getViewHeight(view1);
            view1.setY(topMargin);
            holeDigGuideView.addView(view1, layoutParams);
            dialog.setContentView(holeDigGuideView);
            setPromptWin(dialog);
            show(dialog);
            view1.findViewById(R.id.tv_end).setOnClickListener(v -> {
                // 设置已经弹出并用户已知晓
                ShareData.setShareBooleanData(Const.GuideViewShowConst.HOME_BOTTOM, true);
                if (onEndViewClick != null) {
                    onEndViewClick.clickCall();
                } else {
                    dismiss();
                }
            });
        } catch (Exception e) {
        }
    }

    private void showCusFavoriteGuide() {
        try {
            int[] location = new int[2];
            mView.getLocationOnScreen(location);

            if (location[1] <= 0) {
                return;
            }
            Activity activity = ContextManager.getTopActivity();
            if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                return;
            }
            if (dialog == null) {
                dialog = new Dialog(activity, R.style.FullDialogTheme);
            }
            if (dismissListener != null) {
                dialog.setOnDismissListener(dismissListener);
            }
            //设置物理返回键触发时不关闭
            dialog.setCancelable(false);
            final HoleDigGuideView holeDigGuideView = new HoleDigGuideView(activity);
            int margin = 0;
            // 左边距离屏幕位置
            int left = location[0];
            int top = location[1] + margin;
            int right = location[0] + mView.getMeasuredWidth();
            int bottom = top + mView.getMeasuredHeight() - margin;
            // 设置圆角
            int cornerRadius = DensityUtils.dp(10);
            holeDigGuideView.addHole(new HoleDigGuideView.Hole(left, top, right, bottom, cornerRadius, cornerRadius));

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.rightMargin = DensityUtils.dp(20);

            // 设置指向线的位置
            View view1 = GuideViewController.getView(GuideHelperType.CUS_VIEW_FAVORITE);
            ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) view1.findViewById(R.id.fl_line).getLayoutParams();
            // 将指向线的位置设置到view的中间
            layoutParams1.rightMargin = (right - left) / 2;

            // 设置View位置
            int topMargin = top - getViewHeight(view1);
            view1.setY(topMargin);
            holeDigGuideView.addView(view1, layoutParams);
            dialog.setContentView(holeDigGuideView);
            setPromptWin(dialog);
            show(dialog);
            dismiss(view1.findViewById(R.id.tv_end), () -> {
                // 设置已经弹出并用户已知晓
                ShareData.setShareBooleanData(Const.GuideViewShowConst.CUS_VIEW_FAVORITE, true);
            });
        } catch (Exception e) {
        }
    }

    private void dismiss(View view) {
        dismiss(view, null);
    }

    private void dismiss(View view, OnBindingClickCall onEndViewClick) {
        if (view != null) {
            view.setOnClickListener(v -> {
                dismiss();
                if (onEndViewClick != null) {
                    onEndViewClick.clickCall();
                }
            });
        }
    }

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void setPromptWin(Dialog dialog) {
        Window window = dialog.getWindow();
        if (window != null) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
                window.setAttributes(lp);
                final View decorView = window.getDecorView();
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
            window.setAttributes(layoutParams);
        }
    }

    public int getViewHeight(View view) {
        if (view == null) {
            return 0;
        }
        if (view.getMeasuredHeight() != 0) {
            return view.getMeasuredHeight();
        }
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    private void show(Dialog dialog) {
        show(dialog, null);
    }

    /**
     * 弹出抽取
     * 如果不控制弹出条件 则直接弹出
     * 如果设置弹出条件 则根据条件弹窗
     *
     * @param dialog
     * @param onShowInitCall
     */
    public void show(Dialog dialog, onShowInitCall onShowInitCall) {
        if (onShowInitCall != null) {
            if (onShowInitCall.isShow()) {
                dialog.show();
            }
        } else {
            dialog.show();
        }
        mOnShowInitCall = null;
    }

    public interface onShowInitCall {
        boolean isShow();
    }
}
