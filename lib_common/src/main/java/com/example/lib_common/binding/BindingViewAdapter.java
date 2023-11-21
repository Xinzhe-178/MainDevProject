package com.example.lib_common.binding;

import android.annotation.SuppressLint;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickMoreParamsCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_utils.LogUtils;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

/**
 * Created by 王鑫哲 on 2022/4/30 下午 10:16
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BindingViewAdapter {

    /**
     * 无需传递参数点击
     *
     * @param clickView          被点击的View
     * @param bindingClickCall   点击CallBack
     * @param isClosureAntiShake 是否关闭防抖 防抖默认开启(false) true: 关闭防抖 反之
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"app:onClickCall", "app:onIsClosureAntiShake"}, requireAll = false)
    public static void onClick(View clickView, OnBindingClickCall bindingClickCall, boolean isClosureAntiShake) {
        if (bindingClickCall != null) {
            clickExtract(clickView, isClosureAntiShake, bindingClickCall::clickCall);
        }
    }

    /**
     * 只有一个参数点击
     *
     * @param clickView              被点击的View
     * @param bindingClickParamsCall 点击CallBack
     * @param params                 参数
     * @param isClosureAntiShake     是否关闭防抖 防抖默认开启(false) true: 关闭防抖 反之
     * @param isCloseGuaranteeNoNull 是否关闭确保参数不为空的情况下响应点击 默认开启为false true 关闭确保
     * @param <T>                    参数类型
     */
    @BindingAdapter(value = {"app:onClickCall", "app:onParams1", "app:onIsClosureAntiShake", "app:onIsCloseGuaranteeNoNull"}, requireAll = false)
    public static <T> void onClick(View clickView, OnBindingClickParamsCall<T> bindingClickParamsCall, T params, boolean isClosureAntiShake, boolean isCloseGuaranteeNoNull) {
        if (bindingClickParamsCall != null) {
            clickExtract(clickView, isClosureAntiShake, () -> {
                if (!isCloseGuaranteeNoNull) {
                    if (params != null) {
                        bindingClickParamsCall.clickCall(params);
                    }
                } else {
                    bindingClickParamsCall.clickCall(params);
                }
            });
        }
    }

    /**
     * 两个参数点击
     *
     * @param clickView                  被点击的View
     * @param bindingClickMoreParamsCall 点击CallBack
     * @param params1                    参数1
     * @param params2                    参数2
     * @param isClosureAntiShake         是否关闭防抖 防抖默认开启(false) true: 关闭防抖 反之
     * @param isCloseGuaranteeNoNull     是否关闭确保参数不为空的情况下响应点击 默认开启为false true 关闭确保
     * @param <P1>                       参数1类型
     * @param <P2>                       参数2类型
     */
    @BindingAdapter(value = {"app:onClickCall", "app:onParams1", "app:onParams2", "app:onIsClosureAntiShake", "app:onIsCloseGuaranteeNoNull"}, requireAll = false)
    public static <P1, P2> void onClick(View clickView, OnBindingClickMoreParamsCall<P1, P2> bindingClickMoreParamsCall, P1 params1, P2 params2, boolean isClosureAntiShake, boolean isCloseGuaranteeNoNull) {
        if (bindingClickMoreParamsCall != null) {
            clickExtract(clickView, isClosureAntiShake, () -> {
                if (!isCloseGuaranteeNoNull) {
                    if (params1 != null && params2 != null) {
                        bindingClickMoreParamsCall.clickCall(params1, params2);
                    }
                } else {
                    bindingClickMoreParamsCall.clickCall(params1, params2);
                }
            });
        }
    }

    /**
     * Rxjava处理防抖点击抽取
     *
     * @param clickView          点击View
     * @param isClosureAntiShake 是否关闭防抖动
     * @param click              点击回调接口Call
     */
    @SuppressLint("CheckResult")
    private static void clickExtract(View clickView, boolean isClosureAntiShake, OnBindingClickCall click) {
        if (clickView != null && click != null) {
            RxView.clicks(clickView)
                    .throttleFirst(!isClosureAntiShake ? 1 : 0, TimeUnit.SECONDS)
                    .subscribe(o -> click.clickCall(), throwable -> LogUtils.PrintE("封装点击抽取抛出异常"));
        }
    }
}
