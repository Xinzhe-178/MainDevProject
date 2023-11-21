package com.example.lib_network.callback;

/**
 * Created by 王鑫哲 on 2021/8/24 上午 11:39
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface NetCallBack<T> {
    /**
     * 开始请求
     */
    default void onStart() {

    }

    /**
     * 成功回调
     *
     * @param result
     */
    default void onSuccess(T result) {

    }

    /**
     * 请求完成
     */
    default void onComplete() {

    }

    /**
     * 异常处理
     */
    default void onError(String errorMsg) {

    }
}
