package com.example.networkpro.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.fastjson.JSON;
import com.example.lib_bean.bean.AphorismsBean;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_network.callback.ApiService;
import com.example.lib_network.callback.Net;
import com.example.lib_network.callback.NetCallBack;
import com.example.lib_network.callback.Urls;
import com.example.lib_utils.ClipboardUtils;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.lib_utils.ViewPageUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.AphorismsLayoutBinding;
import com.example.networkpro.databinding.AphorismsLayoutItemBinding;
import com.example.networkpro.ui.activity.SeekInputActivity;
import com.example.networkpro.ui.adapter.ViewPageAdapter;
import com.example.networkpro.ui.view.viewpage.special.ViewPageTransformerX;
import com.jeremyliao.liveeventbus.LiveEventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 王鑫哲 on 2022/8/6 20:19
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class AphorismsView {

    private final Context mContext;

    private final AphorismsLayoutBinding mBinding;

    private ViewPageAdapter mAdapter;

    private List<View> mViews;

    private final int itemCount = 3;

    /**
     * 是否自动轮播
     */
    private static final boolean IS_AUTOMATIC_CAROUSEL = true;

    /**
     * RxJava 计时器
     */
    private Disposable mTimeDis;

    private int errorCount;

    public static AphorismsView init(Context context, AphorismsLayoutBinding binding) {
        return new AphorismsView(context, binding);
    }

    public AphorismsLayoutBinding getBinding() {
        return mBinding;
    }

    private AphorismsView(Context context, AphorismsLayoutBinding binding) {
        mContext = context;
        mBinding = binding;
        initView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        initViewPageData();
        GlideUtils.setCircleImageUrl(mBinding.ivUserLogo, UserManage.getUserAvatar());

        mBinding.vSeek.setType(CusSeekView.TYPE_SHOW).build();

        // 点击搜索框跳转至搜索页面
        mBinding.vSeek.setOnTouchListener(() -> JumpUtils.jump(SeekInputActivity.class));

        // 更新头像后这里也要同步
        LiveEventBus.get(EventPath.UPDATE_USER_AVATAR).observe((LifecycleOwner) mContext, o -> {
            if (o != null && !TextUtils.isEmpty(o.toString())) {
                GlideUtils.setCircleImageUrl(mBinding.ivUserLogo, o.toString());
            }
        });
    }

    private void initViewPageData() {
        ViewPager viewPager = mBinding.vp;
        mViews = new ArrayList<>();

        for (int i = 0; i < itemCount; i++) {
            loadData(10);
        }

        mAdapter = new ViewPageAdapter(mViews);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setPageTransformer(true, new ViewPageTransformerX());

        //设置ViewPage动画时长 未进行设置会导致setCurrentItem 切换时效果不好(太快)
        ViewPageUtils.setAnimDuration(viewPager, 450);

        if (IS_AUTOMATIC_CAROUSEL) {
            // RxJava实现计时器 切换
            mTimeDis = Observable.interval(5000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        if (viewPager.getCurrentItem() >= 0 && viewPager.getCurrentItem() != mViews.size() - 1) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                        } else if (viewPager.getCurrentItem() == mViews.size() - 1) {
                            viewPager.setCurrentItem(0);
                        }
                    });
        }
    }

    private void loadData(int i) {
        ApiService apiService = Net.getUrlApiService(Urls.OPEN_FREE_URL);
        Net.initNetService(apiService.getAphorismsViewData(), new NetCallBack<AphorismsBean>() {
            @Override
            public void onSuccess(AphorismsBean result) {
                addViewData(result, i);
            }

            @Override
            public void onError(String errorMsg) {
                errorCount++;
                LogUtils.PrintE("----- errorCount = " + errorCount);
                if (errorCount == itemCount) {
                    mBinding.vp.setVisibility(View.GONE);
                    LogUtils.PrintE("----- errorCount = " + errorCount + " 隐藏");
                }

            }
        });
    }

    public void refreshData() {
        errorCount = 0;
        for (int i = 0; i < itemCount; i++) {
            loadData(i);
        }
    }

    private void addViewData(AphorismsBean result, int i) {
        if (result == null || result.result == null) {
            return;
        }
        // 该条件用于区分是否为刷新数据
        if (i > itemCount) {
            AphorismsLayoutItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.aphorisms_layout_item, null, false);
            binding.setModel(result.result);

            // 点击跳转到对应搜索
            binding.getRoot().setOnClickListener(v -> {
                String text = TextUtils.getText(binding.tvTitle);
                String loadUrl = Urls.BAI_DU_COMMON_SEEK_URL.concat(text);
                JumpUtils.jumpWeb(true, loadUrl, "");
                LogUtils.PrintD(getClass() + "-> loadUrl = " + loadUrl);
            });

            // 长按复制到剪切板
            binding.getRoot().setOnLongClickListener(v -> {
                String text = TextUtils.getText(binding.tvTitle) + "\t" + TextUtils.getText(binding.tvFrom);
                ClipboardUtils.copy(text);
                ToastUtils.show("复制成功");
                return true;
            });

            mViews.add(binding.getRoot());
            LogUtils.PrintE("----- if itemCount = " + itemCount + " i = " + i + " data = " + JSON.toJSONString(result.result));
        } else {
            // 刷新数据只替换View中的数据
            LogUtils.PrintE("----- else itemCount = " + itemCount + " i = " + i + " data = " + JSON.toJSONString(result.result));
            View view = mViews.get(i);
            if (view != null) {
                ViewDataBinding binding = DataBindingUtil.getBinding(view);
                if (binding instanceof AphorismsLayoutItemBinding) {
                    AphorismsLayoutItemBinding itemBinding = DataBindingUtil.getBinding(view);
                    if (itemBinding != null) {
                        itemBinding.setModel(result.result);
                    }
                }
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    public OnBindingClickParamsCall<Integer> onPageSelected = position -> {

    };

    public void onDestroy() {
        if (mTimeDis != null && !mTimeDis.isDisposed()) {
            mTimeDis.dispose();
            mTimeDis = null;
        }
    }
}
