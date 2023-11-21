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
        // 首页数据存储
        public static final String USER_HOME_ALL_DATA = "user_home_all_data";
        // 首页购物车存储
        public static final String USER_SHOP_CAR_DATA = "user_shop_car_data";
        // 首页左边adapter默认选中存储
        public static final String HOME_LEFT_ADAPTER_SELECT = "home_left_adapter_select";
        // 所有用户信息
        public static final String USER_INFO_GROUP = "user_info_group";
        // 当前登录的用户ID
        public static final String USER_ID = "user_id";
        // 搜索记录(历史)
        public static final String SEEK_HISTORY = "seek_history";
    }

    /**
     * 跳转传值常量存储
     */
    public static class JumpBundle {
        // 点击商品跳转详情页纯传值 详情页图片
        public static final String RIGHT_ITEM_JUMP_SHOP_PAR_LIST_KEY = "right_item_jump_shop_par_list_key";
    }

    /**
     * [我的]功能模块
     */
    public static final class MasterFunType {
        // 设置
        public static final int TYPE_SETTING = 1001;
        // 退出登录
        public static final int TYPE_QUIT_LOGIN = 1002;
        // 编辑商品
        public static final int TYPE_UPDATE_SHOP = 1003;
        // 订单明细
        public static final int TYPE_ORDER_DETAIL = 1004;
        // 清除所有商品
        public static final int TYPE_CLEAR_ALL_SHOP_DATA = 1005;
        // 联系我们
        public static final int TYPE_CONNECT_US = 1006;
        // 设置店铺
        public static final int TYPE_SHOPS_SETTING = 1007;
    }

    /**
     * 扫一扫
     */
    public static final class ScanType {
        // H5调用扫一扫需要用到此key，需要在WebViewActivity onActivityResult 接收回传值
        public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

        /**
         * H5调用
         */
        public static final String TYPE = "type"; //H5调用扫一扫 类型区分 勿动
        public static final String TYPE_ENTERPRISE_SERVICE = "enterpriseService"; //企服调用

        /**
         * 原生调用
         */
        public static final String TYPE_ANDROID = "type_android"; //H5调用扫一扫 类型区分 勿动
        public static final String TYPE_ANDROID_SCAN_ALL = "type_android_scan_all";

        /**
         * 进入搜索结果页面时 传输结果的key
         */
        public static final String SCAN_VALUE_CONST = "scan_value_const";
    }

    public static final class AppStyleConst {
        // 是否展示[广场]Fragment
        public static final String IS_SHOW_SQUARE = "is_show_square";
        // 网页是否可以跳转外部应用
        public static final String WEB_IS_JUMP_EXTERNAL = "web_is_jump_external";
    }

    /**
     * 搜索页面子页面类型
     */
    public static final class SeekInputShowType {
        // 搜索结果页
        public static final String SEEK_VALUE = "seek_value";
        // 搜索联想页面
        public static final String SEEK_LENOVO = "seek_lenovo";
        // 搜索历史页面
        public static final String SEEK_HISTORY = "seek_history";
    }

    /**
     * 首页CardDialog弹窗类型
     */
    public static final class HomeCardDialogShowConst {
        // 首页Card弹窗弹出时机
        public static final String HOME_CARD_DIALOG_SHOW_TIME = "home_card_dialog_show_time";
        // Type
        public static final String TYPE = "type";
        // 一天弹出一次
        public static final String TYPE_EVERY_DAY = "type_every_day";
        // 每打开app弹出
        public static final String TYPE_EVERY_OPEN = "type_every_open";
        // 不再弹出
        public static final String TYPE_NO_SHOW = "type_no_show";
    }

    /**
     * 跳转QQ 参数
     */
    public static final class QQConst {
        // 跳转QQ 进群Key 官网申请 https://qun.qq.com/join.html
        public static final String JUMP_QQ_GROUP_KEY = "gj60r3xEmWItIVUIW3ndy2r6kVoUjLDy";
        // 跳转QQ 群号
        public static final String JUMP_QQ_GROUP_NUMBER = "383819172";
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
        // 自定义view类型-收藏
        public static final String CUS_VIEW_FAVORITE = "cus_view_favorite";
        // 自定义View数据类型 如需向View传递数据 则使用bundle传递 此作为key
        public static final String CUS_VIEW_DATA_KEY = "cus_view_data_key";
        // 自定义View数据类型 广场item
        public static final String CUS_VIEW_DATA_HOME_LIST = "cus_view_data_home_list";
        // 自定义View数据类型 收藏item
        public static final String CUS_VIEW_DATA_FAVORITE_LIST = "cus_view_data_favorite_list";
    }

    /**
     * 浮层诱导弹出
     */
    public static final class GuideViewShowConst {
        // 首页底部弹出
        public static final String HOME_BOTTOM = "home_bottom";
        // 首页底部下单弹出
        public static final String HOM_BOTTOM_ORDER = "hom_bottom_order";
        // 广场列表自定义收藏弹出
        public static final String CUS_VIEW_FAVORITE = "cus_view_favorite";
        // 我的页面 更换头像
        public static final String MASTER_PAGE_UPDATE_USER_AVATAR = "master_page_update_user_avatar";
    }

    /**
     * 店铺设置相关
     */
    public static final class ShopsSettingConst {
        // 店铺名称
        public static final String SHOP_NAME = "shop_name";
        // 店铺地址
        public static final String SHOP_ADDRESS = "shop_address";
        // 页面返回是否跳转下单页面
        public static final String FINISH_IS_JUMP_ORDER_PAGE = "finish_is_jump_order_page";
    }
}
