package com.example.networkpro.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityMainBinding;
import com.example.networkpro.viewmodel.MainViewModel;

public class MainActivity extends BaseMvvmActivity<ActivityMainBinding, MainViewModel> {
    /**
     * app退出时间存储
     */
    private long mExitTime;

    @Override
    protected void initView() {
        mViewModel.initFragmentAndTab(getSupportFragmentManager(), mBinding);
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public Class<MainViewModel> onBindViewModel() {
        return MainViewModel.class;
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) < 2000) {
            ContextManager.exitApp();
        } else {
            ToastUtils.show("再按一次退出应用");
        }
        mExitTime = System.currentTimeMillis();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        // 禁止在activity销毁后保留数据(解决Fragment重叠问题)
//        super.onSaveInstanceState(outState);
    }
}