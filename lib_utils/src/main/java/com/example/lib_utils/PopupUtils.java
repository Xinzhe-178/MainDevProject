package com.example.lib_utils;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.PopupWindow;

/**
 * Created by 王鑫哲 on 2021/7/15 下午 04:53
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class PopupUtils {

    public OnDismiss onDismiss;

    private int location;

    private MyPopup popupWindow;

    private View view;

    private Window window;

    private boolean backgroundIsDarken;

    public View getView() {
        return view;
    }

    public MyPopup getPopupWindow() {
        return popupWindow;
    }

    public PopupUtils(Window window, int layoutId) {
        defaultInit(window, layoutId, GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER, true);
    }

    public PopupUtils(Window window, int layoutId, int layout_height, int location) {
        defaultInit(window, layoutId, GridLayout.LayoutParams.MATCH_PARENT, layout_height, location, true);
    }

    public PopupUtils(Window window, int layoutId, int layout_width, int layout_height, int location) {
        defaultInit(window, layoutId, layout_width, layout_height, location, true);
    }

    public PopupUtils(Window window, int layoutId, int layout_width, int layout_height, int location, boolean backIsGroundDarken) {
        defaultInit(window, layoutId, layout_width, layout_height, location, backIsGroundDarken);
    }

    private void defaultInit(Window window, int layoutId, int layout_width, int layout_height, int location, boolean backIsGroundDarken) {
        this.location = location;
        this.window = window;
        this.backgroundIsDarken = backIsGroundDarken;
        view = LayoutInflater.from(window.getContext()).inflate(layoutId, null);
        popupWindow = new MyPopup(view, layout_width, layout_height);
        popupWindow.setBackgroundDrawable(new ColorDrawable()); //暂测 此行可不设置
        popupWindow.setFocusable(true); //必须加  否则触摸无响应  是否获取焦点
        popupWindow.setOutsideTouchable(false); //暂测 此行可不设置
        popupWindowInit(view, popupWindow);
        if (backgroundIsDarken) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.alpha = 0.5f;
            window.setAttributes(attributes);
        }
        popupWindowListener(view, popupWindow);
        popupWindow.setOnDismissListener(() -> {
            popupWindow.dismiss();
            if (onDismiss != null) onDismiss.onDismiss();
        });
    }

    public void show() {
        popupWindow.showAtLocation(view, location, 0, 0);
    }

    /**
     * popupWindow内容初始化
     */
    public abstract void popupWindowInit(View view, MyPopup popupWindow);

    /**
     * popupWindow内容点击合集
     */
    public void popupWindowListener(View view, MyPopup popupWindow) {

    }

    public class MyPopup extends PopupWindow {
        public MyPopup(View contentView, int width, int height) {
            super(contentView, width, height);
        }

        @Override
        public void dismiss() {
            if (backgroundIsDarken) {
                window.getAttributes().alpha = 1f;
                window.setAttributes(window.getAttributes());
            }
            super.dismiss();
        }
    }

    public void setOnDismiss(OnDismiss onDismiss) {
        this.onDismiss = onDismiss;
    }

    public interface OnDismiss {
        void onDismiss();
    }
}
