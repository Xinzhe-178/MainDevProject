package com.example.networkpro.ui.activity;

import android.content.Context;
import android.content.Intent;

import com.example.lib_common.BaseApplication;
import com.example.lib_common.activity.BaseActivity;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.dialog.DialogManage;
import com.example.lib_common.dialog.DialogType;
import com.example.lib_common.manage.AppStyleManage;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.utils.JumpUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityPrivateLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/9/24 22:14
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class PrivateActivity extends BaseActivity<ActivityPrivateLayoutBinding> {
    @Override
    protected void initView() {
        mTopBar.setTitle("隐私政策");
        mBinding.setActivity(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_private_layout;
    }

    public OnBindingClickCall onCancelClick = () -> {
        DialogManage.init(PrivateActivity.this, DialogType.TYPE_A).
                setDataA("撤回授权将无法正常使用App，是否确认?", null, () -> {
                    UserManage.setAgreePrivacyDialog(false);
                    Context context = ContextManager.getContext();
                    Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(i);
                }, () -> {

                }, null);
    };
}
