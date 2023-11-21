package com.example.lib_utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by 王鑫哲 on 2022/8/21 10:14
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ClipboardUtils {
    /**
     * 复制
     *
     * @param data
     */
    public static void copy(String data) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) UtilApplication.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）,其他的还有
        // newHtmlText、
        // newIntent、
        // newUri、
        // newRawUri
        ClipData clipData = ClipData.newPlainText(null, data);
        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
    }
}
