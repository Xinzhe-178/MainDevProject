package com.example.lib_common.activity;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.mvvm.BaseViewModel;

/**
 * Created by 王鑫哲 on 2021/11/2 下午 03:38
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseMvvmActivity<VDB extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<VDB> {
    /**
     * ViewModel
     */
    protected VM mViewModel;

    /**
     * activity返回监听 默认不对其进行监听 走finish逻辑 一旦设置了返回监听 将需要自己设置处理逻辑
     */
    protected OnBindingClickCall mOnBackListener;

    public void setOnBackListener(OnBindingClickCall onBackListener) {
        mOnBackListener = onBackListener;
    }

    @Override
    protected void initMvvm() {
        mViewModel = new ViewModelProvider(this).get(onBindViewModel());

        //绑定生命周期
        getLifecycle().addObserver(mViewModel);

        //初始化页面观察
        initMutableLiveData();
    }

    protected void initMutableLiveData() {
        //关闭activity
        mViewModel.finish.observe(this, o -> finish());
    }

    /**
     * 返回ViewModel
     *
     * @return
     */
    public abstract Class<VM> onBindViewModel();

    @Override
    public void onBackPressed() {
        //        super.onBackPressed();
        if (mOnBackListener != null) {
            mOnBackListener.clickCall();
        } else {
            mViewModel.onBackPressed();
        }
    }

    public VM getViewModel() {
        return mViewModel;
    }
}
