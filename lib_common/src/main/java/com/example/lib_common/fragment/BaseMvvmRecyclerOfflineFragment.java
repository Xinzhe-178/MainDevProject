package com.example.lib_common.fragment;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.mvvm.BaseViewModel;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2021/11/2 下午 04:41
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseMvvmRecyclerOfflineFragment<VM extends BaseViewModel, T, BAD extends BaseEasyAdapter> extends BaseMvvmRecyclerFragment<VM, T, BAD> {
    @Override
    public Observable<BaseArrBean<T>> getNetObservable(int page) {
        return null;
    }

    @Override
    protected void processDefaultMethodView() {

    }

    @Override
    public void requestNetworkData(int page) {
        requestOfflineData(page);
    }

    protected abstract void requestOfflineData(int page);
}
