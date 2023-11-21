package com.example.networkpro.ui.adapter;

import com.example.lib_bean.bean.local.ViewPageModularBean;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.networkpro.R;
import com.example.networkpro.databinding.HomeModularLayoutBinding;

import java.util.List;

/**
 * Created by 王鑫哲 on 2021/12/4 上午 10:33
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeModularAdapter extends BaseAdapter<ViewPageModularBean, HomeModularLayoutBinding> {

    public HomeModularAdapter(List<ViewPageModularBean> dataBean, int BR_id) {
        super(dataBean, BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_modular_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<HomeModularLayoutBinding> mHolder, int position, ViewPageModularBean data) {

    }
}
