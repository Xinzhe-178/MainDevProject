package com.example.networkpro.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_bean.bean.SeekHotListBean;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_network.callback.ApiService;
import com.example.lib_network.callback.Net;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_network.callback.Urls;
import com.example.lib_utils.DensityUtils;
import com.example.networkpro.BR;
import com.example.networkpro.ui.adapter.SeekHotListAdapter;

/**
 * Created by 王鑫哲 on 2022/11/30 17:39
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekHotRecyclerView extends RecyclerView {

    private SeekHotListAdapter mAdapter;

    private OnBindingClickParamsCall<Boolean> onLoadStateCall;

    public void setOnLoadStateCall(OnBindingClickParamsCall<Boolean> onLoadStateCall) {
        this.onLoadStateCall = onLoadStateCall;
    }

    public SeekHotRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public SeekHotRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        initData();
    }

    private void initData() {
        ApiService apiService = Net.getUrlApiService(Urls.HAN_XIAO_HAN_URL);
        Net.initNetService(apiService.getSeekHotListData("baiduRD"), new NetCallBack<SeekHotListBean>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(SeekHotListBean result) {
                if (result.data != null) {
                    mAdapter.update(result.data);
                    if (onLoadStateCall != null) {
                        onLoadStateCall.clickCall(true);
                    }
                }
            }

            @Override
            public void onError(String errorMsg) {
                if (onLoadStateCall != null) {
                    onLoadStateCall.clickCall(false);
                }
            }
        });
    }

    private void initView() {
        setNestedScrollingEnabled(false);
        mAdapter = new SeekHotListAdapter(BR.model);
        setLayoutManager(new LinearLayoutManager(getContext()));
        // 禁止滑动
        setAdapter(mAdapter);
        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.set(DensityUtils.dp(10), DensityUtils.dp(7), DensityUtils.dp(7), DensityUtils.dp(10));
            }
        });
    }
}
