package com.example.lib_common.fragment;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.example.lib_common.mvvm.BaseViewModel;

/**
 * Created by 王鑫哲 on 2021/11/2 下午 04:48
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseMvvmFragment<VM extends BaseViewModel, VDB extends ViewDataBinding> extends BaseFragment<VDB> {
    /**
     * ViewModel
     */
    protected VM mViewModel;

    @Override
    protected void initMvvm() {
        // 绑定ViewModel
        mViewModel = new ViewModelProvider(this).get(onBindViewModel());
        // 绑定生命周期
        getLifecycle().addObserver(mViewModel);
        // 初始化页面观察
        initMutableLiveData();
    }

    protected void initMutableLiveData() {
        // 关闭activity
        mViewModel.finish.observe(this, o -> {
            if (getActivity() != null) {
                getActivity().finish();
            }
        });
    }

    /**
     * 返回ViewModel
     *
     * @return
     */
    public abstract Class<VM> onBindViewModel();
}
