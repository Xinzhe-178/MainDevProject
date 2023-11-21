package com.example.networkpro.ui.adapter;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.networkpro.R;
import com.example.networkpro.databinding.HomeShopCarAdapterLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/2/5 下午 05:30
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeShopCarAdapter extends BaseAdapter<HomeRecyclerGroupBean.RightGroup, HomeShopCarAdapterLayoutBinding> {

    private onStepperClickListener mOnStepperClickListener;

    public void setOnStepperClickListener(onStepperClickListener onStepperClickListener) {
        mOnStepperClickListener = onStepperClickListener;
    }

    public HomeShopCarAdapter(int BR_id) {
        super(BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_shop_car_adapter_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<HomeShopCarAdapterLayoutBinding> holder, int position, HomeRecyclerGroupBean.RightGroup data) {
        HomeShopCarAdapterLayoutBinding binding = holder.getBinding();
        binding.vSv.setSelectCount(data.count);

        // 步进器点击监听
        binding.vSv.setOnClickListener((OnBindingClickParamsCall<Integer>) integer -> {
            if (mOnStepperClickListener != null) {
                mOnStepperClickListener.onClick(position, binding.vSv.getSelectCount(), integer);
            }
        });
    }

    public interface onStepperClickListener {
        void onClick(int pos, int stepperCount, int stepperType);
    }
}
