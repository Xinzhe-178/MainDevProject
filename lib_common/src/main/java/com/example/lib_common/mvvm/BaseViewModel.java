package com.example.lib_common.mvvm;

import android.app.Application;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.example.lib_network.callback.ApiService;
import com.example.lib_network.callback.Net;
import com.example.lib_network.callback.NetCallBack;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2021/10/25 上午 11:17
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BaseViewModel extends AndroidViewModel implements IBaseViewModel {
    /**
     * 关闭 Activity
     */
    public MutableLiveData<Object> finish = new MutableLiveData<>();

    protected final Application mContent;

    public BaseViewModel(@NonNull @NotNull Application application) {
        super(application);
        mContent = application;
    }

    /*----------------------生命周期start----------------------*/
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    /*----------------------生命周期end----------------------*/

    /*----------------------网络请求start----------------------*/

    public ApiService mApi = Net.getApi();

    public <T> void net(Observable<T> observable, NetCallBack<T> netCallBack) {
        Net.initNetService(observable, netCallBack);
    }

    public ApiService getUrlApi(String baseUrl) {
        return Net.getUrlApiService(baseUrl);
    }

    /*----------------------网络请求end----------------------*/

    /**
     * 获取布局ViewDataBinding
     *
     * @param layoutId
     * @return
     */
    protected <T extends ViewDataBinding> T inflate(int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(mContent), layoutId, null, false);
    }

    /**
     * 系统返回键监听
     */
    public void onBackPressed() {
        finish.setValue("");
    }
}
