package com.example.lib_network.callback;

import com.example.lib_utils.ShareData;
import com.example.lib_utils.TextUtils;

/**
 * Created by 王鑫哲 on 2022/3/13 下午 04:28
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HeaderManage {
    public static final String HEADER_COMMON_KEY = "header_common_key";

    /**
     * 存储公共请求头
     *
     * @param header
     */
    public static void setCommonHeader(String header) {
        ShareData.setShareStringData(HEADER_COMMON_KEY, header);
    }

    /**
     * 过去公共请求头
     *
     * @return
     */
    public static String getCommonHeader() {
        String header = ShareData.getShareStringData(HEADER_COMMON_KEY);
        return TextUtils.isEmpty(header) ? "" : header;
    }
}
