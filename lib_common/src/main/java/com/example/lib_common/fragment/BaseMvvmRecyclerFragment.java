package com.example.lib_common.fragment;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_bean.BaseArrBean;
import com.example.lib_common.R;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_common.databinding.ActivityRecyclerLayoutBinding;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.view.RecyclerViewModel;
import com.example.lib_common.view.stateplaceholderview.StatePlaceType;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_network.callback.NetworkStatusCall;
import com.example.lib_network.callback.Request;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.ToastUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 王鑫哲 on 2021/11/2 下午 04:41
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseMvvmRecyclerFragment<VM extends BaseViewModel, T, BAD extends BaseEasyAdapter> extends BaseMvvmFragment<VM, ActivityRecyclerLayoutBinding> {
    /**
     * 默认Adapter
     */
    public BAD mAdapter;
    /**
     * 默认从第0页开始加载
     */
    protected int mInitPage = 0; //max = 619
    /**
     * 当前加载的页码
     */
    protected int mCurrentPage = mInitPage;
    /**
     * SmartRefreshLayout上拉加载/下拉刷新停留时长
     */
    private final int mSmartRefLoadTime = 5000;
    /**
     * 是否可以加载
     * 默认可以 只有在最后一页后不可以继续加载
     * 下拉刷新则回复可加载状态
     */
    private boolean mEnableLoadMore;

    @Override
    protected void initView() {
        // 初始化默认列表可上拉加载更多
        setEnableLoadMore(true);
        // 获取Adapter
        mAdapter = getAdapter();
        // 初始化列表Header and Footer
        initRecyclerViewHeaderAndFooter();
        // 设置RecyclerView布局管理器 默认LinearLayoutManager 如需改变 则重写getLayoutManager();
        mBinding.smartRecyclerView.setLayoutManager(getLayoutManager());
        // 设置RecyclerView Adapter
        mBinding.smartRecyclerView.setAdapter(mAdapter);
        // 初始化自定义界面内容
        processDefaultMethodView();
        // 初始化请求数据
        requestNetworkData(mCurrentPage);
        // 初始化SmartRefreshLayout 实现下拉刷新&上拉加载
        setSmartRefreshLayout();
    }

    /**
     * 初始化列表Header and Footer
     */
    private void initRecyclerViewHeaderAndFooter() {
        if (mAdapter instanceof BaseAdapter) {
            BaseAdapter adapter = (BaseAdapter) mAdapter;
            RecyclerViewModel model = new RecyclerViewModel();
            ViewDataBinding headerView = getHeaderView();
            if (headerView != null) {
                model.setHaveHeader(headerView.getRoot());
            }
            ViewDataBinding footerView = getFooterView();
            if (footerView != null) {
                model.setHaveFooter(footerView.getRoot());
            }
            adapter.setRecyclerViewModel(model);
        }
    }

    /**
     * SmartRefreshLayout逻辑处理
     */
    protected void setSmartRefreshLayout() {
        mBinding.smartRecyclerView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull @NotNull RefreshLayout refreshLayout) {
                defLordMore();
            }

            @Override
            public void onRefresh(@NonNull @NotNull RefreshLayout refreshLayout) {
                defRefresh();
            }
        });

        /**
         * 预加载开启/关闭
         */
        if (getPresTrainIsOpen()) {
            mBinding.smartRecyclerView.setPresTrainCallBack((data) -> defLordMore());
        }
    }

    protected void defRefresh() {
        downRefresh();
        // 下拉刷新逻辑
        mCurrentPage = mInitPage;
        mAdapter.removeAll();
        requestNetworkData(mCurrentPage);
        // 关闭刷新状态
        mBinding.smartRecyclerView.finishRefresh(mSmartRefLoadTime);

        // 之前已经到最后一页了 刷新后就要设置为可加载
        if (!mEnableLoadMore) {
            setEnableLoadMore(true);
        }
    }

    @Override
    protected void initListener() {
        // 监听RecyclerView滚动 滚动就会调用onRecyclerScrollListener();  如有需要 重写onRecyclerScrollListener()即可
        mBinding.smartRecyclerView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                onRecyclerScrollListener(dy);
            }
        });
    }

    /**
     * RecyclerView滑动时会调用此方法
     */
    protected void onRecyclerScrollListener(Integer dy) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recycler_layout;
    }

    /**
     * 请求网络数据
     * 如果接口返回报文与封装预设不符 则重写该方法 手动实现数据加载逻辑
     * 需要注意 成功拿到数据后最终是要调 onRequestNetSuccess() 别忘记哦
     *
     * @param page 页码
     */
    public void requestNetworkData(int page) {
        Observable<BaseArrBean<T>> observable = getNetObservable(mCurrentPage);
        Request.net(observable, new DefNetCallBack<BaseArrBean<T>>() {
            @Override
            public void onSuccess(BaseArrBean<T> result) {
                BaseArrBean.DataDTO<T> data = result.data;

                if (data == null || data.datas == null || data.datas.size() == 0) {
                    // 在获取的数据为空的情况下判断 只在第一页显示数据为空
                    if (mCurrentPage == mInitPage) {
                        setPlaceholderState(StatePlaceType.Empty);
                    } else {
                        ToastUtils.show("最后一页啦~");
                        // 停止刷新和加载 并禁止上拉加载更多
                        hideRefreshLoad();
                    }
                    return;
                }
                // 设置数据展示
                onRequestNetSuccess(data.datas);
                LogUtils.PrintE("requestNetworkData--onSuccess-->", data.toString());
            }
        }, defNetworkStatusCall);
    }

    /**
     * 停止刷新和加载 并禁止上拉加载更多
     */
    protected void hideRefreshLoad() {
        mBinding.smartRecyclerView.finishRefresh();
        mBinding.smartRecyclerView.finishLoadMore();
        setEnableLoadMore(false);
    }

    /**
     * 成功拿到数据后的操作 设置给适配器
     * 当前是第一页的话 清空数据再设置数据
     * 否则是追加数据
     */
    protected void onRequestNetSuccess(List<T> result) {
        if (mAdapter != null) {
            if (mCurrentPage == mInitPage) {
                mAdapter.update(result);
            } else {
                mAdapter.addAll(result);
            }
            mBinding.smartRecyclerView.finishRefresh();
            mBinding.smartRecyclerView.finishLoadMore();
        }
        setPlaceholderState(StatePlaceType.Success);
    }

    /**
     * 请求数据
     */
    public abstract Observable<BaseArrBean<T>> getNetObservable(int page);

    /**
     * 设置适配器
     *
     * @return extends BaseAdapter
     */
    public abstract BAD getAdapter();

    /**
     * 设置RecyclerView Header
     *
     * @return HeaderView
     */
    public ViewDataBinding getHeaderView() {
        return null;
    }

    /**
     * 设置RecyclerFooter
     *
     * @return FooterView
     */
    public ViewDataBinding getFooterView() {
        return null;
    }

    /**
     * 设置布局管理器
     * Example: new LinearLayoutManager(mActivity)
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(mContext);
    }

    /**
     * 初始化页面内容
     */
    protected abstract void processDefaultMethodView();

    /**
     * 是否开启预加载
     * 默认所有的封装列表不开启
     *
     * @return
     */
    public boolean getPresTrainIsOpen() {
        return false;
    }

    /**
     * 默认列表数据加载逻辑
     */
    protected void defLordMore() {
        if (mEnableLoadMore) {
            mCurrentPage++;
            requestNetworkData(mCurrentPage);
            //关闭加载状态
            mBinding.smartRecyclerView.finishLoadMore(mSmartRefLoadTime);
        } else {
            ToastUtils.show("不可加载");
        }
    }

    /**
     * 设置是否开启上拉加载
     *
     * @param enableLoadMore 是否可以加载
     */
    protected void setEnableLoadMore(boolean enableLoadMore) {
        mEnableLoadMore = enableLoadMore;
        mBinding.smartRecyclerView.setEnableLoadMore(enableLoadMore);
    }

    /**
     * 下拉刷新将会调用此方法
     * 可在此处理额外的刷新逻辑
     */
    protected void downRefresh() {

    }

    /**
     * 默认列表请求-无网络回调
     */
    protected NetworkStatusCall defNetworkStatusCall = () -> {
        ToastUtils.show("网络连接失败，请检查网路是否连接");
        setPlaceholderState(StatePlaceType.NoNetWork);
    };

    @Override
    public void onRetryClickListener() {
        super.onRetryClickListener();
        // 点击封装的重试 走下拉刷新逻辑
        LoadingDialogController.getInstance().showDialog();
        defRefresh();
    }

    public abstract class DefNetCallBack<D> implements NetCallBack<D> {
        @Override
        public void onError(String errorMsg) {
            ToastUtils.show(errorMsg);
            // 加载失败后 展示失败页 不是特别合理，所以 这里只在当前没有数据情况下展示错误页
            if (mAdapter == null || mAdapter.getDataBean() == null || mAdapter.getDataBean().size() == 0) {
                setPlaceholderState(StatePlaceType.Error);
            }
        }
    }
}
