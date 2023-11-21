package com.example.networkpro.ui.view.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.dialog.EasyDialog;
import com.example.lib_common.manage.UserManage;
import com.example.networkpro.databinding.DialogPrivacyLayoutBinding;
import com.example.networkpro.ui.activity.BaseSplashActivity;
import com.example.networkpro.ui.activity.ShortcutCommonActivity;

/**
 * Created by 王鑫哲 on 2022/8/4 21:07
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class PrivacyPolicyDialog extends EasyDialog<DialogPrivacyLayoutBinding> {

    private FragmentActivity mActivity;

    public PrivacyPolicyDialog(@NonNull FragmentActivity activity, int layoutId) {
        super(activity, layoutId);
        mActivity = activity;
        setClickWindowIsDismiss(false);
        setWidth(GridLayout.LayoutParams.MATCH_PARENT);
        setHeight(GridLayout.LayoutParams.MATCH_PARENT);
        setGravity(Gravity.CENTER);
        setCancelable(false);
        build();
    }

    @Override
    protected void initView(DialogPrivacyLayoutBinding mBinding, Dialog dialog) {
        mBinding.setDialog(this);
    }

    public OnBindingClickCall onExitClick = () -> {
        if (mActivity != null) {
            mActivity.finish();
        }
    };

    public OnBindingClickCall onAgreeClick = () -> {
        if (mActivity != null && mActivity instanceof BaseSplashActivity) {
            BaseSplashActivity activity = (BaseSplashActivity) mActivity;
            activity.jump();
            UserManage.setAgreePrivacyDialog(true);
        } else if (mActivity != null && mActivity instanceof ShortcutCommonActivity) {
            ShortcutCommonActivity activity = (ShortcutCommonActivity) mActivity;
            activity.jump();
            UserManage.setAgreePrivacyDialog(true);
        }
    };

    public OnBindingClickParamsCall<Integer> onScrollChangeListener = scrollY -> {

    };
}