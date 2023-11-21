package com.example.networkpro.ui.activity;

import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityLoginBinding;
import com.example.networkpro.viewmodel.LoginChildViewModel;

public class LoginActivity extends BaseMvvmActivity<ActivityLoginBinding, LoginChildViewModel> {

    @Override
    public Class<LoginChildViewModel> onBindViewModel() {
        return LoginChildViewModel.class;
    }

    @Override
    protected void initView() {
        mViewModel.init(mBinding);
    }

    @Override
    protected boolean setWindowsIsImmerse() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }
}