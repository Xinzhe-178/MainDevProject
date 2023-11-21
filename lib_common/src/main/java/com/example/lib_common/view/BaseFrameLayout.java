package com.example.lib_common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * Created by 王鑫哲 on 2021/11/5 下午 02:46
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseFrameLayout<VDB extends ViewDataBinding> extends FrameLayout {
    protected Context mContext;
    protected VDB mBinding;

    public BaseFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public BaseFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        if (getLayoutId() != 0) {
            mBinding = inflate(getLayoutId());
            addViewX(mBinding.getRoot());
        }

        initView();
        initData();
        initListener();
    }

    public void addViewX(View view) {
        addView(view);
    }

    protected abstract @LayoutRes
    int getLayoutId();

    protected abstract void initView();

    protected void initData() {

    }

    protected void initListener() {

    }

    /**
     * 获取布局ViewDataBinding
     *
     * @param layoutId
     * @return
     */
    protected <T extends ViewDataBinding> T inflate(int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, null, false);
    }

    public VDB getBinding() {
        return mBinding;
    }

    public View getView() {
        return mBinding.getRoot();
    }
}
