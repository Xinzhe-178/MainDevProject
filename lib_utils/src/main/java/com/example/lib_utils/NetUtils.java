package com.example.lib_utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Gabriel on 2019/3/22.
 * Email 17600284843@163.com
 * Description: 网络检测工具类
 */
public class NetUtils {

    /**
     * 判断网络情况
     *
     * @return false 表示没有网络 true 表示有网络
     */
    public static boolean isNetworkAvailable() {
        return isNetworkAvailable(UtilApplication.getInstance().getApplicationContext());
    }

    /**
     * 判断网络情况
     *
     * @param context 上下文
     * @return false 表示没有网络 true 表示有网络
     */
    public static boolean isNetworkAvailable(Context context) {
        // 获得网络状态管理器
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        } else {
            // 建立网络数组
            @SuppressLint("MissingPermission") NetworkInfo[] net_info = connectivityManager.getAllNetworkInfo();
            if (net_info != null) {
                for (int i = 0; i < net_info.length; i++) {
                    // 判断获得的网络状态是否是处于连接状态
                    if (net_info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

