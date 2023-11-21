package com.example.lib_utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by GabrielHeng on 2021/1/22
 * Emails:17310551233@163.com
 * Describe:SharedPreferences 储存的数据的类 存储位置：/data/data/<package name>/shared_prefs/
 */
public class ShareData {
    /**
     * sp的文件名
     */
    public static final String SP_NAME = "NEW_FDT_SP";

    /**
     * 数据持久化存储实例
     */
    private static SharedPreferences sp;

    /**
     * 初始化
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
    }

    /**
     * 获取存储Int值
     *
     * @param key key
     * @return int 内容
     */
    public static int getShareIntData(String key) {
        return sp.getInt(key, 0);
    }

    /**
     * 存储Int值
     *
     * @param key   key
     * @param value 存储Int
     */
    public static void setShareIntData(String key, int value) {
        sp.edit().putInt(key, value).commit();
    }

    /**
     * 获取存储String内容
     *
     * @param key key
     * @return string内容
     */
    public static String getShareStringData(String key) {
        return sp.getString(key, "");
    }

    /**
     * 存储String内容
     *
     * @param key   key
     * @param value string内容
     */
    public static void setShareStringData(String key, String value) {
        sp.edit().putString(key, value).commit();
    }

    /**
     * 获取存储boolean内容
     *
     * @param key key
     * @return Boolean内容
     */
    public static boolean getShareBooleanData(String key) {
        return sp.getBoolean(key, false);
    }

    /**
     * 存储Boolean内容
     *
     * @param key   key
     * @param value Boolean内容
     */
    public static void setShareBooleanData(String key, boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 移除指定存储内容
     *
     * @param key key
     */
    public static void remove(String key) {
        sp.edit().remove(key).commit();
    }

    /**
     * 清空存储
     */
    public static void clear() {
        sp.edit().clear().commit();
    }
}
