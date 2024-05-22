package com.example.networkpro.ui.fragment;

import com.example.lib_common.fragment.BaseMvvmFragment;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.networkpro.R;
import com.example.networkpro.databinding.FragmentMineLayoutBinding;

/**
 * Created by 王鑫哲 on 2021/11/5 上午 11:00
 * E-mail: User_wang_178@163.com
 * Ps: [首页] fragment
 */
public class MineFragment extends BaseMvvmFragment<BaseViewModel, FragmentMineLayoutBinding> {
    @Override
    protected void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    public Class<BaseViewModel> onBindViewModel() {
        return BaseViewModel.class;
    }
}