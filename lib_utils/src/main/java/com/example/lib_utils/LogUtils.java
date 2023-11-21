package com.example.lib_utils;

import android.util.Log;

/**
 * Created by 王鑫哲 on 2021/10/11 下午 05:32
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class LogUtils {
    /**
     * 是否开启log打印
     */
    private static final boolean isOpenLog = BuildConfig.BUILD_TYPE.equals("debug");

    public static void PrintD(String tag, String content) {
        if (isOpenLog) {
            Log.d(tag, getContent(content));
        }
    }

    public static void PrintD(String content) {
        if (isOpenLog) {
            Log.d("者D->", getContent(content));
        }
    }

    public static void PrintE(String tag, String content) {
        if (isOpenLog) {
            Log.e(tag, getContent(content));
        }
    }

    public static void PrintE(String content) {
        if (isOpenLog) {
            Log.e("者E->", getContent(content));
        }
    }

    private static String getNameFromTrace(StackTraceElement[] traceElements) {
        StringBuilder taskName = new StringBuilder();
        if (traceElements != null && traceElements.length > 4) {
            StackTraceElement traceElement = traceElements[4];
            taskName.append(traceElement.getMethodName());
            taskName.append("(").append(traceElement.getFileName()).append(":").append(traceElement.getLineNumber()).append(")");
        }
        return taskName.toString();
    }

    private static String getContent(String msg) {
        try {
            String sourceLinks = getNameFromTrace(Thread.currentThread().getStackTrace());
            return sourceLinks + msg;
        } catch (Throwable throwable) {
            return msg;
        }
    }
}
