package com.example.lib_common.consts;

import com.example.lib_common.manage.ContextManager;

/**
 * Created by 王鑫哲 on 2021/9/30 上午 09:41
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class Const {

    public static final String PACKAGE_NAME = ContextManager.getContext().getPackageName();

    /**
     * 适配器类型
     */
    public static class AdapterType {
        // 页眉Header
        public static final int TYPE_HEADER = 1001;
        // 正常展示
        public static final int TYPE_BODY = 1002;
        // 页脚Footer
        public static final int TYPE_FOOTER = 1003;
    }

    /**
     * 数据存储
     */
    public static class Data {
        // 所有用户信息
        public static final String USER_INFO_GROUP = "user_info_group";
    }

    /**
     * 公用WebView页面所需
     */
    public static final class CommonWebViewPageConst {
        // 获取传来的url
        public static final String URL_KEK = "url_key";
        // 是否显示原生topBar
        public static final String IS_SHOW_TOP_BAR_KEY = "is_show_top_bar_key";
        // titleKey
        public static final String TITLE_KEY = "title_key";
        // 自定义viewKey
        public static final String CUS_VIEW_KEY = "cus_view_key";
    }
}
