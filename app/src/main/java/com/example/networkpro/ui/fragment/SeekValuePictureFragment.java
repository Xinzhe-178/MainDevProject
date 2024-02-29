package com.example.networkpro.ui.fragment;

import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.bean.SeekBean;
import com.example.lib_common.fragment.BaseMvvmRecyclerFragment;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_network.callback.Request;
import com.example.lib_network.callback.Urls;
import com.example.lib_utils.KeyboardUtils;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.BR;
import com.example.networkpro.ui.activity.SeekInputActivity;
import com.example.networkpro.ui.adapter.Itemdecoration.SeekItemDecoration;
import com.example.networkpro.ui.adapter.SeekAdapter;
import com.example.networkpro.viewmodel.SeekInputViewModel;
import com.example.networkpro.viewmodel.SeekValueViewModel;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2024/2/26 10:43 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekValuePictureFragment extends BaseMvvmRecyclerFragment<SeekValueViewModel, SeekBean.ListDTO, SeekAdapter> {

    private final SeekInputViewModel mViewModel;

    public SeekValuePictureFragment(SeekInputViewModel viewModel) {
        mViewModel = viewModel;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mBinding.smartRecyclerView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                layoutManager.invalidateSpanAssignments();//重新布局
            }
        });
        return layoutManager;
    }

    @Override
    public void requestNetworkData(int page) {
        if (TextUtils.isEmpty(mViewModel.SEEK_KEY)) {
            return;
        }
        Request.net(Request.getUrlApi(Urls.SEEK_BASE_URL).getSeekListData(mViewModel.SEEK_KEY, page, 15), new NetCallBack<SeekBean>() {
            @Override
            public void onSuccess(SeekBean result) {
                onRequestNetSuccess(result.list);
            }
        });
    }

    @Override
    public Observable<BaseArrBean<SeekBean.ListDTO>> getNetObservable(int page) {
        return null;
    }

    @Override
    public SeekAdapter getAdapter() {
        RecyclerView recyclerView = mBinding.smartRecyclerView.getRecyclerView();
        SeekItemDecoration itemDecoration = new SeekItemDecoration(5);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return new SeekAdapter(BR.model);
    }

    @Override
    protected void processDefaultMethodView() {
        mAdapter.addItemClickListener((data, holder, pos) -> JumpUtils.jumpWeb(true, data.link, ""));

        // 监听RecyclerView滚动 正在输入状态，滑动列表键盘不会收起 这里处理
        mBinding.smartRecyclerView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    // 正在拖拽
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        // 滑动停止
                    case RecyclerView.SCROLL_STATE_IDLE:
                        // 惯性滑动中
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        FragmentActivity activity = getActivity();
                        if (activity instanceof SeekInputActivity) {
                            SeekInputActivity inputActivity = (SeekInputActivity) activity;
                            // 搜索框editText
                            EditText editText = inputActivity.mBinding.vSeek.getEditText();

                            // 隐藏光标
                            editText.setCursorVisible(false);
                            // 隐藏软键盘
                            KeyboardUtils.hide(editText);
                        }
                        break;
                }
            }
        });

    }

    @Override
    public Class<SeekValueViewModel> onBindViewModel() {
        return SeekValueViewModel.class;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            // 当前搜索Fragment显示 更新搜索内容
            if (mAdapter != null) {
                mAdapter.removeAll();
            }
            mCurrentPage = 0;
            requestNetworkData(mCurrentPage);
        }
    }
}
