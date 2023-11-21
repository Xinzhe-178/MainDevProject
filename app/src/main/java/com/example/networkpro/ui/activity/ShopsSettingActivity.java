package com.example.networkpro.ui.activity;

import android.os.Bundle;

import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.consts.Const;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.ShareData;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityShopsSettingBinding;
import com.example.networkpro.viewmodel.ShopsSettingViewModel;

/**
 * Created by 王鑫哲 on 2023/8/22 10:28 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ShopsSettingActivity extends BaseMvvmActivity<ActivityShopsSettingBinding, ShopsSettingViewModel> {
    /**
     * 关闭当前页面后 是否跳转到下单页面
     * 因为为进入这个页面有两个入口，其中一个入口是点击下单，判断没有店铺名或者没有店铺地址会进入当前页面。从这个入口进入，设置好了，触发返回则跳转下单页面，依旧没有设置好，则正常返回即可
     */
    private boolean finishIsJumpOrderPage = false;
    /**
     * 点击下单跳转的页面，购物车数据 如果返回需要跳转下单页，则该参数需要传给下单页面
     */
    private String s;

    @Override
    protected void initView() {
        mTopBar.setTitle("店铺设置");
        mViewModel.setBinding(mBinding);
        mBinding.setViewModel(mViewModel);

        // 初始化展示店铺名称
        String shopName = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_NAME);
        boolean shopNameIsEmpty = TextUtils.isEmpty(shopName);
        mBinding.tvShop.setText(shopNameIsEmpty ? "点击设置店铺名称" : shopName);

        // 初始化展示店铺地址
        String shopAddress = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_ADDRESS);
        boolean shopAddressIsEmpty = TextUtils.isEmpty(shopAddress);
        mBinding.tvShopAddress.setText(shopAddressIsEmpty ? "点击设置店铺地址" : shopAddress);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shops_setting;
    }

    @Override
    public Class<ShopsSettingViewModel> onBindViewModel() {
        return ShopsSettingViewModel.class;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        String isFinish = bundle.getString(Const.ShopsSettingConst.FINISH_IS_JUMP_ORDER_PAGE);
        finishIsJumpOrderPage = TextUtils.equals(isFinish, "true");
        s = bundle.getString("key");
    }

    @Override
    public void onFinishCLickCall() {
        super.onFinishCLickCall();
        cusFinish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cusFinish();
    }

    /**
     * 自定义返回逻辑
     * 正常返回的前提下，判断关闭页面后是否跳转下单页
     */
    private void cusFinish() {
        String shop_address = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_ADDRESS);
        String shop_name = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_NAME);
        // 点击下单进入的页面，设置成功，返回则跳转下单页面
        if (finishIsJumpOrderPage && !TextUtils.isEmpty(shop_address) && !TextUtils.isEmpty(shop_name)) {
            // 进入下单页面
            Bundle bundle = new Bundle();
            bundle.putString("key", s);
            JumpUtils.jump(PlaceOrderActivity.class, bundle);
        }
    }
}
