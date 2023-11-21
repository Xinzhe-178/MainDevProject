package com.example.networkpro.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.example.lib_bean.bean.HomeBottomShopBean;
import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.HomeBottomShopLayoutBinding;

import java.util.List;

/**
 * Created by 王鑫哲 on 2022/2/5 上午 11:35
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeBottomShopView extends BaseFrameLayout<HomeBottomShopLayoutBinding> {
    /**
     * 当前购物车的数据
     */
    private List<HomeRecyclerGroupBean.RightGroup> mRightGroups;

    private OnBindingClickParamsCall<String> onCommitListener;

    public void setOnCommitListener(OnBindingClickParamsCall<String> onCommitListener) {
        this.onCommitListener = onCommitListener;
    }

    public HomeBottomShopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_bottom_shop_layout;
    }

    @Override
    public void initView() {
        mBinding.setView(this);
    }

    public OnBindingClickCall GoSettlementClick = () -> {
        if (mRightGroups == null || mRightGroups.size() == 0) {
            ToastUtils.show("购物车是空的哦");
            return;
        }
        if (onCommitListener != null) {
            onCommitListener.clickCall(JSON.toJSONString(mRightGroups));
        }
    };

    public HomeBottomShopView setRightHintText(String text) {
        mBinding.tvGoSettlement.setText(text);
        return this;
    }

    public HomeBottomShopView setModel(HomeBottomShopBean shopBean) {
        shopBean.coverImageUrl = R.drawable.icon_home_bottom_shop;
        mBinding.setModel(shopBean);
        return this;
    }

    public HomeBottomShopView setModel(List<HomeRecyclerGroupBean.RightGroup> shopBean) {
        mRightGroups = shopBean;

        //商品总价
        float commodityTotalPrice = 0f;
        int commodityCount = 0;
        for (HomeRecyclerGroupBean.RightGroup group : shopBean) {
            // 商品数量
            commodityCount += group.count;
            // 商品总价格=单价*数量
            commodityTotalPrice += group.price * group.count;
        }
        setModel(new HomeBottomShopBean(commodityCount, commodityTotalPrice));

        return this;
    }
}
