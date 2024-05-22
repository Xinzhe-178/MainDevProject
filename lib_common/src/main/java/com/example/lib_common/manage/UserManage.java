package com.example.lib_common.manage;

import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.UserConst;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.TextUtils;

/**
 * Created by 王鑫哲 on 2022/1/25 下午 06:33
 * E-mail: User_wang_178@163.com
 * 且也有 Gson相互转化的方法
 * Ps:
 */
public class UserManage {
    /**
     * 当前用户是否登录
     */
    public static boolean getUserIsLogin() {
        return !TextUtils.isEmpty(ShareData.getShareStringData(Const.Data.USER_INFO_GROUP));
    }

    /**
     * 是否同意[隐私政策]Dialog
     */
    public static void setAgreePrivacyDialog(boolean isAgree) {
        ShareData.setShareBooleanData(UserConst.IS_AGREE_PRIVACY_DIALOG, isAgree);
    }

    public static boolean getAgreePrivacyDialog() {
        return ShareData.getShareBooleanData(UserConst.IS_AGREE_PRIVACY_DIALOG);
    }

    /**
     * 保存app版本
     */
    public static void setAppVersion(String version) {
        ShareData.setShareStringData(UserConst.APP_VERSION, version);
    }

    public static String getAppVersion() {
        return ShareData.getShareStringData(UserConst.APP_VERSION);
    }
}
