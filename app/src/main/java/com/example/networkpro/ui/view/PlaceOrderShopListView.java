package com.example.networkpro.ui.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.ShareData;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityPlaceOrderLayoutBinding;
import com.example.networkpro.databinding.PlaceOrderShopListItemLayoutBinding;
import com.example.networkpro.databinding.PlaceOrderShopListViewLayoutBinding;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by 王鑫哲 on 2023/7/19 11:02 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class PlaceOrderShopListView extends BaseFrameLayout<PlaceOrderShopListViewLayoutBinding> {

    /**
     * 当前是否展开
     */
    private boolean isExpand = false;

    /**
     * 当前购物车的数据
     */
    private List<HomeRecyclerGroupBean.RightGroup> list;

    /**
     * 最多显示即为不折叠
     */
    private final int maxExpandCount = 3;

    /**
     * 提交订单页面binding
     */
    private ActivityPlaceOrderLayoutBinding mPlaceOrderLayoutBinding;

    public PlaceOrderShopListView(@NonNull Context context) {
        super(context);
    }

    public PlaceOrderShopListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.place_order_shop_list_view_layout;
    }

    @Override
    protected void initView() {
        String shopName = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_NAME);
        mBinding.setShopName(shopName);
    }

    public void setPageBinding(ActivityPlaceOrderLayoutBinding binding) {
        mPlaceOrderLayoutBinding = binding;
    }

    public void updateData(List<HomeRecyclerGroupBean.RightGroup> rightGroupList) {
        // 总价
        float totalPrice = 0;

        list = rightGroupList;
        if (list == null || list.size() == 0) {
            return;
        }
        for (HomeRecyclerGroupBean.RightGroup data : list) {
            mBinding.llList.addView(getItemView(data));
            totalPrice += data.price * data.count;
        }

        mBinding.setIsExpand(isExpand);
        mBinding.setIsShowExpand(list.size() > maxExpandCount);
        mBinding.setView(this);
        setTotalPrice(totalPrice);

        operateDataExpand();
    }

    private View getItemView(HomeRecyclerGroupBean.RightGroup data) {
        PlaceOrderShopListItemLayoutBinding inflate = inflate(R.layout.place_order_shop_list_item_layout);
        inflate.setModel(data);
        return inflate.getRoot();
    }

    /**
     * 展开/折叠点击
     */
    public OnBindingClickCall onExpandClick = this::operateDataExpand;

    /**
     * 执行折叠和展开后部分的商品
     * 对View进行隐藏/显示操作
     */
    private void operateDataExpand() {
        if (list == null || list.size() == 0 || list.size() <= maxExpandCount) {
            return;
        }
        int childCount = mBinding.llList.getChildCount();
        for (int i = 0; i < childCount; i++) {
            // 前max_expand_count位不对其进行操作
            if (i < maxExpandCount) {
                continue;
            }
            View view = mBinding.llList.getChildAt(i);
            view.setVisibility(isExpand ? VISIBLE : GONE);
        }

        isExpand = !isExpand;
        mBinding.setIsExpand(isExpand);
    }

    /**
     * 设置不同的字体大小
     *
     * @param s
     */
    private void setTotalPrice(float s) {
        // 保留两位小数
        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(2);

        SpannableString spannableString = new SpannableString("小计 ￥" + nf.format(s));
        spannableString.setSpan(new AbsoluteSizeSpan(DensityUtils.dp(13)), 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new AbsoluteSizeSpan(DensityUtils.dp(12)), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 4, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mBinding.tvTotalPrice.setText(spannableString);

        // 设置提交订单页面底部总价
        SpannableString bottomPriceSpan = new SpannableString("￥" + nf.format(s));
        bottomPriceSpan.setSpan(new AbsoluteSizeSpan(DensityUtils.dp(12)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        // 实现小数点后面的数字小的效果
        // 是否包含小数点
        if (bottomPriceSpan.toString().contains(".")) {
            // 取到小数点位置
            int i = bottomPriceSpan.toString().indexOf(".");
            // 将小数点后的价格 字体调小 i + 1是因为小数点本身不变小
            bottomPriceSpan.setSpan(new AbsoluteSizeSpan(DensityUtils.dp(11)), i + 1, bottomPriceSpan.toString().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        mPlaceOrderLayoutBinding.tvPrice.setText(bottomPriceSpan);
    }
}
