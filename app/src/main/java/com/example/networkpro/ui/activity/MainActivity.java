package com.example.networkpro.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.consts.CardDialogConst;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.manage.AppStyleManage;
import com.example.lib_common.manage.ContextManager;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.RxUtils;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityMainBinding;
import com.example.networkpro.manage.CardDialogManage;
import com.example.networkpro.manage.fragment.FragmentSwitch;
import com.example.networkpro.ui.fragment.HomeFragment;
import com.example.networkpro.ui.fragment.HomeNewFragment;
import com.example.networkpro.ui.fragment.MasterFragment;
import com.example.networkpro.ui.fragment.RecyclerFragment;
import com.example.networkpro.viewmodel.MainViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;

public class MainActivity extends BaseMvvmActivity<ActivityMainBinding, MainViewModel> {
    /**
     * app退出时间存储
     */
    private long mExitTime;
    /**
     * 弹窗管理器
     */
    private CardDialogManage mDialogManage;
    /**
     * 首页Fragment
     */
    private HomeNewFragment mHomeNewFragment;

    @Override
    protected void initView() {
        initFragmentAndTab();
        initDialog();

        if (UserManage.getHomeCardDialogIsShow()) {
            RxUtils.timedTask(100, () -> {
                mDialogManage.getDialog().showDialog();
                ShareData.setShareStringData(Const.HomeCardDialogShowConst.HOME_CARD_DIALOG_SHOW_TIME, String.valueOf(System.currentTimeMillis()));
            });
        } else {
            boolean isShowBottomGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOME_BOTTOM);
            boolean isShowBottomOrderGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOM_BOTTOM_ORDER);
            if (!isShowBottomGuide || !isShowBottomOrderGuide) {
                LiveEventBus.get(EventPath.HOME_BOTTOM_GUIDE_SHOW).post(true);
            }
        }
    }

    private void initDialog() {
        mDialogManage = CardDialogManage.init(this, CardDialogConst.HOME_TYPE);
    }

    private void initFragmentAndTab() {
        FragmentSwitch fragmentSwitch = FragmentSwitch.getInstance();

        // 首页Fragment 这里首页Fragment不管在什么条件下都是会加载的，提出来是为了进行扫一扫，扫一扫在首页Fragment中
        // mHomeNewFragment在下面初始化过一次，会在从桌面Shortcut 进入的时候初始化，正常点击logo启动 就没有初始化，这里再初始化一次
        if (mHomeNewFragment == null) {
            mHomeNewFragment = new HomeNewFragment();
        }
        if (AppStyleManage.isShowSquare()) {
            fragmentSwitch.init(getSupportFragmentManager(), R.id.fl_main, 0, mHomeNewFragment, new RecyclerFragment(), new MasterFragment());
        } else {
            fragmentSwitch.init(getSupportFragmentManager(), R.id.fl_main, 0, mHomeNewFragment, new MasterFragment());
        }

        mViewModel.initBottomTab(mBinding, fragmentSwitch);
//        mViewModel.initIsNormalSse();
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
        // 由于商品的购物车View 改成了显示和隐藏，这里触发返回时，如果在显示 则隐藏
        ArrayList<Fragment> fragments = mHomeNewFragment.getFragments();
        if (fragments != null && fragments.size() > 0) {
            Fragment fragment = fragments.get(0);
            if (fragment instanceof HomeFragment) {
                HomeFragment homeFragment = (HomeFragment) fragment;
                if (homeFragment.mBinding.vShopCar.getVisibility() == View.VISIBLE) {
                    homeFragment.getViewModel().setShopCarViewIsShow(false);
                    return;
                }
            }
        }

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

    @Override
    protected void getIntentData(String intentData, String action) {
        LogUtils.PrintE("getIntentData-MainActivity-> intentData = " + intentData + " action = " + action);
        if (!TextUtils.isEmpty(intentData) && !TextUtils.isEmpty(action) && TextUtils.equals(action, Intent.ACTION_VIEW)) {
            switch (intentData) {
                case "com.example.lib_common.activity.CaptureActivity":
                    mHomeNewFragment = new HomeNewFragment();
                    mHomeNewFragment.setOnInitListener(() -> mHomeNewFragment.onScanClick.clickCall());
                    break;
                case "com.example.networkpro.ui.activity.UpdateFoodActivity":
                    JumpUtils.jump(UpdateFoodActivity.class);
                    break;
                case "com.example.networkpro.ui.activity.SeekInputActivity":
                    JumpUtils.jump(SeekInputActivity.class);
                    break;
                case "com.example.networkpro.ui.activity.MainActivity.HomeNewFragment.BeautyFragment":
                    mHomeNewFragment = new HomeNewFragment();
                    mHomeNewFragment.setOnInitListener(() -> mHomeNewFragment.mBinding.tab.setAllCurrentItem(1));
                    break;
                case "com.example.networkpro.ui.activity.FavoriteActivity":
                    JumpUtils.jump(FavoriteActivity.class);
                    break;
            }
            clearIntendData();
        }
    }

    @Override
    protected boolean setWindowsIsImmerse() {
        return true;
    }
}