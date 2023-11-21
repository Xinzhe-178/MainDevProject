package com.example.lib_common.view.stateplaceholderview;

import android.view.View;

/**
 * Created by 王鑫哲 on 2023/6/1 10:53 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface IStatePlaceholderView extends IStateSetPlaceholderView {

    View getErrorView();

    View getEmptyView();

    View getNullView();

    View getNoNetWorkView();

    View getPreloadView();

    void onRetryClickListener();
}
