package com.example.networkpro.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.Res;
import com.example.lib_utils.ShareData;
import com.example.networkpro.R;
import com.example.networkpro.databinding.PlaceOrderTopViewLayoutBinding;

/**
 * Created by 王鑫哲 on 2023/7/21 10:19 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class PlaceOrderTopView extends BaseFrameLayout<PlaceOrderTopViewLayoutBinding> {

    private boolean isSelectLeft = true;

    private final int selectHeight = DensityUtils.dp(45);

    private final int noSelectHeight = DensityUtils.dp(35);

    /**
     * view绘制完成后初始化操作 会无限循环 这里打标记解决
     */
    private boolean flag = true;

    public PlaceOrderTopView(@NonNull Context context) {
        super(context);
    }

    public PlaceOrderTopView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.place_order_top_view_layout;
    }

    @Override
    protected void initView() {
        mBinding.setView(this);

        // 绘制完成时 初始化大小
        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            if (flag) {
                refreshState();
                flag = false;
            }
        });

        // 设置店铺地址
        String shopAddress = ShareData.getShareStringData(Const.ShopsSettingConst.SHOP_ADDRESS);
        mBinding.tvAddress.setText(shopAddress);
    }

    private void refreshState() {
        ViewGroup.LayoutParams leftParams = mBinding.tvLeft.getLayoutParams();
        ViewGroup.LayoutParams rightParams = mBinding.tvRight.getLayoutParams();
        ViewGroup.LayoutParams centerParams = mBinding.vCenter.getLayoutParams();

        if (isSelectLeft) {
            leftParams.height = selectHeight;
            rightParams.height = noSelectHeight;

            mBinding.tvLeft.setBackground(Res.Drawable(R.drawable.place_order_tab_on));
            mBinding.tvRight.setBackground(Res.Drawable(R.drawable.place_order_tab_no_right));

            mBinding.tvLeft.setTypeface(Typeface.DEFAULT_BOLD);
            mBinding.tvRight.setTypeface(Typeface.DEFAULT);

            mBinding.tvLeft.setTextSize(16);
            mBinding.tvRight.setTextSize(14);
        } else {
            leftParams.height = noSelectHeight;
            rightParams.height = selectHeight;

            mBinding.tvLeft.setBackground(Res.Drawable(R.drawable.place_order_tab_no_left));
            mBinding.tvRight.setBackground(Res.Drawable(R.drawable.place_order_tab_on));

            mBinding.tvLeft.setTypeface(Typeface.DEFAULT);
            mBinding.tvRight.setTypeface(Typeface.DEFAULT_BOLD);

            mBinding.tvLeft.setTextSize(14);
            mBinding.tvRight.setTextSize(16);
        }
        centerParams.height = noSelectHeight;

        mBinding.tvLeft.setLayoutParams(leftParams);
        mBinding.tvRight.setLayoutParams(rightParams);
        mBinding.vCenter.setLayoutParams(centerParams);

        isSelectLeft = !isSelectLeft;
    }

    public OnBindingClickParamsCall<Boolean> onTabClick = aBoolean -> {
        isSelectLeft = aBoolean;
        refreshState();
    };
}
