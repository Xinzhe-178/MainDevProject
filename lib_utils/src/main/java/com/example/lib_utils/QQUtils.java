package com.example.lib_utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by 王鑫哲 on 2023/5/18 3:56 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class QQUtils {

    public static void jumpQQChat(Context context, String qqNum) {
        if (context == null || TextUtils.isEmpty(qqNum)) {
            return;
        }
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=".concat(qqNum);//uin是发送过去的qq号码
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Throwable e) {
            e.printStackTrace();
            ToastUtils.show("您没有安装qq哦");
        }
    }

    /**
     * @param context
     * @param key     由官网生成的key https://qun.qq.com/join.html
     */
    public static void jumpQQGroup(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return;
        }
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
        } catch (Throwable e) {
            ToastUtils.show("您没有安装qq哦");
        }
    }
}
