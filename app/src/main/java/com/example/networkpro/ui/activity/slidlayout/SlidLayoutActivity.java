package com.example.networkpro.ui.activity.slidlayout;

import com.example.lib_common.activity.BaseActivity;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivitySlidLayoutBinding;

/**
 * Created by 王鑫哲 on 2021/12/29 下午 01:38
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SlidLayoutActivity extends BaseActivity<ActivitySlidLayoutBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_slid_layout;
    }

    @Override
    protected void initView() {
        DemoListAdapter adapter = new DemoListAdapter();
        mBinding.listviewId.setAdapter(adapter);

        DemoPageAdapter pageAdapter = new DemoPageAdapter(this);
        mBinding.viewpager.setAdapter(pageAdapter);
    }

}
