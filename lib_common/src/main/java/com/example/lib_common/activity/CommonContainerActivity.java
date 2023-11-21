package com.example.lib_common.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.lib_common.R;
import com.example.lib_common.databinding.ActivityCommonContainerLayoutBinding;
import com.example.lib_utils.ToastUtils;

import java.io.Serializable;

/**
 * Created by 王鑫哲 on 2022/5/10 下午 10:15
 * E-mail: User_wang_178@163.com
 * Ps: 公共展示Fragment的activity 用于展示普通列表等   activity不涉及业务
 */
public class CommonContainerActivity extends BaseActivity<ActivityCommonContainerLayoutBinding> {

    public static String title;

    public static Fragment mFragment = null;

    @Override
    protected void initView() {
        if (mFragment != null) {
            mTopBar.setTitle(title);
            showFragment(R.id.fl_fragment, mFragment);
        } else {
            ToastUtils.show("设置有误！|");
            finish();
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_common_container_layout;
    }

    @Override
    protected void getBundle(Bundle bundle) {
        title = bundle.getString(title);
        Serializable fragment = bundle.getSerializable("mFragment");
        if (fragment instanceof Fragment) {
            mFragment = (Fragment) fragment;
        }
    }
}
