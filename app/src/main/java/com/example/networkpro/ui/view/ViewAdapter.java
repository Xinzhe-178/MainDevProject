package com.example.networkpro.ui.view;

import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.lib_common.Interfac.OnPageChangeListener;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.view.SwitchButton;
import com.example.lib_utils.TextUtils;

import java.text.NumberFormat;

/**
 * Created by 王鑫哲 on 2022/5/8 下午 08:53
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ViewAdapter {
    /**
     * 设置TextView两位小数
     */
    @BindingAdapter(value = {"android:setTextViewTwoDecimal", "android:leftHintText"}, requireAll = false)
    public static void setTextViewTwoDecimal(TextView textView, float s, String leftHintText) {
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);
        textView.setText(TextUtils.isEmpty(leftHintText) ? nf.format(s) : leftHintText.concat(nf.format(s)));
    }

    /**
     * 仿 IOS 开关状态监听
     */
    @BindingAdapter(value = {"app:onCheckedChangeListener"}, requireAll = false)
    public static void OnCheckedChangeListener(SwitchButton view, OnBindingClickParamsCall<Boolean> call) {
        if (view != null && call != null) {
            view.setmOnCheckedChangeListener(call::clickCall);
        }
    }

    /**
     * ViewPage 滑动监听
     */
    @BindingAdapter(value = {"app:onPageSelected"}, requireAll = false)
    public static void OnPageChangeListener(ViewPager view, OnBindingClickParamsCall<Integer> call) {
        if (view != null && call != null) {
            view.addOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    call.clickCall(position);
                }
            });
        }
    }

    /**
     * NestedScrollView 滑动监听
     */
    @BindingAdapter(value = {"app:onScrollChange"}, requireAll = false)
    public static void OnScrollChangeListener(NestedScrollView view, OnBindingClickParamsCall<Integer> call) {
        if (view != null && call != null) {
            view.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> call.clickCall(scrollY));
        }
    }
}
