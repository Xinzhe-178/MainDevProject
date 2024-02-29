package com.example.networkpro.ui.fragment;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_common.consts.Const;
import com.example.lib_common.manage.WanAndroidApiManager;
import com.example.networkpro.BR;
import com.example.networkpro.ui.adapter.RecyclerAdapter;
import com.example.networkpro.viewmodel.SeekInputViewModel;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2024/2/26 10:43 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekValueAndroidFragment extends FavoriteFragment {

    private final SeekInputViewModel mViewModel;

    public SeekValueAndroidFragment(SeekInputViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public Observable<BaseArrBean<RecyclerBean>> getNetObservable(int page) {
        return WanAndroidApiManager.getInstance().getHomeDataObservable(page);
    }

    @Override
    public RecyclerAdapter getAdapter() {
        return new RecyclerAdapter(BR.model, Const.CommonWebViewPageConst.CUS_VIEW_DATA_HOME_LIST);
    }

    @Override
    protected void processDefaultMethodView() {

    }
}
