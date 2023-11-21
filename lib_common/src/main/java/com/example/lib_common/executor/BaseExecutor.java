package com.example.lib_common.executor;

import com.example.lib_network.callback.ApiService;
import com.example.lib_network.callback.Net;
import com.example.lib_network.callback.NetCallBack;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2022/3/17 下午 06:07
 * E-mail: User_wang_178@163.com
 * Ps: base执行者 如需用观察者模式  则执行者继承自该类
 */
public class BaseExecutor<E> {

    /**
     * 观察者
     */
    public final E mExecutor;

    public BaseExecutor(E e) {
        mExecutor = e;
    }

    public ApiService mApi = Net.getApi();

    public <T> void net(Observable<T> observable, NetCallBack<T> netCallBack) {
        Net.initNetService(observable, netCallBack);
    }

    public ApiService getUrlApi(String baseUrl) {
        return Net.getUrlApiService(baseUrl);
    }
}
