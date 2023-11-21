package com.example.lib_common.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import com.example.lib_common.R;
import com.example.lib_common.consts.Const;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_utils.ClipboardUtils;
import com.example.lib_utils.Res;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;

/**
 * Created by 王鑫哲 on 2022/9/29 3:39 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ScanValueActivity extends BaseActivity {

    private String scanValue = "";

    @Override
    protected void initView() {
        TextView textView = new TextView(this);
        textView.setText(scanValue);
        textView.setTextColor(Res.color(R.color.black));
        textView.setTextSize(16);

        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(layoutParams);
        getBindingBase().viewBaseActivity.addView(textView);

        textView.setOnClickListener(v -> {
            ClipboardUtils.copy(TextUtils.getText(textView));
            ToastUtils.show("复制成功");
        });
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        scanValue = bundle.getString(Const.ScanType.SCAN_VALUE_CONST);
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }
}
