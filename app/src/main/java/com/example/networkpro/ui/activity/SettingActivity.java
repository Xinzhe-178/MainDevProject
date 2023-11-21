package com.example.networkpro.ui.activity;

import android.view.ViewGroup;

import androidx.core.widget.NestedScrollView;

import com.example.lib_bean.bean.LoginBean;
import com.example.lib_common.activity.BaseActivity;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.UserConst;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.topbar.TopBarView;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivitySettingBinding;

/**
 * Created by 王鑫哲 on 2022/7/1 6:33 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SettingActivity extends BaseActivity<ActivitySettingBinding> {

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    @Override
    protected void initView() {
        initTopBar();
        mBinding.setActivity(this);

        LoginBean loginBean = UserManage.getLoginBean();
        mBinding.tvUserName.setText(loginBean.phoneModel);
        GlideUtils.setCircleImageUrl(mBinding.ivUserAvatar,UserManage.getUserAvatar());
    }

    private void initTopBar() {
        TopBarView topBar = mBinding.appBar;
        topBar.setTopBarOnClickListener(this);
        topBar.setTitle("设置");

        ViewGroup.LayoutParams topBarLayoutParams = topBar.getLayoutParams();
        topBarLayoutParams.height = DensityUtils.dp(50);
        topBar.setLayoutParams(topBarLayoutParams);

        NestedScrollView.LayoutParams llGroupLayoutParams = (NestedScrollView.LayoutParams) mBinding.llGroup.getLayoutParams();
        llGroupLayoutParams.topMargin = topBar.getLayoutParams().height;
        mBinding.llGroup.setLayoutParams(llGroupLayoutParams);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    public OnBindingClickCall onAboutClick = () -> JumpUtils.jump(AboutActivity.class);

    public OnBindingClickCall onHomeSetClick = () -> JumpUtils.jump(HomeControlActivity.class);

    public OnBindingClickCall onPrivateClick = () -> JumpUtils.jump(PrivateActivity.class);

    public OnBindingClickCall onClick = () -> ToastUtils.show("敬请期待~");

}
