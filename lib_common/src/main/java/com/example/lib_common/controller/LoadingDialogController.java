package com.example.lib_common.controller;

import android.app.Activity;

import com.example.lib_common.BaseApplication;
import com.example.lib_common.Interfac.ILoadingDialog;
import com.example.lib_common.dialog.NetDiaLog;

/**
 * Created by 王鑫哲 on 2023/2/1 10:23 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class LoadingDialogController implements ILoadingDialog {

    private NetDiaLog mNetDiaLog;

    private static class Holder {
        static LoadingDialogController H = new LoadingDialogController();
    }

    public static LoadingDialogController getInstance() {
        return Holder.H;
    }

    @Override
    public void showDialog() {
        try {
            dismissDialog();
            if (mNetDiaLog == null) {
                mNetDiaLog = new NetDiaLog(getActivity());
                mNetDiaLog.setCanceledOnTouchOutside(false);
            }
            mNetDiaLog.setCanceledOnTouchOutside(false);
            mNetDiaLog.show();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismissDialog() {
        try {
            if (mNetDiaLog != null) {
                mNetDiaLog.dismiss();
                mNetDiaLog = null;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setNetDialogHintText(String s) {
        mNetDiaLog.setHintText(s);
    }

    private Activity getActivity() {
        BaseApplication instance = (BaseApplication) BaseApplication.getInstance();
        return instance.getActivityManager().getTopActivity();
    }
}
