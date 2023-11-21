package com.example.lib_common.viewmodel;


import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.lib_common.activity.ScanValueActivity;
import com.example.lib_common.consts.Const;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.LogUtils;

/**
 * Created by 王鑫哲 on 2022/2/23 上午 11:33
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CaptureViewModel extends BaseViewModel {

    public CaptureViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 执行扫码成功后逻辑
     * 只有原生扫码会走, H5走DefaultWebViewActivity onActivityResult
     *
     * @param scanResult 二维码扫描后的地址URL
     * @param typeValue  当前扫码类型
     */
    public void executeScanResult(String scanResult, String typeValue) {
        LogUtils.PrintE("扫码-->android扫码ViewModel-->" + "mTypeAndroid= " + typeValue + "扫描地址 = " + scanResult);
        switch (typeValue) {
            /**
             * 类型区分暂搁置 逻辑已完善
             * 如果扫码结果为http开头 则跳转网页 否则则为支付
             */
            case Const.ScanType.TYPE_ANDROID_SCAN_ALL:
                if (scanResult.startsWith("http")) {
                    JumpUtils.jumpWeb(true, scanResult, "");
                    LogUtils.PrintE("scanResult--> " + scanResult);
                } else {
                    // 扫描结果非网址
                    Bundle bundle = new Bundle();
                    bundle.putString(Const.ScanType.SCAN_VALUE_CONST,scanResult);
                    JumpUtils.jump(ScanValueActivity.class,bundle);
                }
                break;
        }
        finish.setValue(true);
    }
}
