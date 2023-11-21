package com.example.networkpro.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.lib_common.consts.Const;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.topbar.TopBarView;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ShareData;
import com.example.networkpro.databinding.ActivityPlaceOrderLayoutBinding;

/**
 * Created by 王鑫哲 on 2023/7/17 3:46 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class PlaceOrderViewModel extends BaseViewModel {

    public PlaceOrderViewModel(@NonNull Application application) {
        super(application);
    }

    public String getTitleText() {
        return "提交订单";
    }

    /**
     * 标题切换及透明度刷新
     *
     * @param binding
     */
    public void initTitleAlpha(ActivityPlaceOrderLayoutBinding binding) {
        // 店铺地址
        String shopAddress = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_ADDRESS);

        TopBarView topBarView = binding.vTopBar;
        int titleHeight = DensityUtils.dp(60);
        int addressHeight = DensityUtils.dp(110);

        binding.nsvView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            LogUtils.PrintD(getClass() + "-scrollY-> " + scrollY);
            if (scrollY <= 0) {
                // 为顶部 不透明 标题为默认标题
                binding.vTopBar.setTitle(getTitleText());
                topBarView.getTitleView().setAlpha(1);
            } else if (scrollY <= titleHeight) {
                // 设置为默认标题
                binding.vTopBar.setTitle(getTitleText());
                // 这个滑动区域为到地址之前 将其刷新透明度 由高到低
                float scale = (float) scrollY / titleHeight;
                topBarView.getTitleView().setAlpha(1f - scale);
            } else if (scrollY <= addressHeight) {
                // 将当前地址设置为标题
                binding.vTopBar.setTitle(shopAddress);
                // 这个为地址区域的高度 刷新透明度 由低到高
                float scale = (float) scrollY / addressHeight;
                topBarView.getTitleView().setAlpha((float) (scale * 0.7));
            } else {
                // 滑动超过以上范围，不更改其标题，只将透明度设为不透明
                topBarView.getTitleView().setAlpha(1);
            }
        });
    }
}
