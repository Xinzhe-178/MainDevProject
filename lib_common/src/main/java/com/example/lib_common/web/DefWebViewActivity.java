package com.example.lib_common.web;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;

import com.example.lib_common.R;
import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.consts.Const;
import com.example.lib_common.databinding.ActivityDefWebviewLayoutBinding;
import com.example.lib_common.manage.AppStyleManage;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.utils.imagepreview.ImagePreviewUtils;
import com.example.lib_common.web.common.CommonJavascriptInterface;
import com.example.lib_common.web.common.X5WebChromeClient;
import com.example.lib_common.web.common.X5WebViewClient;
import com.example.lib_common.web.utils.FullscreenHolder;
import com.example.lib_common.web.webinterface.IWebViewActivity;
import com.example.lib_utils.ClipboardUtils;
import com.example.lib_utils.SaveUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by 王鑫哲 on 2022/4/3 上午 11:30
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class DefWebViewActivity extends BaseMvvmActivity<ActivityDefWebviewLayoutBinding, DefWebViewViewModel> implements IWebViewActivity {

    private String loadUrl;
    private boolean isShowTopBar;
    private String title;
    private String cusViewType;
    private View mCusView;

    private Object mCusData;

    // 加载视频相关
    private X5WebChromeClient mWebChromeClient;
    // 全屏时视频加载view
    private FrameLayout videoFullView;

    @Override
    protected void initView() {
        initWebView();
        initTopBar();
        initCusView();
    }

    private void initCusView() {
        if (TextUtils.isEmpty(cusViewType)) {
            return;
        }

        mCusView = mViewModel.getCusView(cusViewType, mCusData);
        if (mCusView != null) {
            mBinding.flCusView.setVisibility(View.VISIBLE);
            mBinding.flCusView.addView(mCusView);
        }
    }

    private void initTopBar() {
        if (TextUtils.isEmpty(title)) {
            mWebChromeClient.GetWebViewTitle(title -> mTopBar.setTitle(title));
        } else {
            mTopBar.setTitle(title);
        }
        mTopBar.setCloseShow(true);
    }

    private void initWebView() {
        videoFullView = mBinding.videoFullView;
        mWebChromeClient = new X5WebChromeClient(this);
        mBinding.webView.setWebChromeClient(mWebChromeClient);
        // 与js交互
        mBinding.webView.addJavascriptInterface(new CommonJavascriptInterface(this), "injectedObject");
        X5WebViewClient x5WebViewClient = new X5WebViewClient(this);
        x5WebViewClient.setOpenApp(AppStyleManage.webIsJumpExternal());
        mBinding.webView.setWebViewClient(x5WebViewClient);
        mBinding.webView.loadUrl(loadUrl);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_def_webview_layout;
    }

    @Override
    public Class<DefWebViewViewModel> onBindViewModel() {
        return DefWebViewViewModel.class;
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return isShowTopBar ? TopBarIsShow.HAVE_TOP_BAR : TopBarIsShow.NO_TOP_BAR;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        loadUrl = bundle.getString(Const.CommonWebViewPageConst.URL_KEK);
        isShowTopBar = bundle.getBoolean(Const.CommonWebViewPageConst.IS_SHOW_TOP_BAR_KEY);
        title = bundle.getString(Const.CommonWebViewPageConst.TITLE_KEY);
        cusViewType = bundle.getString(Const.CommonWebViewPageConst.CUS_VIEW_KEY);
        mCusData = bundle.getSerializable(Const.CommonWebViewPageConst.CUS_VIEW_DATA_KEY);
    }

    @Override
    protected void initListener() {
        // 长按操作
        mBinding.webView.setOnLongClickListener(v -> handleLongImage());
    }

    @Override
    public void hideProgressBar() {
        mBinding.loading.setVisibility(View.GONE);
    }

    @Override
    public void showWebView() {
        mBinding.webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideWebView() {
        mBinding.webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void startProgress(int newProgress) {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public FrameLayout getVideoFullView() {
        return videoFullView;
    }

    @Override
    public void fullViewAddView(View view) {
        FrameLayout decor = (FrameLayout) getWindow().getDecorView();
        videoFullView = new FullscreenHolder(this);
        videoFullView.addView(view);
        decor.addView(videoFullView);
    }

    @Override
    public void showVideoFullView() {
        videoFullView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideVideoFullView() {
        videoFullView.setVisibility(View.GONE);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onResume() {
        super.onResume();
        mBinding.webView.onResume();
        // 支付宝网页版在打开文章详情之后,无法点击按钮下一步
        mBinding.webView.resumeTimers();
        // 设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.webView.onPause();
    }

    @Override
    protected void onDestroy() {
        try {
            videoFullView.removeAllViews();
            mBinding.webView.stopLoading();
            mBinding.webView.setWebChromeClient(null);
            mBinding.webView.setWebViewClient(null);
            mBinding.webView.destroy();
        } catch (Exception ignored) {

        }
        super.onDestroy();
    }

    @Override
    public void onFinishCLickCall() {
        goBackFinish();
    }

    @Override
    public void onCloseClickCall() {
        finish();
    }

    @Override
    public void onTitleClickCall() {
        ClipboardUtils.copy(mBinding.webView.getUrl());
        ToastUtils.show("已将该链接复制到剪切板");
    }

    @Override
    public void onBackPressed() {
        goBackFinish();
    }

    private void goBackFinish() {
        if (mBinding.webView.canGoBack()) {
            mBinding.webView.goBack();//返回上个页面
        } else {
            finish();
        }
    }

    /**
     * 长按图片事件处理
     */
    private boolean handleLongImage() {
        WebView.HitTestResult hitTestResult = mBinding.webView.getHitTestResult();
        // 如果是图片类型或者是带有图片链接的类型
        if (hitTestResult.getType() == com.tencent.smtt.sdk.WebView.HitTestResult.IMAGE_TYPE ||
                hitTestResult.getType() == com.tencent.smtt.sdk.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            // 弹出保存图片的对话框
            new AlertDialog.Builder(mActivity)
                    .setItems(new String[]{"查看大图", "保存图片到相册"}, (dialog, which) -> {
                        String picUrl = hitTestResult.getExtra();
                        //获取图片
                        Log.e("picUrl", picUrl);
                        switch (which) {
                            case 0:
                                ImagePreviewUtils.start(mActivity, picUrl, 0);
                                break;
                            case 1:
                                SaveUtils.saveImage(picUrl, () -> {
                                    runOnUiThread(() -> {
                                        ToastUtils.show("保存成功");
                                    });
                                });
                                break;
                            default:
                                break;
                        }
                    }).show();
            return true;
        }
        return false;
    }
}
