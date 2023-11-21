package com.example.lib_common.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.lib_utils.LogUtils;

/**
 * Created by 王鑫哲 on 2022/7/29 4:32 下午
 * E-mail: User_wang_178@163.com
 * Ps: https://www.it610.com/article/1289157452167716864.htm
 * https://blog.csdn.net/fengyeNom1/article/details/72870586
 */
public class WifiReceiver extends BroadcastReceiver {

    /**
     * 这个系统广播会调用多次，使用打标记来解决
     */
    private boolean wifiFlag = true;

    private boolean mobileFlag = true;

    private OnNetStateListener mOnNetStateListener;

    public void setOnNetStateListener(OnNetStateListener onNetStateListener) {
        mOnNetStateListener = onNetStateListener;
    }

    @SuppressLint("WifiManagerPotentialLeak")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    if (wifiFlag) {
                        LogUtils.PrintE("wifiReceiver--> " + "wifi连接");
                        wifiFlag = false;

                        if (mOnNetStateListener != null) {
                            mOnNetStateListener.haveNetwork(true);
                        }
                    }
                    mobileFlag = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (mobileFlag) {
                        LogUtils.PrintE("wifiReceiver--> " + "流量连接");
                        mobileFlag = false;

                        if (mOnNetStateListener != null) {
                            mOnNetStateListener.haveNetwork(false);
                        }
                    }
                    wifiFlag = true;
                } else {
                    LogUtils.PrintE("wifiReceiver--> " + "无网络");
                    mobileFlag = true;
                    wifiFlag = true;

                    if (mOnNetStateListener != null) {
                        mOnNetStateListener.noNetwork();
                    }
                }
            } else {
                LogUtils.PrintE("wifiReceiver--> " + "无网络");
                mobileFlag = true;
                wifiFlag = true;

                if (mOnNetStateListener != null) {
                    mOnNetStateListener.noNetwork();
                }
            }
        }
    }

    public interface OnNetStateListener {
        /**
         * 有网
         *
         * @param isWifi 是否为wifi
         */
        void haveNetwork(boolean isWifi);

        /**
         * 没网
         */
        void noNetwork();
    }
}
