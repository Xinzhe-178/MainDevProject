package com.example.lib_network.callback;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2023/2/7 3:50 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class Request {

    /**
     * 默认域名的ApiService
     */
    public static final ApiService mApi = Net.getApi();

    /**
     * 进行请求
     *
     * @param observable
     * @param netCallBack
     * @param <T>
     */
    public static <T> void net(Observable<T> observable, NetCallBack<T> netCallBack, NetworkStatusCall networkStatusCall) {
        Net.initNetService(observable, netCallBack, networkStatusCall);
    }

    public static <T> void net(Observable<T> observable, NetCallBack<T> netCallBack) {
        net(observable, netCallBack, null);
    }

    /**
     * 快捷获取ApiService 传入BaseUrl进行获取
     *
     * @param baseUrl
     * @return
     */
    public static ApiService getUrlApi(String baseUrl) {
        return Net.getUrlApiService(baseUrl);
    }
}
