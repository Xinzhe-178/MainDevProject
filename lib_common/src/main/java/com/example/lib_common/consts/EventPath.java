package com.example.lib_common.consts;

/**
 * Created by 王鑫哲 on 2021/11/13 下午 10:51
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class EventPath {
    /**
     * 点击tab 通知列表回到顶部
     */
    public static final String HOME_FRAGMENT_RETURN_TOP = "home_fragment_return_top";
    /**
     * 编辑商品中 添加商品成功后通知首页刷新数据
     */
    public static final String UPDATE_ADD_REFRESH_HOME_DATA = "update_add_refresh_home_data";
    /**
     * 一键清除所有商品数据专用通知
     */
    public static final String CLEAR_ALL_SHOP_DATA = "clear_all_shop_data";
    /**
     * 搜索历史item点击
     */
    public static final String SEEK_HIS_ITEM_CLICK = "seek_his_item_click";
    /**
     * 搜索输入页面点击搜索框获取焦点
     */
    public static final String SEEK_HIS_SEEK_VIEW_CLICK = "seek_his_seek_view_click";
    /**
     * 首页底部浮层引导弹出
     */
    public static final String HOME_BOTTOM_GUIDE_SHOW = "home_bottom_guide_show";
    /**
     * 首页底部浮层引导弹出关闭后发起
     * 用于首页没有数据预设弹窗弹出时机
     */
    public static final String HOME_BOTTOM_GUIDE_DISMISS = "home_bottom_guide_dismiss";
    /**
     * 更新头像后发起通知
     */
    public static final String UPDATE_USER_AVATAR = "update_user_avatar";
}
