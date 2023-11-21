package com.example.lib_network.callback;

import com.example.lib_utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 王鑫哲 on 2021/12/1 下午 03:40
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class RetrofitLogInterceptor implements Interceptor {
    /**
     * 请求头
     */
    private String header;

    public RetrofitLogInterceptor(String header) {
        this.header = header;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis(); //请求开始时间

        request.newBuilder()
                .addHeader("Authorization", header)
                .build();

        Response proceed = chain.proceed(request);

        assert proceed.body() != null;
        LogUtils.PrintE("RetrofitLogInterceptor",
                "请求:\nurl: " + request.url() +
                        "\nheader: " + request.headers() +
                        "\nResponse: " + proceed.body().string());

        Response response = chain.proceed(request);

        long endTime = System.currentTimeMillis(); //请求结束时间

        LogUtils.PrintE("RetrofitLogInterceptor", "请求耗时:->time->" + +(endTime - startTime) + "毫秒");

        return response;
    }
}
