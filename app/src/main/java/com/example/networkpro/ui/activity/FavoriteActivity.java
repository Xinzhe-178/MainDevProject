package com.example.networkpro.ui.activity;

import com.example.lib_common.activity.BaseActivity;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityFavoriteBinding;
import com.example.networkpro.ui.fragment.FavoriteFragment;

/**
 * Created by 王鑫哲 on 2023/5/26 11:06 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class FavoriteActivity extends BaseActivity<ActivityFavoriteBinding> {
    @Override
    protected void initView() {
        mTopBar.setTitle("收藏");

        showFragment(R.id.fl_fav, new FavoriteFragment());
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_favorite;
    }
}
