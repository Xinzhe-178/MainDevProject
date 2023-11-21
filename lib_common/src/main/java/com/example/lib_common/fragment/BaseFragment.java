package com.example.lib_common.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.lib_common.R;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.databinding.FragmentBaseLayoutBinding;
import com.example.lib_common.view.stateplaceholderview.IStatePlaceholderView;
import com.example.lib_common.view.stateplaceholderview.StatePlaceType;
import com.example.lib_common.view.stateplaceholderview.StatePlaceView;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 王鑫哲 on 2021/11/2 下午 02:14
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseFragment<VDB extends ViewDataBinding> extends Fragment implements IStatePlaceholderView {
    /**
     * 全局实例
     */
    public FragmentActivity mContext;
    /**
     * 视图绑定
     */
    public VDB mBinding;

    private OnBindingClickCall onInitListener;

    private StatePlaceView mStatePlaceView;

    private FragmentBaseLayoutBinding mGroupBinding;

    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 获取全局实例 在onViewCreated方法取上下文会报空
        mContext = getActivity();

        mBinding = inflate(getLayoutId());

        mGroupBinding = inflate(R.layout.fragment_base_layout);
        mGroupBinding.flPage.addView(mBinding.getRoot());

        // 初始化占位View
        mStatePlaceView = StatePlaceView.register(mGroupBinding.flPreload, this);

        initMvvm();
        initView();
        initData();
        initListener();
        if (onInitListener != null) {
            onInitListener.clickCall();
        }
        return mGroupBinding.getRoot();
    }

    /**
     * 初始化MVVM 只用与绑定MVVM基类
     */
    protected void initMvvm() {

    }

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 初始化监听
     */
    protected void initListener() {

    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 获取LayoutId
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 快捷转换方法
     * layout => ViewDataBinding
     * 子类可直使用
     *
     * @param layoutId
     * @param <T>
     * @return
     */
    protected <T extends ViewDataBinding> T inflate(int layoutId) {
        return DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, null, false);
    }

    public void setOnInitListener(OnBindingClickCall call) {
        this.onInitListener = call;
    }

    @Override
    public void setPlaceholderState(StatePlaceType type) {
        if (mStatePlaceView != null) {
            mStatePlaceView.setPlaceholderState(type);
        }
    }

    @Override
    public View getErrorView() {
        return inflate(R.layout.activity_place_view_error_layout).getRoot();
    }

    @Override
    public View getEmptyView() {
        return inflate(R.layout.activity_place_view_empty_layout).getRoot();
    }

    @Override
    public View getNullView() {
        return inflate(R.layout.activity_place_view_null_layout).getRoot();
    }

    @Override
    public View getNoNetWorkView() {
        return inflate(R.layout.activity_place_view_nonetwork_layout).getRoot();
    }

    @Override
    public void onRetryClickListener() {

    }

    /**
     * 预加载布局，默认为null 如果需要展示预加载，则重写该方法
     * base类中的该方法必须为null 否则所有页面都会展示预加载页面
     *
     * @return
     */
    @Override
    public View getPreloadView() {
        return null;
    }
}
