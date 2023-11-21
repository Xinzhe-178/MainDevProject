package com.example.lib_common.dialog.call;

import androidx.databinding.ViewDataBinding;

/**
 * Created by 王鑫哲 on 2022/4/14 下午 02:28
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface OnDialogInitBindingCall<VDB extends ViewDataBinding> {
    void onInit(VDB mBinding);
}
