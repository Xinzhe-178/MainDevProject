package com.example.networkpro.viewmodel;

import android.app.Application;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_common.topbar.TopBarView;
import com.example.lib_utils.FixedSpeedScroller;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.Res;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityShopParticularsBinding;
import com.example.networkpro.ui.adapter.ViewPageAdapter;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 王鑫哲 on 2022/3/9 下午 03:20
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ShopParticularsViewModel extends BaseViewModel {

    /**
     * 滑动 标题渐变的高度范围
     */
    private final float slideGradientDistance = DensityUtils.dp(280);

    /**
     * RxJava 计时器
     */
    private Disposable mTimeDis;

    private ActivityShopParticularsBinding mBinding;

    /**
     * 是否自动轮播
     */
    private static final boolean IS_AUTOMATIC_CAROUSEL = false;

    public ShopParticularsViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    /**
     * 初始化ViewPage
     */
    public void initViewPage(ViewPager viewPager, List<View> viewList, OnBindingClickParamsCall<Integer> call) {
        if (viewPager != null && viewList != null && viewList.size() > 0) {
            ViewPageAdapter adapter = new ViewPageAdapter(viewList);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(0);

            //设置ViewPage动画时长 未进行设置会导致setCurrentItem 切换时效果不好(太快)
            try {
                Field mField = viewPager.getClass().getDeclaredField("mScroller");
                mField.setAccessible(true);
                FixedSpeedScroller s = new FixedSpeedScroller(viewPager.getContext(), new AccelerateInterpolator());
                mField.set(viewPager, s);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (IS_AUTOMATIC_CAROUSEL) {
                // RxJava实现计时器 切换
                mTimeDis = Observable.interval(3000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aLong -> {
                            if (viewPager.getCurrentItem() >= 0 && viewPager.getCurrentItem() != viewList.size() - 1) {
                                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                            } else if (viewPager.getCurrentItem() == viewList.size() - 1) {
                                viewPager.setCurrentItem(0);
                            }
                            if (call != null) call.clickCall(viewPager.getCurrentItem());
                        });
            }
        }
    }

    /**
     * ViewPage滑动监听
     */
    public OnBindingClickParamsCall<Integer> onPageSelected = position -> mBinding.setCurrentIndex(position + 1);

    /**
     * NestedScrollView 滑动监听
     */
    public OnBindingClickParamsCall<Integer> onScrollChange = scrollY -> {
        TopBarView topBar = mBinding.viewTopBar;
        ImageView backView = topBar.getLeftBackView();

        if (scrollY < slideGradientDistance) {
            if (scrollY == 0) {
                topBar.setTitleColor(Res.color(R.color.transparent));
                topBar.setBackgroundColor(Res.color(R.color.transparent));
                backView.getDrawable().setTint(Res.color(R.color.white));
            } else {
                int alpha = (int) (scrollY * 255 / slideGradientDistance);
                topBar.setTitleColor(Color.argb(alpha, 0, 0, 0));
                topBar.setBackgroundColor(Color.argb(alpha, 255, 255, 255));
                backView.getDrawable().setTint(Color.argb(alpha, 0, 0, 0));

//                if (scrollY <= slideGradientDistance / 2) {
//                    int alpha1 = (int) (scrollY * 255 / (slideGradientDistance / 2));
////                    backView.getDrawable().setTint(Color.argb(alpha1, 10, 10, 10));
//                    backView.getDrawable().setTint(Color.argb(slideGradientDistance / 2 - alpha1, 255, 255, 255));
////                    backView.getDrawable().setTint(Color.argb(alpha1, 255, 255, 255));
//                } else {
//                    int alpha2 = (int) (scrollY * 255 / (slideGradientDistance / 2));
//                    backView.getDrawable().setTint(Color.argb(alpha2, 0, 0, 0));
//                }
            }
        } else {
            topBar.setBackgroundColor(Res.color(R.color.white));
            backView.getDrawable().setTint(Res.color(R.color.black));
        }
    };

    @Override
    public void onDestroy() {
        if (mTimeDis != null && !mTimeDis.isDisposed()) {
            mTimeDis.dispose();
            mTimeDis = null;
        }
    }

    public void initBinding(ActivityShopParticularsBinding binding) {
        mBinding = binding;
    }
}
