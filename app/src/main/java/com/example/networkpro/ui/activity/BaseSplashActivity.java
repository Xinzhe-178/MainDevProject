package com.example.networkpro.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.lib_common.activity.BaseActivity;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_statistics.Statistics;
import com.example.lib_statistics.StatisticsConst;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.RxUtils;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.BuildConfig;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivitySplashBinding;
import com.example.networkpro.ui.view.dialog.PrivacyPolicyDialog;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;

/**
 * Created by 王鑫哲 on 2022/4/26 下午 05:07
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseSplashActivity extends BaseActivity<ActivitySplashBinding> {
    /**
     * 跳转时间（单位 毫秒）
     */
    public static final int SKIP_TIME = 1500;

    protected Disposable mDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        // app启动埋点
        Statistics.getInstance().addRecord(StatisticsConst.APP_START);

        String appVersion = UserManage.getAppVersion();
        if (TextUtils.isEmpty(appVersion) || !TextUtils.equals(appVersion, BuildConfig.VERSION_NAME)) {
            UserManage.setAgreePrivacyDialog(false);
            UserManage.setAppVersion(BuildConfig.VERSION_NAME);
        }
        LogUtils.PrintE("appVersion--> " + appVersion + " VERSION_NAME--> " + BuildConfig.VERSION_NAME);
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ResourceType")
    @Override
    protected void initView() {
        GlideUtils.setImageNPEDrawable(mBinding.ivSplash, R.drawable.ic_splash_gif_bg_200x200);
        setStatusBarColor(R.color.splash_bg);

        if (UserManage.getAgreePrivacyDialog()) {
            initJump();
        } else {
            // 弹出[隐私政策]Dialog
            new PrivacyPolicyDialog(this, R.layout.dialog_privacy_layout).show();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    /**
     * SKIP_TIME毫秒后 执行跳转逻辑
     */
    public void initJump() {
        mDisposable = RxUtils.timedTask(SKIP_TIME, TimeUnit.MILLISECONDS, this::jump);
    }

    public void jump() {
        if (UserManage.getIsLoadGuidePage()) {
            if (UserManage.getUserIsLogin()) {
                JumpUtils.jump(MainActivity.class);
            } else {
                JumpUtils.jump(LoginActivity.class);
            }
        } else {
            JumpUtils.jump(GuidePageActivity.class);
        }
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }
}
