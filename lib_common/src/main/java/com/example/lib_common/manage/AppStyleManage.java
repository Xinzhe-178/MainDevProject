package com.example.lib_common.manage;

import com.example.lib_common.consts.Const;
import com.example.lib_utils.ShareData;

/**
 * Created by 王鑫哲 on 2022/8/18 21:50
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class AppStyleManage {
    /**
     * 是否展示[广场]Fragment
     */
    public static boolean isShowSquare() {
        return ShareData.getShareBooleanData(Const.AppStyleConst.IS_SHOW_SQUARE);
    }

    public static void setIsShowSquare(boolean isShowSquare) {
        ShareData.setShareBooleanData(Const.AppStyleConst.IS_SHOW_SQUARE, isShowSquare);
    }

    /**
     * 网页是否可以跳转外部应用
     */
    public static boolean webIsJumpExternal() {
        return ShareData.getShareBooleanData(Const.AppStyleConst.WEB_IS_JUMP_EXTERNAL);
    }

    public static void setWebIsJumpExternal(boolean isJumpExternal) {
        ShareData.setShareBooleanData(Const.AppStyleConst.WEB_IS_JUMP_EXTERNAL, isJumpExternal);
    }
}
