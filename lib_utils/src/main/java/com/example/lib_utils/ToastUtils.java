package com.example.lib_utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.lib_utils.databinding.ToastDefaultLayoutBinding;

/**
 * Created by 王鑫哲 on 2021/9/30 上午 10:25
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ToastUtils {

    private static Toast toast = null;

    public static void show(Object hintText) {
        if (hintText == null || TextUtils.isEmpty(hintText.toString())) {
            return;
        }
        show(hintText, R.layout.toast_default_layout, (OnToastInit<ToastDefaultLayoutBinding>) (mBinding, msg) -> {
            mBinding.tvToast.setText(msg.toString());
        });
    }

    /**
     * 此方法特意透出, 如有需要自定义Toast布局 则使用该方法 onToastInit回调中处理展示逻辑即可 可参考默认toast方法使用
     *
     * @param msg         展示内容
     * @param layoutId    布局xml
     * @param onToastInit 初始化回调
     */
    public static <VDB extends ViewDataBinding> void show(@NonNull Object msg, @LayoutRes int layoutId, OnToastInit<VDB> onToastInit) {
        if (toast != null) toast.cancel();
        toast = new Toast(UtilApplication.getInstance());
        VDB binding = DataBindingUtil.inflate(LayoutInflater.from(UtilApplication.getInstance()), layoutId, null, false);
        if (onToastInit != null) onToastInit.init(binding, msg);
        toast.setView(binding.getRoot());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public interface OnToastInit<VDB extends ViewDataBinding> {
        void init(VDB mBinding, Object msg);
    }
}
