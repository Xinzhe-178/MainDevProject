package com.example.networkpro.ui.fragment;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_common.dialog.DialogManage;
import com.example.lib_common.dialog.DialogType;
import com.example.lib_common.fragment.BaseMvvmFragment;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_common.view.guide.GuideHelper;
import com.example.lib_common.view.guide.GuideHelperType;
import com.example.lib_utils.JsonUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.FragmentHomeLayoutBinding;
import com.example.networkpro.ui.activity.UpdateFoodActivity;
import com.example.networkpro.viewmodel.HomeViewModel;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.List;

/**
 * Created by 王鑫哲 on 2021/11/5 上午 11:00
 * E-mail: User_wang_178@163.com
 * Ps: [首页] fragment
 */
public class HomeFragment extends BaseMvvmFragment<HomeViewModel, FragmentHomeLayoutBinding> {

    /**
     * 判断美食预设dialog 点击的是取消还是确定标志
     */
    private boolean flag = false;

    @Override
    protected void initView() {
        boolean isOne = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOME_BOTTOM);
        boolean isTwo = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOM_BOTTOM_ORDER);
        if (!isOne || !isTwo) {
            // 如果会弹出浮层引导 那就等浮层引导关闭后再初始化
            LiveEventBus.get(EventPath.HOME_BOTTOM_GUIDE_DISMISS).observe(mContext, o -> {
                cusInitView();
            });
        } else {
            // 不会弹出浮层引导 直接初始化
            cusInitView();
        }

        // 监听浮层诱导是否弹出
        LiveEventBus.get(EventPath.HOME_BOTTOM_GUIDE_SHOW).observe(mContext, o -> {

            // 如果需要弹出诱导的话 在关闭掉cardDialog后弹出 不然效果不好
            boolean isShowBottomGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOME_BOTTOM);
            boolean isShowBottomOrderGuide = ShareData.getShareBooleanData(Const.GuideViewShowConst.HOM_BOTTOM_ORDER);
            if (!isShowBottomGuide && !isShowBottomOrderGuide) {
                GuideHelper.getInstance().showGuide(GuideHelperType.HOME_BOTTOM, mBinding.bottomView).setOnEndViewClick(() -> {
                    GuideHelper.getInstance().showGuide(GuideHelperType.HOM_BOTTOM_ORDER, mBinding.bottomView.getBinding().tvGoSettlement);
                });
            } else if (!isShowBottomGuide) {
                GuideHelper.getInstance().showGuide(GuideHelperType.HOME_BOTTOM, mBinding.bottomView).setOnEndViewClick(null);
            } else if (!isShowBottomOrderGuide) {
                GuideHelper.getInstance().showGuide(GuideHelperType.HOM_BOTTOM_ORDER, mBinding.bottomView.getBinding().tvGoSettlement);
            }
        });
    }

    private void cusInitView() {
        List<HomeRecyclerGroupBean> homeAllData = UserManage.getHomeAllData();

        // 如果当前数据为空 弹出引导弹窗
        if (homeAllData.size() == 0) {
            DialogManage.init(getActivity(), DialogType.TYPE_A).setDataA("这边为您提供了一些美食预设，是否导入？", null, () -> {
                // 点击确定
                flag = true;
            }, () -> {
                // 点击取消
            }, () -> {
                // 弹窗关闭
                // 弹窗关闭时，判断是否点击了确定，点击了确定，则缓存数据后再初始化
                if (flag) {
                    // 解析本地json 并将其缓存到本地 传入null 的话，初始化时再重新获取达到添加预设目的
                    putLocalityData(() -> init(null));
                } else {
                    init(homeAllData);
                }
            });
        } else {
            // 存在数据 直接初始化
            init(homeAllData);
        }
    }

    private void init(List<HomeRecyclerGroupBean> homeAllData) {
        mViewModel.initRecycler(mBinding, getActivity(), homeAllData);
        mBinding.setFragment(HomeFragment.this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    public HomeViewModel getViewModel() {
        return mViewModel;
    }

    @Override
    public Class<HomeViewModel> onBindViewModel() {
        return HomeViewModel.class;
    }

    /**
     * [去添加]点击
     */
    public OnBindingClickCall onAddClick = () -> JumpUtils.jump(UpdateFoodActivity.class);

    /**
     * 底部购物车点击
     */
    public OnBindingClickCall onBottomShopClick = () -> mViewModel.showShopCarPopupWindow();

    public void putLocalityData(OnBindingClickCall call) {
        LoadingDialogController.getInstance().showDialog();
        try {
            String json = JsonUtils.localityAnalyseJsonString("food/food_presets_all.json");
            ShareData.setShareStringData(Const.Data.USER_HOME_ALL_DATA, json);
            LoadingDialogController.getInstance().dismissDialog();
            if (call != null) {
                call.clickCall();
            }
        } catch (Exception e) {
            ToastUtils.show("导入预设失败！");
            LogUtils.PrintD(getClass() + "-putLocalityData-catch-> " + e.getMessage());
            e.printStackTrace();
        }
    }
}
