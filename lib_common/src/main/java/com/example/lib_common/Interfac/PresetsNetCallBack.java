package com.example.lib_common.Interfac;

import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_utils.ToastUtils;

/**
 * Created by 王鑫哲 on 2023/7/4 5:15 下午
 * E-mail: User_wang_178@163.com
 * Ps: 预设请求回调 可根据情况自行选择
 */
public class PresetsNetCallBack {

    public static class DefNetCallOne<T> implements NetCallBack<T> {
        @Override
        public void onStart() {
            LoadingDialogController.getInstance().showDialog();
        }

        @Override
        public void onError(String errorMsg) {
            ToastUtils.show(errorMsg);
            LoadingDialogController.getInstance().dismissDialog();
        }
    }

    public static class DefNetCallTwo<T> extends DefNetCallOne<T> {
        @Override
        public void onSuccess(T result) {
            LoadingDialogController.getInstance().dismissDialog();
        }
    }
}
