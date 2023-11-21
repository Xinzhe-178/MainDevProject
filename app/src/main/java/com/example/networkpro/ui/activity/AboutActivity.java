package com.example.networkpro.ui.activity;

import android.annotation.SuppressLint;

import com.example.lib_common.activity.BaseActivity;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_utils.ClipboardUtils;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.QQUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.BuildConfig;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityAboutLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/8/18 21:17
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class AboutActivity extends BaseActivity<ActivityAboutLayoutBinding> {

    @SuppressLint("SetTextI18n")
    @Override
    protected void initView() {
        mTopBar.setTitle("关于我们");
        mBinding.setVersion(BuildConfig.VERSION_NAME);
        mBinding.setAppName(getString(R.string.app_name));
        mBinding.setActivity(this);
        mBinding.tvQqGroup.setText("哲不折 -- QQ交流群：".concat(Const.QQConst.JUMP_QQ_GROUP_NUMBER));

        GlideUtils.setImageDrawable(mBinding.ivLog, R.drawable.ic_splash_gif_bg_200x200);

        // 长按将QQ号复制到剪切板
        mBinding.tvQqGroup.setOnLongClickListener(v -> {
            ClipboardUtils.copy(Const.QQConst.JUMP_QQ_GROUP_NUMBER);
            ToastUtils.show("复制成功");
            return true;
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_layout;
    }

    public OnBindingClickCall onQQNumClick = () -> QQUtils.jumpQQGroup(this, Const.QQConst.JUMP_QQ_GROUP_KEY);
}
