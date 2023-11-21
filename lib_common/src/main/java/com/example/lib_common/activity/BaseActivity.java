package com.example.lib_common.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.lib_common.BaseApplication;
import com.example.lib_common.R;
import com.example.lib_common.databinding.ActivityBaseBinding;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.topbar.TopBarOnClickListener;
import com.example.lib_common.topbar.TopBarView;
import com.example.lib_common.view.stateplaceholderview.IStatePlaceholderView;
import com.example.lib_common.view.stateplaceholderview.StatePlaceType;
import com.example.lib_common.view.stateplaceholderview.StatePlaceView;
import com.example.lib_utils.WatermarkUtils;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

/**
 * Created by 王鑫哲 on 2021/8/24 下午 01:41
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseActivity<VDB extends ViewDataBinding> extends AppCompatActivity implements TopBarOnClickListener, IStatePlaceholderView {

    public Activity mActivity;

    public VDB mBinding;

    public TopBarView mTopBar;

    private ActivityBaseBinding mBindingBase;

    private StatePlaceView mStatePlaceView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 提取全局实例
        mActivity = this;
        // 初始化基类布局
        mBindingBase = inflate(R.layout.activity_base);
        // 设置加载布局
        setContentView(mBindingBase.getRoot());
        // 获取子类展示布局
        if (getLayoutId() != 0) mBinding = inflate(getLayoutId());
        //立即更新UI
        if (mBinding != null) mBinding.executePendingBindings();
        // 初始化占位View
        mStatePlaceView = StatePlaceView.register(mBindingBase.viewPlaceActivity, this);
        // 每启动一个Activity 将其添加置于Activity集合中 模拟栈操作
        BaseApplication instance = (BaseApplication) BaseApplication.getInstance();
        instance.getActivityManager().addActivity(this);
        initBundleData();
        // 设置沉浸式系统状态栏
        if (setWindowsIsImmerse()) {
            setStatusBarColor(R.color.transparent);
        }
        initMvvm();
        initTopBarView();
        initView();
        initData();
        initListener();

        mBindingBase.viewTopBar.setTopBarOnClickListener(this);
        // 页面水印
        WatermarkUtils.show(mActivity);
    }

    /**
     * 获取上个页面跳转到当前页面的传值 如果要获取 则重写getBundle() 只有不为空的时候会调用
     */
    protected void initBundleData() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                getBundle(extras);
            }

            Uri uri = intent.getData();
            if (uri != null) {
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments != null && pathSegments.size() > 0) {
                    getIntentData(pathSegments.get(0), intent.getAction());
                }
            }
        }
    }

    /**
     * 设置状态栏是否沉浸
     *
     * @return
     */
    protected boolean setWindowsIsImmerse() {
        return false;
    }

    protected void initMvvm() {

    }

    /**
     * 设置系统导航颜色
     *
     * @param color
     */
    protected void setStatusBarColor(@ColorRes int color) {
        ImmersionBar.with(this)
                // 状态栏颜色
                .statusBarColor(color)
                // 导航栏颜色
                .navigationBarColor(R.color.transparent)
                // 解决布局与状态栏重叠问题
                .fitsSystemWindows(false)
                // 是否启用 自动根据StatusBar和NavigationBar颜色调整深色模式与亮色模式 isEnable – true启用 默认false
                .autoDarkModeEnable(true)
                // 状态栏字体深色或亮色 true深色 false 浅色
                .statusBarDarkFont(false).init();
    }

    protected void initTopBarView() {
        mTopBar = mBindingBase.viewTopBar;
        mBindingBase.viewTopBar.setVisibility(getDefTopBar().equals(TopBarIsShow.HAVE_TOP_BAR) ? View.VISIBLE : View.GONE);
        if (mBinding != null) mBindingBase.viewBaseActivity.addView(mBinding.getRoot());
    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化监听
     */
    protected void initListener() {

    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 获取Layout
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 设置默认TopBar显示&不显示
     *
     * @return
     */
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.HAVE_TOP_BAR;
    }

    @Override
    public void onFinishCLickCall() {
        finish();
    }

    @Override
    public void onCloseClickCall() {
        finish();
    }

    @Override
    public void onTitleClickCall() {

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        overrideFontScale(newBase);
    }

    /**
     * 重置配置 fontScale：保持字体比例不变，始终为 1.
     */
    private void overrideFontScale(Context context) {
        if (context == null) return;
        Configuration configuration = context.getResources().getConfiguration();
        configuration.fontScale = 1f;
        applyOverrideConfiguration(configuration);
    }
//
//    /**
//     * 设置APP字体大小不随系统设置而改变的
//     *
//     * @return
//     */
//    @Override
//    public Resources getResources() {
//        Resources res = super.getResources();
//        Configuration config = new Configuration();
//        config.setToDefaults();
//        res.updateConfiguration(config, res.getDisplayMetrics());
//        return res;
//    }

    /**
     * 快捷加载Fragment方法
     *
     * @param fragmentId    fragment控件 传递ID
     * @param fragmentClass 显示的fragment
     * @param <F>
     */
    public <F extends Fragment> void showFragment(int fragmentId, F fragmentClass) {
        getSupportFragmentManager().beginTransaction().replace(fragmentId, fragmentClass).commit();
    }

    /**
     * 快捷转换方法
     * layout => ViewDataBinding
     * 子类可直使用
     *
     * @param layoutId
     * @param <T>
     * @return
     */
    protected <T extends ViewDataBinding> T inflate(int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(mActivity), layoutId, null, false);
    }

    /**
     * 获取传值bundle
     * 如需接收传值 则重写该方法取bundle
     * 只有在bundle不为null才会调用
     */
    protected void getBundle(Bundle bundle) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication instance = (BaseApplication) BaseApplication.getInstance();
        instance.getActivityManager().removeActivity(this);
    }

    public ActivityBaseBinding getBindingBase() {
        return mBindingBase;
    }

    protected void getIntentData(String intentData, String action) {

    }

    /**
     * 清除 IntentData 一般配合getIntentData使用 即 不需要当前传递参数向后面跳转的activity传递
     */
    protected void clearIntendData() {
        getIntent().setData(null);
    }

    @Override
    public void setPlaceholderState(StatePlaceType type) {
        if (mStatePlaceView != null) {
            mStatePlaceView.setPlaceholderState(type);
        }
    }

    @Override
    public View getErrorView() {
        return inflate(R.layout.activity_place_view_error_layout).getRoot();
    }

    @Override
    public View getEmptyView() {
        return inflate(R.layout.activity_place_view_empty_layout).getRoot();
    }

    @Override
    public View getNullView() {
        return inflate(R.layout.activity_place_view_null_layout).getRoot();
    }

    @Override
    public View getNoNetWorkView() {
        return inflate(R.layout.activity_place_view_nonetwork_layout).getRoot();
    }

    @Override
    public void onRetryClickListener() {

    }

    /**
     * 预加载布局，默认为null 如果需要展示预加载，则重写该方法
     * base类中的该方法必须为null 否则所有页面都会展示预加载页面
     *
     * @return
     */
    @Override
    public View getPreloadView() {
        return null;
    }
}
