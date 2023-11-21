package com.example.networkpro.ui.fragment;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.lib_bean.bean.LoginBean;
import com.example.lib_common.consts.Const;
import com.example.lib_common.fragment.BaseMvvmFragment;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.view.guide.GuideHelper;
import com.example.lib_common.view.guide.GuideHelperType;
import com.example.lib_common.view.pulltozoom.PullToZoomScrollViewEx;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.ShareData;
import com.example.networkpro.R;
import com.example.networkpro.databinding.FragmentMasterLayoutBinding;
import com.example.networkpro.databinding.MasterViewContentLayoutBinding;
import com.example.networkpro.databinding.MasterViewHeaderLayoutBinding;
import com.example.networkpro.databinding.MasterViewZoomLayoutBinding;
import com.example.networkpro.viewmodel.MasterViewModel;

import java.util.Objects;

/**
 * Created by 王鑫哲 on 2021/11/12 下午 05:44
 * E-mail: User_wang_178@163.com
 * Ps: [我的] Fragment
 */
public class MasterFragment extends BaseMvvmFragment<MasterViewModel, FragmentMasterLayoutBinding> {

    private MasterViewHeaderLayoutBinding mHeaderView;
    private MasterViewZoomLayoutBinding mZoomView;
    private MasterViewContentLayoutBinding mContentView;

    @Override
    protected void initView() {
        initZoomView();
        initShowInfo();
    }

    private void initShowInfo() {
        LoginBean userInfo = UserManage.getLoginBean();
        mContentView.tvPhoneModel.setText(userInfo.phoneModel);
    }

    private void initZoomView() {
        mHeaderView = inflate(R.layout.master_view_header_layout);
        mZoomView = inflate(R.layout.master_view_zoom_layout);
        mContentView = inflate(R.layout.master_view_content_layout);
        mBinding.viewZoom.setZoomView(mZoomView.getRoot());
        mBinding.viewZoom.setHeaderView(mHeaderView.getRoot());
        mBinding.viewZoom.setScrollContentView(mContentView.getRoot());
        setPullToZoomViewLayoutParams(mBinding.viewZoom);
        mHeaderView.setViewModel(mViewModel);
        mZoomView.setViewModel(mViewModel);
        mZoomView.setUserAvatar(UserManage.getUserAvatar());

        // 初始化高斯模糊背景
        mViewModel.initZoomBinging(mZoomView);

        // 设置顶部高度，由于顶部设置了沉浸式，整体View会顶上去，所以自己写一个状态栏
        ViewGroup.LayoutParams layoutParams = mHeaderView.vStatusBar.getLayoutParams();
        layoutParams.height = DensityUtils.getStatusHeight();
        mHeaderView.vStatusBar.setLayoutParams(layoutParams);
    }

    // 设置头部的View的宽高。
    @SuppressLint("UseRequireInsteadOfGet")
    private void setPullToZoomViewLayoutParams(PullToZoomScrollViewEx scrollView) {
        DisplayMetrics localDisplayMetrics = new DisplayMetrics();
        Objects.requireNonNull(getActivity()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
        int mScreenWidth = localDisplayMetrics.widthPixels;
        // 这里的高度需要加上状态栏高度
        LinearLayout.LayoutParams localObject = new LinearLayout.LayoutParams(mScreenWidth, (int) (9.0F * (mScreenWidth / 15.0F) + DensityUtils.getStatusHeight()));
        scrollView.setHeaderLayoutParams(localObject);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_master_layout;
    }

    @Override
    public Class<MasterViewModel> onBindViewModel() {
        return MasterViewModel.class;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            boolean isShowGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.MASTER_PAGE_UPDATE_USER_AVATAR);
            if (!isShowGuide) {
                // 页面显示时 显示引导
                GuideHelper.getInstance().showGuide(GuideHelperType.UPDATE_USER_AVATAR, mZoomView.ivUserAvatar);
            }
        }
    }
}