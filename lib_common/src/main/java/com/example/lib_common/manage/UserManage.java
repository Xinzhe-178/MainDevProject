package com.example.lib_common.manage;

import com.alibaba.fastjson.JSON;
import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_bean.bean.LoginBean;
import com.example.lib_bean.bean.SeekHistoryBean;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.UserConst;
import com.example.lib_utils.DateUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.TextUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/1/25 下午 06:33
 * E-mail: User_wang_178@163.com
 * 且也有 Gson相互转化的方法
 * Ps:
 */
public class UserManage {
    /**
     * 是否加载过[引导页]
     */
    public static void setIsLoadGuidePage(boolean isLogin) {
        ShareData.setShareBooleanData(UserConst.IS_LOAD_GUIDE_PAGE, isLogin);
    }

    public static boolean getIsLoadGuidePage() {
        return ShareData.getShareBooleanData(UserConst.IS_LOAD_GUIDE_PAGE);
    }

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

    /**
     * 保存头像
     */
    public static void setUserAvatar(String userAvatar) {
        ShareData.setShareStringData(UserConst.USER_AVATAR, userAvatar);
    }

    public static String getUserAvatar() {
        String userAvatar = ShareData.getShareStringData(UserConst.USER_AVATAR);
        String defUserAvatar = "https://c-ssl.duitang.com/uploads/blog/202106/15/20210615084425_774a9.thumb.1000_0.png";
        return TextUtils.isEmpty(userAvatar) ? defUserAvatar : userAvatar;
    }

    /**
     * get购物车数据
     *
     * @return
     */
    public static List<HomeRecyclerGroupBean.RightGroup> getShopCarData() {
        String s = ShareData.getShareStringData(Const.Data.USER_SHOP_CAR_DATA);

        List<HomeRecyclerGroupBean.RightGroup> list = new Gson().fromJson(s, new TypeToken<List<HomeRecyclerGroupBean.RightGroup>>() {
        }.getType());
        return list != null ? list : new ArrayList<>();
    }

    public static void setShopCarData(List<HomeRecyclerGroupBean.RightGroup> list) {
        ShareData.setShareStringData(Const.Data.USER_SHOP_CAR_DATA, list == null ? "" : new Gson().toJson(list));
    }

    /**
     * get首页数据
     *
     * @return
     */
    public static List<HomeRecyclerGroupBean> getHomeAllData() {
        String s = ShareData.getShareStringData(Const.Data.USER_HOME_ALL_DATA);

        Gson gson = new Gson();
        List<HomeRecyclerGroupBean> list = gson.fromJson(s, new TypeToken<List<HomeRecyclerGroupBean>>() {
        }.getType());

        if (list != null) {
            LogUtils.PrintE("USER_HOME_ALL_DATA-->", JSON.toJSONString(list));
        }

        return list != null ? list : new ArrayList<>();
    }

    public static void setHomeAllData(List<HomeRecyclerGroupBean> list) {
        ShareData.setShareStringData(Const.Data.USER_HOME_ALL_DATA, list == null ? "" : new Gson().toJson(list));
    }

    /**
     * 获取目前登录用户
     */
    public static LoginBean getLoginBean() {
        String data = ShareData.getShareStringData(Const.Data.USER_INFO_GROUP);
        try {
            LoginBean loginBean = new Gson().fromJson(data, LoginBean.class);
            return loginBean == null ? new LoginBean() : loginBean;
        } catch (Throwable throwable) {
            return new LoginBean();
        }
    }

    public static void setLoginBean(LoginBean info) {
        ShareData.setShareStringData(Const.Data.USER_INFO_GROUP, info == null ? "" : new Gson().toJson(info));
    }

    /**
     * 获取用户ID
     */
    public static String getUserId() {
        return String.valueOf(getLoginBean().id);
    }

    public static void setSeekHistory(List<SeekHistoryBean> list) {
        ShareData.setShareStringData(Const.Data.SEEK_HISTORY, new Gson().toJson(list));
    }

    /**
     * 获取搜索历史记录
     *
     * @return
     */
    public static List<SeekHistoryBean> getSeekHistory() {
        String s = ShareData.getShareStringData(Const.Data.SEEK_HISTORY);
        Gson gson = new Gson();
        List<SeekHistoryBean> list = gson.fromJson(s, new TypeToken<List<SeekHistoryBean>>() {
        }.getType());
        return list != null ? list : new ArrayList<>();
    }

    public static void setHomeCardDialogShowType(String type) {
        ShareData.setShareStringData(Const.HomeCardDialogShowConst.TYPE, type);
    }

    /**
     * 首页Card弹窗展示时机
     *
     * @return
     */
    public static boolean getHomeCardDialogIsShow() {
        String s = ShareData.getShareStringData(Const.HomeCardDialogShowConst.TYPE);
        if (s.equals(Const.HomeCardDialogShowConst.TYPE_NO_SHOW)) {
            return false;
        } else if (s.equals(Const.HomeCardDialogShowConst.TYPE_EVERY_OPEN)) {
            return true;
        } else if (s.equals(Const.HomeCardDialogShowConst.TYPE_EVERY_DAY)) {
            String lastTime = ShareData.getShareStringData(Const.HomeCardDialogShowConst.HOME_CARD_DIALOG_SHOW_TIME);
            long timestampSecond = System.currentTimeMillis();
            long intervalDay = (timestampSecond - Long.parseLong(lastTime)) / DateUtils.ONE_DAY;
            return intervalDay >= 1;
        } else if (s.equals("")) {
            ShareData.setShareStringData(Const.HomeCardDialogShowConst.TYPE, Const.HomeCardDialogShowConst.TYPE_EVERY_OPEN);
        }
        return true;
    }

    /**
     * 清除所有商品数据
     */
    public static void clearAllShopData() {
        setShopCarData(null);
        setHomeAllData(null);
    }
}
