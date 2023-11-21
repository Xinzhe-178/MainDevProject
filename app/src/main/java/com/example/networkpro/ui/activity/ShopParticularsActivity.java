package com.example.networkpro.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AlertDialog;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.consts.Const;
import com.example.lib_common.manage.AppStyleManage;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.topbar.TopBarView;
import com.example.lib_common.utils.imagepreview.ImagePreviewUtils;
import com.example.lib_common.utils.imagepreview.ImageViewInfo;
import com.example.lib_common.web.common.CommonJavascriptInterface;
import com.example.lib_common.web.common.X5WebChromeClient;
import com.example.lib_common.web.common.X5WebViewClient;
import com.example.lib_common.web.utils.FullscreenHolder;
import com.example.lib_common.web.webinterface.IWebViewActivity;
import com.example.lib_network.callback.Urls;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.Res;
import com.example.lib_utils.SaveUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityShopParticularsBinding;
import com.example.networkpro.databinding.ShopPariticularsVpLayoutBinding;
import com.example.networkpro.viewmodel.ShopParticularsViewModel;
import com.tencent.smtt.sdk.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/3/9 下午 03:19
 * E-mail: User_wang_178@163.com
 * Ps: 商品详情页
 */
public class ShopParticularsActivity extends BaseMvvmActivity<ActivityShopParticularsBinding, ShopParticularsViewModel> implements IWebViewActivity {

    private HomeRecyclerGroupBean.RightGroup mRightData;

    // 全屏时视频加载view
    private FrameLayout videoFullView;

    @Override
    protected void initView() {
        mViewModel.initBinding(mBinding);
        mBinding.setViewModel(mViewModel);

        initViewPageData();
        initTopBar();

        initWebView();
    }

    private void initWebView() {
        videoFullView = mBinding.videoFullView;
        // 加载视频相关
        X5WebChromeClient webChromeClient = new X5WebChromeClient(this);
        mBinding.webView.setWebChromeClient(webChromeClient);
        // 与js交互
        mBinding.webView.addJavascriptInterface(new CommonJavascriptInterface(this), "injectedObject");
        X5WebViewClient x5WebViewClient = new X5WebViewClient(this);
        x5WebViewClient.setOpenApp(AppStyleManage.webIsJumpExternal());
        mBinding.webView.setWebViewClient(x5WebViewClient);

        String loadUrl = Urls.BAI_DU_BAI_KE_URl.concat(mRightData.rightTitle);
        mBinding.webView.loadUrl(loadUrl);
    }

    @Override
    protected boolean setWindowsIsImmerse() {
        return true;
    }

    private void initTopBar() {
        mBinding.viewTopBar.setTopBarOnClickListener(this);

        TopBarView topBar = mBinding.viewTopBar;
        topBar.setTitleColor(Res.color(R.color.transparent));
        topBar.setTitle(mRightData.rightTitle);

        topBar.setBackgroundColor(Res.color(R.color.transparent));
        topBar.getLeftBackView().getDrawable().setTint(Res.color(R.color.white));

        View placeholderView = topBar.getPlaceholderView();
        placeholderView.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = placeholderView.getLayoutParams();
        layoutParams.height = DensityUtils.getStatusHeight();
        placeholderView.setLayoutParams(layoutParams);
    }

    private void initViewPageData() {
        List<View> views = new ArrayList<>();
        // 为点击预览集合
        ArrayList<ImageViewInfo> imageViewInfo = new ArrayList<>();
        for (int i = 0; i < mRightData.childImageData.mImageLists.size(); i++) {
            ShopPariticularsVpLayoutBinding binding = inflate(R.layout.shop_pariticulars_vp_layout);
            views.add(binding.getRoot());

            String imgPath = mRightData.childImageData.mImageLists.get(i).imgPath;
            binding.setImageUrl(imgPath);

            imageViewInfo.add(new ImageViewInfo(imgPath));
            binding.ivImg.setOnClickListener(v -> {
                ImagePreviewUtils.start(mActivity, imageViewInfo, mBinding.vpViewPage.getCurrentItem());
            });
        }

        mViewModel.initViewPage(mBinding.vpViewPage, views, integer -> mBinding.setCurrentIndex(integer + 1));
        mBinding.setMaxNum(views.size());
        mBinding.setCurrentIndex(1);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shop_particulars;
    }

    @Override
    public Class<ShopParticularsViewModel> onBindViewModel() {
        return ShopParticularsViewModel.class;
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        mRightData = (HomeRecyclerGroupBean.RightGroup) bundle.getSerializable(Const.JumpBundle.RIGHT_ITEM_JUMP_SHOP_PAR_LIST_KEY);
//        mBinding.tvTestShow.setText(JSON.toJSONString(mRightData));
        if (TextUtils.isEmpty(mRightData.desc)) {
            mBinding.tvTestShow.setVisibility(View.GONE);
        } else {
            mBinding.tvTestShow.setText(mRightData.desc);
        }
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
        if (hitTestResult.getType() == com.tencent.smtt.sdk.WebView.HitTestResult.IMAGE_TYPE || hitTestResult.getType() == com.tencent.smtt.sdk.WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
            // 弹出保存图片的对话框
            new AlertDialog.Builder(mActivity).setItems(new String[]{"查看大图", "保存图片到相册"}, (dialog, which) -> {
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
