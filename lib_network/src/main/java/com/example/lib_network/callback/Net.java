package com.example.lib_network.callback;

import com.alibaba.fastjson.JSON;
import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.BaseObjBean;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.NetUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.lib_utils.UtilApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 王鑫哲 on 2021/8/24 上午 11:42
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class Net {
    /**
     * 读超时长，单位：毫秒
     */
    public static final int READ_TIME_OUT = 7000;
    /**
     * 连接时长，单位：毫秒
     */
    public static final int CONNECT_TIME_OUT = 7000;

    private Net() {
    }

    private volatile static ApiService apiService;

    public static ApiService getApi() {
        if (apiService == null) {
            synchronized (Net.class) {
                if (apiService == null) {
                    apiService = getApiService();
                }
            }
        }
        return apiService;
    }

    /**
     * 默认域名
     *
     * @return
     */
    private static ApiService getApiService() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Urls.DEF_BASE_URL)
                .client(getHttpClient())
                .build()
                .create(ApiService.class);
    }

    /**
     * 非默认域名 重写此方法设置域名得到ApiService
     *
     * @param baseUrl
     * @return
     */
    public static ApiService getUrlApiService(String baseUrl) {
        // Use JsonReader.setLenient(true) to accept malformed JSON at line 1 column 1 pat
        // https://cloud.tencent.com/developer/ask/sof/75245
        // 修复请求出错
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(TextUtils.isEmpty(baseUrl) ? Urls.DEF_BASE_URL : baseUrl)
                .client(getHttpClient())
                .build()
                .create(ApiService.class);
    }

    private static OkHttpClient getHttpClient() {
        return new OkHttpClient().newBuilder()
                .addInterceptor(new RetrofitLogInterceptor(HeaderManage.getCommonHeader()))
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS) //连接超时
                .writeTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)  //读取超时
                .retryOnConnectionFailure(true) // 失败自动重连
                .build();
    }

    public static <T> void initNetService(Observable<T> observable, NetCallBack<T> netCallBack) {
        initNetService(observable, netCallBack, () -> {
            ToastUtils.show("网络连接失败，请检查网路是否连接");
        });
    }

    /**
     * 处理返回结各种状态
     * 返回报文为null 或data为null 都视为失败
     *
     * @param observable
     * @param netCallBack
     * @param <T>
     */
    public static <T> void initNetService(Observable<T> observable, NetCallBack<T> netCallBack, NetworkStatusCall networkStatusCall) {
        if (observable == null || netCallBack == null) {
            LogUtils.PrintD("initNetService", "非法请求");
            return;
        }

        if (networkStatusCall == null) {
            networkStatusCall = () -> ToastUtils.show("网络连接失败，请检查网路是否连接");
        }

        if (!NetUtils.isNetworkAvailable()) {
            networkStatusCall.onNoNetWork();
            return;
        }

        try {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<T>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            netCallBack.onStart();
                            LogUtils.PrintD("netCallBack->" + "onStart");
                        }

                        @Override
                        public void onNext(T t) {
                            if (t == null || TextUtils.equals(t.toString(), "")) {
                                LogUtils.PrintD("netCallBack->" + "onSuccess->返回报文为空");
                                netCallBack.onError("返回报文为空 " + JSON.toJSONString(t));
                                return;
                            }

                            // 玩安卓开放api：errorCode！=0 即为失败

                            if (t instanceof BaseArrBean) {
                                BaseArrBean bean = (BaseArrBean) t;
                                if (bean.errorCode == 0) {
                                    netCallBack.onSuccess(t);
                                } else {
                                    netCallBack.onError(bean.errorMsg);
                                }
                                LogUtils.PrintD("netCallBack->" + "onSuccess-->t=BaseArrBean->" + JSON.toJSONString(bean));
                            } else if (t instanceof BaseObjBean) {
                                BaseObjBean bean = (BaseObjBean) t;
                                if (bean.errorCode == 0) {
                                    netCallBack.onSuccess(t);
                                } else {
                                    netCallBack.onError(bean.errorMsg);
                                }
                                LogUtils.PrintD("netCallBack->" + "onSuccess-->t=BaseObjBean->" + JSON.toJSONString(bean));
                            } else {
                                // 这里处理其他网络类型网络请求(也就是非玩安卓api)
                                netCallBack.onSuccess(t);
                                LogUtils.PrintD("netCallBack->" + "onSuccess-->t!=Base-> " + JSON.toJSONString(t));
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            netCallBack.onError(e.getMessage());
                            LogUtils.PrintD("netCallBack->" + "onError->" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {
                            netCallBack.onComplete();
                            LogUtils.PrintD("netCallBack->" + "onComplete");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
