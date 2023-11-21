package com.example.networkpro.ui.activity;

import com.example.lib_common.activity.BaseActivity;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickMoreParamsCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityTestBindingClickLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/5/7 下午 10:04
 * E-mail: User_wang_178@163.com
 * Ps:
 * 这个类是View点击事件参考Demo
 * 封装后续还会完善 但大体思路 用法雷同
 * 需注意:
 * 所有点击事件默认是带防抖 所以 设置点击时 只需要设置点击回调即可
 * 带参数的点击事件 默认是带的参数判空 即: 点击时拿到的参数为空 就不会走点击回调 这样免去了点击事件中手动判空的臃肿逻辑
 */
public class TestBingingClickActivity extends BaseActivity<ActivityTestBindingClickLayoutBinding> {
    @Override
    protected void initView() {
        mBinding.setActivity(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_test_binding_click_layout;
    }

    /**
     * 无参数点击事件
     */
    public OnBindingClickCall mOnBindingClickCall = () -> ToastUtils.show("点击事件");

    /**
     * 一个参数点击事件
     */
    public OnBindingClickParamsCall<Object> mOnBindingClickParamsCall = o -> {
        if (o == null) {
            ToastUtils.show("我的参数是null 但是关闭非空校验 所有我还是可以收到点击事件");
        } else {
            ToastUtils.show(o);
        }
    };

    /**
     * 两个参数点击事件
     */
    public OnBindingClickMoreParamsCall<Object, Object> mOnBindingClickMoreParamsCall = (onParams1, onParams2) -> {
        if (onParams1 == null || onParams2 == null) {
            ToastUtils.show("onParams = null 但是关闭非空校验 所有我还是可以收到点击事件");
        } else {
            ToastUtils.show("onParams1 = " + onParams1 + "  onParams2 = " + onParams2);
        }
    };

}
