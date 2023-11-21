package com.example.networkpro.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.consts.Const;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ShopCarViewLayoutBinding;
import com.example.networkpro.ui.activity.ShopParticularsActivity;
import com.example.networkpro.ui.adapter.HomeShopCarAdapter;
import com.example.networkpro.viewmodel.HomeViewModel;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by 王鑫哲 on 2023/7/14 2:51 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ShopCarView extends BaseFrameLayout<ShopCarViewLayoutBinding> {

    /**
     * 购物车Adapter
     */
    private HomeShopCarAdapter mCarAdapter;

    private HomeBottomShopView mBottomView;

    private HomeViewModel mHomeViewModel;

    public ShopCarView(@NonNull Context context) {
        super(context);
    }

    public ShopCarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setBottomView(HomeBottomShopView bottomView) {
        mBottomView = bottomView;
    }

    public void setViewModel(HomeViewModel homeViewModel) {
        mHomeViewModel = homeViewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.shop_car_view_layout;
    }

    @Override
    protected void initView() {
        ViewGroup.LayoutParams layoutParams = mBinding.clGroup.getLayoutParams();
        layoutParams.height = (int) (DensityUtils.getHeight() * 0.4f);
        mBinding.clGroup.setLayoutParams(layoutParams);

        mBinding.rvHomeShopCar.setLayoutManager(new LinearLayoutManager(getContext()));
        mCarAdapter = new HomeShopCarAdapter(BR.model);
        mBinding.rvHomeShopCar.setAdapter(mCarAdapter);
    }

    @Override
    protected void initListener() {
        mCarAdapter.setOnStepperClickListener((pos, count, type) -> {
            // 同步主要数据数量 这一行必须要放在第一行 因为下面有删除数据操作，会导致下标越界
            mHomeViewModel.carStepperStateRefresh(pos, type, getShopCarListData().get(pos).indexOfLeft);
            // 1为减  2为加
            if (type == 1) {
                if (count == 0) {
                    // 点击这个item后 数量没有了 则删除这个商品
                    /**
                     * 此处删除这么写是因为 mShopCarListData是购物车的主数据 而removeItem仅仅只删除了已经设置给adapter的数据
                     * 会有问题如已经删除过的数据  关闭弹窗再弹出时 数据还在等问题 所以需要让主数据和adapter的数据实时同步
                     */
                    mCarAdapter.removeItem(pos);
                    setShopCarListData(mCarAdapter.getDataBean());
                    if (getShopCarListData().size() <= 0) {
                        mBinding.tvDefHint.setVisibility(View.VISIBLE);
                    }
                } else {
                    // 点击这个item后 还有数量 则更新数量
                    // 将当前条目显示的数量-1 并只刷新当前点击的item
                    getShopCarListData().get(pos).count -= 1;
                    mCarAdapter.notifyItemChanged(pos);
                }
            } else {
                // 数量加一
                getShopCarListData().get(pos).count += 1;
                mCarAdapter.notifyItemChanged(pos);
            }
            setShopCarListData(mCarAdapter.getDataBean());
            mBottomView.setModel(getShopCarListData());
        });

        mCarAdapter.addItemClickListener((data, holder, pos) -> jumpShopDetails(data));

        mBinding.llGroup.setOnClickListener(v -> mHomeViewModel.setShopCarViewIsShow(false));
    }

    public void update(List<HomeRecyclerGroupBean.RightGroup> list) {
        mCarAdapter.update(list);
    }

    /**
     * 跳转商品详情逻辑
     *
     * @param data
     */
    public void jumpShopDetails(HomeRecyclerGroupBean.RightGroup data) {
        if (data.childImageData != null && data.childImageData.mImageLists != null && data.childImageData.mImageLists.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.JumpBundle.RIGHT_ITEM_JUMP_SHOP_PAR_LIST_KEY, data);
            JumpUtils.jump(ShopParticularsActivity.class, bundle);
            LogUtils.PrintD("data", " = " + new Gson().toJson(data));
        } else {
            ToastUtils.show("该商品没有详情页哦");
        }
    }

    /**
     * 当前购物车List
     */
    public List<HomeRecyclerGroupBean.RightGroup> getShopCarListData() {
        return mHomeViewModel.getShopCarListData();
    }

    /**
     * 这里操作的数据需要是一个数据，因为这个View独立出来了 所以通过这种方式
     *
     * @param list
     */
    public void setShopCarListData(List<HomeRecyclerGroupBean.RightGroup> list) {
        mHomeViewModel.setShopCarListData(list);
    }
}
