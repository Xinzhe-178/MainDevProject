package com.example.networkpro.ui.adapter;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.networkpro.R;
import com.example.networkpro.databinding.HomeRecyclerRightLayoutBinding;
import com.example.networkpro.viewmodel.HomeViewModel;

/**
 * Created by 王鑫哲 on 2021/11/24 下午 06:24
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeRecyclerRightAdapter extends BaseAdapter<HomeRecyclerGroupBean.RightGroup, HomeRecyclerRightLayoutBinding> {

    private final HomeViewModel mViewModel;

    public HomeRecyclerRightAdapter(int BR_id, HomeViewModel viewModel) {
        super(BR_id);
        mViewModel = viewModel;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_recycler_right_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<HomeRecyclerRightLayoutBinding> Holder, int position, HomeRecyclerGroupBean.RightGroup data) {
        Holder.getBinding().setViewModel(mViewModel);
    }
}
