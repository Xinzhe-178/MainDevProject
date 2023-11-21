package com.example.lib_common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.lib_common.R;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.TextUtils;

/**
 * Created by 王鑫哲 on 2021/11/5 上午 10:34
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class NetDiaLog extends Dialog {

    private final String def_hint = getContext().getResources().getString(R.string.common_load_hint);

    public NetDiaLog(@NonNull Context context) {
        super(context, R.style.style_common_dialog);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.net_dialog_layout);
        GlideUtils.setImageUrlNativeGif(findViewById(R.id.iv_pb), R.drawable.ic_net_dialog_bg);
        setHintText(def_hint);
    }

    public void setHintText(String hintText) {
        TextView tv_hint_content = findViewById(R.id.tv_hint_content);
        tv_hint_content.setText(TextUtils.isEmpty(hintText) ? def_hint : hintText);
    }
}
