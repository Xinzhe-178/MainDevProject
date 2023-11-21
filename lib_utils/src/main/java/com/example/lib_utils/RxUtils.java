package com.example.lib_utils;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Gabriel on 2020/06/04.
 * Email 17600284843@163.com
 * Description:创建RxJava的工具类
 */
public class RxUtils {

    public interface Callback {
        void onFinish();
    }

    /**
     * 定时任务
     */
    @SuppressLint("CheckResult")
    public static void timedTask(long milliseconds, final Callback callback) {
        Disposable subscribe = Observable.timer(milliseconds, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> callback.onFinish());
    }

    /**
     * 定时任务
     */
    @SuppressLint("CheckResult")
    public static Disposable timedTask(long milliseconds, TimeUnit unit, final Callback callback) {
        return Observable.timer(milliseconds, unit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> callback.onFinish());
    }

    /**
     * 指定时间后结束
     *
     * @param size     指定的时间
     * @param consumer 时间
     * @return 取消订阅实例
     */
    @SuppressLint("CheckResult")
    public static Disposable interval(int size, Consumer<Long> consumer) {
        return Observable.interval(0, 1, TimeUnit.SECONDS).take(size)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 指定时间后结束
     *
     * @param size     指定的时间
     * @param consumer 时间
     * @return 取消订阅实例
     */
    @SuppressLint("CheckResult")
    public static Disposable interval(int size, Consumer<Long> consumer, TimeUnit unit) {
        return Observable.interval(0, 1, unit).take(size)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer);
    }

    /**
     * 每隔milliseconds毫秒后执行指定动作
     */
    public static void countDown(int count_time, Observer<Long> observer) {
        Observable.interval(0, 1, TimeUnit.SECONDS) //0延迟  每隔1秒触发
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .take(count_time + 1) //设置循环次数
                .map(aLong -> count_time - aLong) //从60-1
                .subscribe(observer);
    }
}
