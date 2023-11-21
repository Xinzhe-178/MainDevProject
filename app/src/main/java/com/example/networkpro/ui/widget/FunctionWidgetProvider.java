package com.example.networkpro.ui.widget;

import android.content.Context;
import android.content.Intent;

import com.example.lib_common.utils.JumpUtils;
import com.example.lib_common.widget.BaseWidgetProvider;
import com.example.lib_common.widget.IWidgetManager;
import com.example.networkpro.ui.activity.ShortcutCommonActivity;

/**
 * Created by 王鑫哲 on 2023/5/18 9:19 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class FunctionWidgetProvider extends BaseWidgetProvider {

    @Override
    protected void onReceive(Context context, Intent intent, String action, String intentData) {
        jump(intent);
    }

    @Override
    protected IWidgetManager getWidgetManager() {
        return FunctionWidgetManager.getInstance();
    }

    public void jump(Intent i) {
        Intent intent = new Intent();
        intent.setClass(mContext, ShortcutCommonActivity.class);
        intent.setData(i.getData());
        intent.setAction(i.getAction());
        JumpUtils.jump(mContext, intent);
    }
}
