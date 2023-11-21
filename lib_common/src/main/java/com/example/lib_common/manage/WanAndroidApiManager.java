package com.example.lib_common.manage;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.BaseObjBean;
import com.example.lib_bean.bean.LoginBean;
import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_bean.bean.RegisterBean;
import com.example.lib_common.Interfac.WanAndroidApiManagerCall;
import com.example.lib_network.callback.Net;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_network.callback.Request;

import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2022/9/13 2:55 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class WanAndroidApiManager extends ApiManager implements WanAndroidApiManagerCall {

    private WanAndroidApiManager() {
        super();
    }

    public static WanAndroidApiManager getInstance() {
        return new WanAndroidApiManager();
    }

    @Override
    public void onLoginApiCall(Map<String, String> map, NetCallBack<BaseObjBean<LoginBean>> callBack) {
        Request.net(Request.mApi.getLoginApi(map), callBack);
    }

    @Override
    public void onRegisterApiCall(Map<String, String> map, NetCallBack<BaseObjBean<RegisterBean>> callBack) {
        Request.net(Request.mApi.getRegisterApi(map), callBack);
    }

    @Override
    public void onAddFavArticleApiCall(int id, NetCallBack<BaseObjBean<Object>> callBack) {
        Request.net(Request.mApi.addFavArticle(id, userName, userPassword), callBack);
    }

    @Override
    public void onRemoveFavArticleApiCall(int id, NetCallBack<BaseObjBean<Object>> callBack) {
        Request.net(Request.mApi.removeFavArticle(id, userName, userPassword), callBack);
    }

    @Override
    public Observable<BaseArrBean<RecyclerBean>> getFavoriteDataObservable(int page) {
        return Net.getApi().getFavListData(page, userName, userPassword);
    }

    @Override
    public Observable<BaseArrBean<RecyclerBean>> getHomeDataObservable(int page) {
        return Net.getApi().getHomeListData(page);
    }
}
