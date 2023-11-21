package com.example.networkpro.ui.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_common.view.BaseFrameLayout;
import com.example.networkpro.R;
import com.example.networkpro.databinding.SeekHotLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/11/29 22:56
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekHotView extends BaseFrameLayout<SeekHotLayoutBinding> {

    public SeekHotView(@NonNull Context context) {
        super(context);
    }

    public SeekHotView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.seek_hot_layout;
    }

    @Override
    protected void initView() {
        // 热门搜索模块加载监听 只有加载成功后才会显示该模块
        mBinding.vSeekHot.setOnLoadStateCall(aBoolean -> {
            mBinding.llGroup.setVisibility(VISIBLE);
        });
    }
}
