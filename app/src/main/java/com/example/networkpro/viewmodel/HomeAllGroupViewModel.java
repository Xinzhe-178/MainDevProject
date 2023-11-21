package com.example.networkpro.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.lib_common.Interfac.PresetsNetCallBack;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_network.callback.Request;
import com.example.lib_network.callback.Urls;

/**
 * Created by 王鑫哲 on 2023/7/4 3:41 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeAllGroupViewModel extends BaseViewModel {

    public MutableLiveData<String> getRandomJokeTextData = new MutableLiveData<>();

    public MutableLiveData<String> getRandomIanTextData = new MutableLiveData<>();

    public MutableLiveData<String> getRandomLoveTextData = new MutableLiveData<>();

    public HomeAllGroupViewModel(@NonNull Application application) {
        super(application);
    }

    public void getRandomJokeTextData() {
        Request.net(Request.getUrlApi(Urls.HAN_XIAO_HAN_URL).getRandomJokeTextData(), new PresetsNetCallBack.DefNetCallTwo<Object>() {
            @Override
            public void onSuccess(Object result) {
                super.onSuccess(result);
                getRandomJokeTextData.postValue(result == null ? "" : result.toString());
            }
        });
    }

    public void getRandomIanTextData() {
        Request.net(Request.getUrlApi(Urls.HAN_XIAO_HAN_URL).getRandomIanTextData(), new PresetsNetCallBack.DefNetCallTwo<Object>() {
            @Override
            public void onSuccess(Object result) {
                super.onSuccess(result);
                getRandomIanTextData.postValue(result == null ? "" : result.toString());
            }
        });
    }

    public void getRandomLoveTextData() {
        Request.net(Request.getUrlApi(Urls.HAN_XIAO_HAN_URL).getRandomLoveTextData(), new PresetsNetCallBack.DefNetCallTwo<Object>() {
            @Override
            public void onSuccess(Object result) {
                super.onSuccess(result);
                getRandomLoveTextData.postValue(result == null ? "" : result.toString());
            }
        });
    }
}
