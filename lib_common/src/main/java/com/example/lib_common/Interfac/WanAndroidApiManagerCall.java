package com.example.lib_common.Interfac;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.BaseObjBean;
import com.example.lib_bean.bean.LoginBean;
import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_bean.bean.RegisterBean;
import com.example.lib_network.callback.NetCallBack;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2022/9/13 3:05 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface WanAndroidApiManagerCall {
    void onLoginApiCall(Map<String, String> map, NetCallBack<BaseObjBean<LoginBean>> callBack);

    void onRegisterApiCall(Map<String, String> map, NetCallBack<BaseObjBean<RegisterBean>> callBack);

    void onAddFavArticleApiCall(int id, NetCallBack<BaseObjBean<Object>> callBack);

    void onRemoveFavArticleApiCall(int id, NetCallBack<BaseObjBean<Object>> callBack);

    Observable<BaseArrBean<RecyclerBean>> getFavoriteDataObservable(int page);

    Observable<BaseArrBean<RecyclerBean>> getHomeDataObservable(int page);
}
