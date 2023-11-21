package com.example.networkpro.ui.fragment;

import android.view.ViewGroup;

import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_common.fragment.BaseMvvmRecyclerFragment;
import com.example.lib_common.manage.WanAndroidApiManager;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_common.view.CusRecyclerView;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.Res;
import com.example.networkpro.R;
import com.example.networkpro.ui.activity.FavoriteActivity;
import com.example.networkpro.ui.activity.MainActivity;
import com.example.networkpro.ui.adapter.RecyclerAdapter;
import com.example.networkpro.ui.view.AphorismsView;
import com.jeremyliao.liveeventbus.LiveEventBus;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2021/11/13 上午 09:36
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class RecyclerFragment extends BaseMvvmRecyclerFragment<BaseViewModel, RecyclerBean, RecyclerAdapter> {

    private AphorismsView headerView;

    @Override
    public Observable<BaseArrBean<RecyclerBean>> getNetObservable(int page) {
        return WanAndroidApiManager.getInstance().getHomeDataObservable(page);
    }

    @Override
    protected void initListener() {
        /**
         * 子类重写此方法 必须要调用super() 因为在父类类有列表的监听事件
         */
        super.initListener();
        headerView.getBinding().ivUserLogo.setOnClickListener(view -> {
            FragmentActivity activity = getActivity();
            if (activity != null && activity instanceof MainActivity) {
                MainActivity mainActivity = (MainActivity) activity;
                // 自动将Tab切换到[我的]
                mainActivity.mBinding.navView.setSelectTab(2);
            }
        });

        // 进入收藏页面
        headerView.getBinding().ivCollect.setOnClickListener(view -> JumpUtils.jump(FavoriteActivity.class));
    }

    @Override
    public RecyclerAdapter getAdapter() {
        return new RecyclerAdapter(BR.model, Const.CommonWebViewPageConst.CUS_VIEW_DATA_HOME_LIST);
    }

    @Override
    protected void processDefaultMethodView() {

        // 设置顶部高度，由于顶部设置了沉浸式，整体View会顶上去，所以自己写一个状态栏
        ViewGroup.LayoutParams layoutParams = mBinding.vStatusBar.getLayoutParams();
        layoutParams.height = DensityUtils.getStatusHeight();
        mBinding.vStatusBar.setLayoutParams(layoutParams);
        mBinding.vStatusBar.setBackground(Res.Drawable(R.color.default_status_bar_color));

        // 点击[广场]Tab 如果在顶部就刷新列表 不在顶部就滚动到顶部
        LiveEventBus.get(EventPath.HOME_FRAGMENT_RETURN_TOP).observe(this, o -> {
            if (o instanceof Integer) {
                int pos = Integer.parseInt(o.toString());
                // pos==1 为点击[广场]Tab
                if (pos == 1) {
                    CusRecyclerView recyclerView = mBinding.smartRecyclerView.getRecyclerView();
                    if (recyclerView != null && recyclerView.getLayoutManager() != null) {
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            int lastPos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                            if (lastPos == 0) {
                                // 显示封装的dialog
                                LoadingDialogController.getInstance().showDialog();
                                defRefresh();
                            } else {
                                layoutManager.scrollToPosition(0);
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public ViewDataBinding getHeaderView() {
        headerView = AphorismsView.init(getActivity(), inflate(R.layout.aphorisms_layout));
        return headerView.getBinding();
    }

    @Override
    public boolean getPresTrainIsOpen() {
        return true;
    }

    @Override
    public Class<BaseViewModel> onBindViewModel() {
        return BaseViewModel.class;
    }

    @Override
    public void onRecyclerScrollListener(Integer dy) {

    }

    @Override
    protected void downRefresh() {
        if (headerView != null) {
            headerView.refreshData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (headerView != null) {
            headerView.onDestroy();
        }
    }
}
