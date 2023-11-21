package com.example.lib_utils;

import android.os.Build;

/**
 * =====================================
 * 作    者: 陈嘉桐
 * 版    本：1.1.4
 * 创建日期：2017/6/9
 * 描    述：
 * =====================================
 */
public class DeviceUtil {

    public static String getDeviceInfo() {
        String handSetInfo =
                "手机型号：" + Build.DEVICE +
                        "\n系统版本：" + Build.VERSION.RELEASE +
                        "\nSDK版本：" + Build.VERSION.SDK_INT;
        return handSetInfo;
    }

    public static String getDeviceModel() {
        return Build.DEVICE;
    }

    /**
     * 获取设备厂商
     * <p>如 Xiaomi</p>
     *
     * @return 设备厂商
     */

    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }
}
