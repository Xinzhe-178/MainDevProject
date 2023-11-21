package com.example.lib_common;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import androidx.annotation.NonNull;

import com.example.lib_common.Interfac.AbsActivityLifecycleCallbacks;
import com.example.lib_common.Interfac.FrontDeskStateListenerCall;
import com.example.lib_common.dialog.TipView;
import com.example.lib_common.manage.AllActivityManager;
import com.example.lib_common.receiver.WifiReceiver;
import com.example.lib_common.utils.imagepreview.ImagePreviewLoader;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.UtilApplication;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.core.FlipperClient;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.soloader.SoLoader;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.previewlibrary.ZoomMediaLoader;

import io.reactivex.plugins.RxJavaPlugins;

/**
 * Created by 王鑫哲 on 2022/4/2 下午 01:56
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseApplication extends UtilApplication {
    /**
     * activity管理类
     */
    private AllActivityManager mActivityManager;
    /**
     * 是否处于前台
     */
    private boolean isFrontDesk = false;
    /**
     * 控制状态时机 最初已连接wifi则不执行逻辑
     */
    private boolean wifiReceiverFlag = false;
    /**
     * 程序前后台状态
     */
    private FrontDeskStateListenerCall mFrontDeskStateListenerCall;

    @Override
    public void init() {
        mActivityManager = new AllActivityManager();
        // 初始化LiveEventBus
        initLiveBus();
        // 初始化缓存
        ShareData.init(this);
        // 抛出RxJava异常，防止崩溃
        rxJavaException();
        // 前后台状态监听
        initFrontDeskStateListener();
        // 初始化网络状态广播
        initWifiReceiver();
        // 初始化Flipper 调试工具
        initFlipper();
        // 初始化图片预览
        initImagePreview();
    }

    private void initImagePreview() {
        ZoomMediaLoader.getInstance().init(new ImagePreviewLoader());
    }

    private void rxJavaException() {
        //这里处理所有的Rxjava异常
        RxJavaPlugins.setErrorHandler(Throwable::printStackTrace);
    }

    public void initWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        WifiReceiver wifiReceiver = new WifiReceiver();
        registerReceiver(wifiReceiver, filter);

        wifiReceiver.setOnNetStateListener(new WifiReceiver.OnNetStateListener() {
            @Override
            public void haveNetwork(boolean isWifi) {
                if (isWifi) {
                    if (wifiReceiverFlag) {
                        TipView.getInstance().start("已连接到wifi", "网络数据可用");
                    }
                } else {
                    wifiReceiverFlag = true;
                    TipView.getInstance().start("正在使用流量", "网络数据可用");
                }
            }

            @Override
            public void noNetwork() {
                wifiReceiverFlag = true;
                TipView.getInstance().start("无网络", "网络数据不可用");
            }
        });
    }

    private void initLiveBus() {
        LiveEventBus.config().autoClear(true).lifecycleObserverAlwaysActive(true);
    }

    private void initFrontDeskStateListener() {
        registerActivityLifecycleCallbacks(new AbsActivityLifecycleCallbacks() {
            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                isFrontDesk = true;
                if (mFrontDeskStateListenerCall != null) {
                    mFrontDeskStateListenerCall.onActivityResumed();
                }
                LogUtils.PrintE("initFrontDeskStateListener--前台");
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                super.onActivityPaused(activity);
                isFrontDesk = false;
                if (mFrontDeskStateListenerCall != null) {
                    mFrontDeskStateListenerCall.onActivityPaused();
                }
                LogUtils.PrintE("initFrontDeskStateListener--后台");
            }
        });
    }

    public AllActivityManager getActivityManager() {
        return mActivityManager != null ? mActivityManager : new AllActivityManager();
    }

    public void setFrontDeskStateListenerCall(FrontDeskStateListenerCall frontDeskStateListenerCall) {
        mFrontDeskStateListenerCall = frontDeskStateListenerCall;
    }

    public boolean isFrontDesk() {
        return isFrontDesk;
    }

    private void initFlipper() {
        SoLoader.init(this, false);
        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            FlipperClient client = AndroidFlipperClient.getInstance(this);
            client.addPlugin(new InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()));
            client.start();
        }
    }
}
