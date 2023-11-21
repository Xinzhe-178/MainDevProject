package com.example.lib_common.manage;

import com.example.lib_common.consts.Const;
import com.example.lib_utils.ShareData;

/**
 * Created by 王鑫哲 on 2022/7/2 10:28 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CacheDataManage {
    /**
     * 只清除所有商品相关数据
     */
    public static void clearAllShop() {
        ShareData.remove(Const.Data.USER_HOME_ALL_DATA);
        ShareData.remove(Const.Data.USER_SHOP_CAR_DATA);
        ShareData.remove(Const.Data.HOME_LEFT_ADAPTER_SELECT);
    }
}
