package com.example.networkpro.ui.activity;

import android.os.Bundle;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.topbar.TopBarView;
import com.example.lib_utils.Res;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityPlaceOrderLayoutBinding;
import com.example.networkpro.viewmodel.PlaceOrderViewModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2023/7/17 3:46 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class PlaceOrderActivity extends BaseMvvmActivity<ActivityPlaceOrderLayoutBinding, PlaceOrderViewModel> {

    private List<HomeRecyclerGroupBean.RightGroup> mShopCarData;

    @Override
    protected void initView() {
        mBinding.setActivity(this);
        initTopBar();
        mViewModel.initTitleAlpha(mBinding);

        mBinding.tvList.setPageBinding(mBinding);
        mBinding.tvList.updateData(mShopCarData);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_place_order_layout;
    }

    @Override
    public Class<PlaceOrderViewModel> onBindViewModel() {
        return PlaceOrderViewModel.class;
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    private void initTopBar() {
        // 设置导航栏颜色
        setStatusBarColor(R.color.pink);
        TopBarView topBar = mBinding.vTopBar;
        // 赋值监听事件
        topBar.setTopBarOnClickListener(this);
        topBar.setTitleColor(Res.color(R.color.black));
        topBar.setTitle(mViewModel.getTitleText());
        topBar.setBackgroundColor(Res.color(R.color.pink));
        topBar.CusTopBarSet(topBar);
        topBar.getTitleView().setIsFocused(false);
    }

    public OnBindingClickCall onCommitClick = () -> {
        ToastUtils.show("提交订单");
    };

    public OnBindingClickCall onOtherPaymentClick = () -> {
        ToastUtils.show("找人付");
    };

    @Override
    public void onTitleClickCall() {
        if (!TextUtils.equals(TextUtils.getText(mBinding.vTopBar.getTitleView()), mViewModel.getTitleText())) {
            ToastUtils.show("标题点击");
        }
    }

    @Override
    protected void getBundle(Bundle bundle) {
        String shopCarDataJson = bundle.getString("key");
        mShopCarData = getShopCarData(shopCarDataJson);
    }

    public static List<HomeRecyclerGroupBean.RightGroup> getShopCarData(String s) {
        List<HomeRecyclerGroupBean.RightGroup> list = new Gson().fromJson(s, new TypeToken<List<HomeRecyclerGroupBean.RightGroup>>() {
        }.getType());
        return list != null ? list : new ArrayList<>();
    }
}
