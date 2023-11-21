package com.example.lib_common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.lib_common.BaseApplication;
import com.example.lib_common.activity.CaptureActivity;
import com.example.lib_common.activity.CommonContainerActivity;
import com.example.lib_common.consts.Const;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_common.web.DefWebViewActivity;
import com.example.lib_utils.UtilApplication;

import java.io.Serializable;

/**
 * Created by 王鑫哲 on 2022/5/10 下午 10:39
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class JumpUtils {

    public static <C extends Activity> void jump(Class<C> activity) {
        jump(activity, null);
    }

    public static void jump(Fragment fragment, String title) {
        Bundle bundle = new Bundle();
        bundle.putString(CommonContainerActivity.title, title);
        bundle.putSerializable("mFragment", (Serializable) fragment);
        jump(CommonContainerActivity.class, bundle);
    }

    public static <C extends Activity> void jump(Class<C> activity, Bundle bundle) {
        if (activity == null) {
            return;
        }

        Intent intent = new Intent(BaseApplication.getInstance(), activity);
        Intent topActivityIntent = ContextManager.getTopActivity().getIntent();
        if (topActivityIntent != null && topActivityIntent.getData() != null) {
            intent.setData(topActivityIntent.getData());
            intent.setAction(topActivityIntent.getAction());
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (bundle != null) intent.putExtras(bundle);
        try {
            BaseApplication.getInstance().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 跳转WebViewActivity
     * PS: 如果设置为显示TopBar 那么title如果不设置的话/为空 则显示的title为默认为当前网页的title
     */
    public static void jumpWeb(boolean isShowTopBar, String link, String title) {
        jumpWeb(isShowTopBar, link, title, "");
    }

    public static void jumpWeb(boolean isShowTopBar, String link, String title, String cusViewType, Bundle bundle) {
        bundle.putString(Const.CommonWebViewPageConst.URL_KEK, link);
        bundle.putBoolean(Const.CommonWebViewPageConst.IS_SHOW_TOP_BAR_KEY, isShowTopBar);
        bundle.putString(Const.CommonWebViewPageConst.TITLE_KEY, title);
        bundle.putBoolean(Const.CommonWebViewPageConst.TITLE_KEY, false);
        bundle.putString(Const.CommonWebViewPageConst.CUS_VIEW_KEY, cusViewType);
        jump(DefWebViewActivity.class, bundle);
    }

    public static void jumpWeb(boolean isShowTopBar, String link, String title, String cusViewType) {
        jumpWeb(isShowTopBar, link, title, cusViewType, new Bundle());
    }

    /**
     * 直接拨打电话
     *
     * @param phone 手机号码
     */
    public static void jumpCallPhone(String phone) {
        UtilApplication instance = BaseApplication.getInstance();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:".concat(phone)));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        instance.startActivity(intent);
    }

    /**
     * 跳转原生扫码页
     *
     * @param type 扫码类型 传入android类型
     */
    public static void jumpAndroidScan(String type) {
        Bundle bundle = new Bundle();
        bundle.putString(Const.ScanType.TYPE, Const.ScanType.TYPE_ANDROID);
        bundle.putString(Const.ScanType.TYPE_ANDROID, type);
        JumpUtils.jump(CaptureActivity.class, bundle);
    }

    /**
     * intent 需要提前设置好所有 此方法只负责跳转
     *
     * @param context
     * @param intent
     */
    public static void jump(Context context, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
