package com.example.networkpro.ui.fragment;

import androidx.fragment.app.FragmentActivity;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.bean.SeekLenovoBean;
import com.example.lib_common.fragment.BaseMvvmRecyclerFragment;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.BR;
import com.example.networkpro.ui.activity.SeekInputActivity;
import com.example.networkpro.ui.adapter.SeekLenovoAdapter;
import com.example.networkpro.viewmodel.SeekValueViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2022/9/8 21:25
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekLenovoFragment extends BaseMvvmRecyclerFragment<SeekValueViewModel, SeekLenovoBean, SeekLenovoAdapter> {

    private final List<SeekLenovoBean> list = new ArrayList<>();

    @Override
    public Observable<BaseArrBean<SeekLenovoBean>> getNetObservable(int page) {
        return null;
    }

    private void refreshData(SeekLenovoBean bean) {
        if (bean == null || TextUtils.isEmpty(bean.seekText)) {
            return;
        }

        if (mAdapter != null) {
            mAdapter.setInputText(bean.seekText);
        }

        if (list.size() > 0) {
            list.clear();
            mAdapter.removeAll();
        }

        bean.seekText = bean.seekText + "联想";
        for (int i = 0; i < 15; i++) {
            list.add(bean);
        }
        onRequestNetSuccess(list);
    }

    @Override
    public SeekLenovoAdapter getAdapter() {
        return new SeekLenovoAdapter(BR.model);
    }

    @Override
    protected void processDefaultMethodView() {
        // 禁掉上滑加载更多
        setEnableLoadMore(false);
        // 禁掉下拉刷新
        mBinding.smartRecyclerView.setEnableRefresh(false);

        // 监听搜索框内容变化，及时更新列表
        FragmentActivity activity = getActivity();
        if (activity instanceof SeekInputActivity) {
            SeekInputActivity seekInputActivity = (SeekInputActivity) activity;
            seekInputActivity.setOnSeekLenovoListener(this::refreshData);
        }
    }

    @Override
    public Class<SeekValueViewModel> onBindViewModel() {
        return SeekValueViewModel.class;
    }
}