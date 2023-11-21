package com.example.networkpro.ui.fragment;

import android.view.View;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_common.consts.Const;
import com.example.lib_common.fragment.BaseMvvmRecyclerFragment;
import com.example.lib_common.manage.WanAndroidApiManager;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.networkpro.BR;
import com.example.networkpro.R;
import com.example.networkpro.ui.adapter.RecyclerAdapter;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2023/5/26 11:07 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class FavoriteFragment extends BaseMvvmRecyclerFragment<BaseViewModel, RecyclerBean, RecyclerAdapter> {

    @Override
    public Class<BaseViewModel> onBindViewModel() {
        return BaseViewModel.class;
    }

    @Override
    public Observable<BaseArrBean<RecyclerBean>> getNetObservable(int page) {
        return WanAndroidApiManager.getInstance().getFavoriteDataObservable(page);
    }

    @Override
    public RecyclerAdapter getAdapter() {
        return new RecyclerAdapter(BR.model, Const.CommonWebViewPageConst.CUS_VIEW_DATA_FAVORITE_LIST);
    }

    @Override
    protected void processDefaultMethodView() {

    }

    @Override
    public View getPreloadView() {
        return inflate(R.layout.fav_item_placeholder_layout).getRoot();
    }
}
