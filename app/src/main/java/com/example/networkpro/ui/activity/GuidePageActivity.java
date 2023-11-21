package com.example.networkpro.ui.activity;

import android.view.View;

import com.example.lib_common.activity.BaseActivity;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.manage.UserManage;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_common.utils.JumpUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityGuidePageBinding;
import com.example.networkpro.databinding.GuidePageViewBinding;
import com.example.networkpro.ui.adapter.ViewPageAdapter;

import java.util.ArrayList;

/**
 * Created by 王鑫哲 on 2021/11/22 下午 07:09
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class GuidePageActivity extends BaseActivity<ActivityGuidePageBinding> {

    private ArrayList<View> mViews;

    private ViewPageAdapter mViewPageAdapter;

    /**
     * 页面总个数
     */
    private final int pageCount = 3;

    @Override
    protected void initView() {
        mBinding.setActivity(this);
        initViewPage();
        mBinding.setIndicatorViewIsShow(true);
    }

    @Override
    protected boolean setWindowsIsImmerse() {
        return true;
    }

    private void initViewPage() {
        mViews = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            GuidePageViewBinding binding = inflate(R.layout.guide_page_view);
            mViews.add(binding.getRoot());
            binding.setShowText("我是第" + i + "页--------- ViewPage");
            binding.setGoIsShow(i == pageCount - 1);

            binding.setActivity(this);
        }
        mViewPageAdapter = new ViewPageAdapter(mViews);
        mBinding.viewPage.setAdapter(mViewPageAdapter);

        // 初始化指示器
        mBinding.IndicatorView.setIndexNum(pageCount).build();
    }

    /**
     * ViewPage滑动监听
     */
    public OnBindingClickParamsCall<Integer> onPageSelected = position -> {
        UserManage.setIsLoadGuidePage(true);
        mBinding.IndicatorView.setIndicatorColor(position);
        //当滑动到最后一页时 隐藏指示器
        mBinding.setIndicatorViewIsShow(position != pageCount - 1);
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_guide_page;
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    /**
     * 跳转登录页面
     */
    public OnBindingClickCall jumpLoginPage = () -> {
        JumpUtils.jump(LoginActivity.class);
        finish();
    };
}
