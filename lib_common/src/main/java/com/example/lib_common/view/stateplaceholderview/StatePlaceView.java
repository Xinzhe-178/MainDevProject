package com.example.lib_common.view.stateplaceholderview;

import android.view.View;
import android.view.ViewGroup;

import com.example.lib_common.R;
import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_utils.AntiShakeUtils;

/**
 * Created by 王鑫哲 on 2023/6/1 11:19 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class StatePlaceView implements IStateSetPlaceholderView {

    private final ViewGroup mGroupView;

    private final IStatePlaceholderView mPlaceholderView;

    public static StatePlaceView register(ViewGroup groupView, IStatePlaceholderView placeholderView) {
        return new StatePlaceView(groupView, placeholderView);
    }

    public StatePlaceView(ViewGroup groupView, IStatePlaceholderView placeholderView) {
        mGroupView = groupView;
        mPlaceholderView = placeholderView;

        // 展示预加载页面
        setPlaceholderState(StatePlaceType.Preload);
    }

    @Override
    public void setPlaceholderState(StatePlaceType type) {
        // 关闭封装的Dialog
        LoadingDialogController.getInstance().dismissDialog();

        removerGroup();
        switch (type) {
            case Error:
                showPlaceHolder(mPlaceholderView.getErrorView());
                break;
            case Empty:
                showPlaceHolder(mPlaceholderView.getEmptyView());
                break;
            case Null:
                showPlaceHolder(mPlaceholderView.getNullView());
                break;
            case NoNetWork:
                showPlaceHolder(mPlaceholderView.getNoNetWorkView());
                break;
            case Preload:
                showPlaceHolder(mPlaceholderView.getPreloadView());
                break;
        }
    }

    private void showPlaceHolder(View view) {
        if (view == null) {
            // 如果为空，就视为不需要展示view
            return;
        }

        // 设置点击事件，不设置否则会导致点击穿透
        view.setOnClickListener(view1 -> {

        });

        // 点击重试逻辑 因为不是每一个View都会有这个按钮，会存在找不到的情况，这里try catch
        try {
            view.findViewById(R.id.tv_place_retry).setOnClickListener(v -> {
                if (AntiShakeUtils.isInvalidClick(view)) return;
                mPlaceholderView.onRetryClickListener();
            });
        } catch (Throwable throwable) {

        }

        mGroupView.addView(view);
        mGroupView.setVisibility(View.VISIBLE);
    }

    private void removerGroup() {
        mGroupView.removeAllViews();
    }
}
