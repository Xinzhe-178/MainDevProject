package com.example.networkpro.ui.adapter;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.networkpro.R;
import com.example.networkpro.databinding.HomeRecyclerLeftLayoutBinding;

/**
 * Created by 王鑫哲 on 2021/11/24 下午 06:24
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeRecyclerLeftAdapter extends BaseAdapter<HomeRecyclerGroupBean, HomeRecyclerLeftLayoutBinding> {

    public HomeRecyclerLeftAdapter(int BR_id) {
        super(BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_recycler_left_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<HomeRecyclerLeftLayoutBinding> mHolder, int position, HomeRecyclerGroupBean data) {

    }
}
