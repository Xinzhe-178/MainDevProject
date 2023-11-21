package com.example.networkpro.viewmodel;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.lib_common.Interfac.OnPageChangeListener;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.lib_utils.ViewPageUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivityLoginBinding;
import com.example.networkpro.databinding.UserPassLoginLayoutBinding;
import com.example.networkpro.databinding.UserRegisterLayoutBinding;
import com.example.networkpro.ui.adapter.ViewPageAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/1/5 下午 06:45
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class LoginViewModel extends BaseViewModel {

    protected UserPassLoginLayoutBinding mPageViewOne;

    protected UserRegisterLayoutBinding mPageViewTwo;

    protected ActivityLoginBinding mBinding;

    protected List<View> mViewList;

    public LoginViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    /**
     * 初始化ViewPage
     *
     * @param binding
     */
    public void init(ActivityLoginBinding binding) {
        mBinding = binding;
        initViewPage();

        //初始化上方默认提示文字 (登录&注册)
        mBinding.setTopHintImageView(R.drawable.ic_login_hint_login);

        initListener();
    }

    private void initViewPage() {
        mPageViewOne = inflate(R.layout.user_pass_login_layout);
        mPageViewTwo = inflate(R.layout.user_register_layout);

        if (mViewList != null) mViewList.clear();
        mViewList = new ArrayList<>();
        mViewList.add(mPageViewOne.getRoot());
        mViewList.add(mPageViewTwo.getRoot());

        ViewPageAdapter pageAdapter = new ViewPageAdapter(mViewList);
        mBinding.viewPager.setAdapter(pageAdapter);
        mBinding.viewPager.setPageTransformer(true, new com.example.networkpro.ui.view.viewpage.special.ViewPageTransformer());

        //设置ViewPage动画时长 未进行设置会导致setCurrentItem 切换时效果不好(太快)
        ViewPageUtils.setAnimDuration(mBinding.viewPager, 235);

        //这里设置提示图片大小是因为这两张图片是截图来的 大小不太一样 需要重新校对下 后期再优化吧
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mBinding.ivTopHint.getLayoutParams();
        layoutParams.width = DensityUtils.dp(70);
        layoutParams.height = DensityUtils.dp(35);
        mBinding.ivTopHint.setLayoutParams(layoutParams);
    }

    protected void initListener() {
        mBinding.viewPager.addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                boolean showTypeFlag = position == 0;
                mBinding.setTopHintImageView(showTypeFlag ? R.drawable.ic_login_hint_login : R.drawable.ic_login_hint_register);

                ////这里设置提示图片大小是因为这两张图片是截图来的 大小不太一样 需要重新校对下 后期再优化吧
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mBinding.ivTopHint.getLayoutParams();
                layoutParams.width = DensityUtils.dp(showTypeFlag ? 70 : 75);
                layoutParams.height = DensityUtils.dp(showTypeFlag ? 35 : 40);
                mBinding.ivTopHint.setLayoutParams(layoutParams);
            }
        });
    }

    /**
     * 小[立即登录]点击
     */
    public OnBindingClickCall onLoginTvRegister = () -> selectPage(1);

    /**
     * 小[立即注册]点击
     */
    public OnBindingClickCall onRegisterTvLogin = () -> selectPage(0);

    /**
     * 切换page方法抽取
     *
     * @param pageIndex page的下标
     */
    protected void selectPage(int pageIndex) {
        mBinding.viewPager.setCurrentItem(pageIndex, true);
    }

    /**
     * 判断是否满足登录条件
     *
     * @return
     */
    protected boolean isSatisfyLoginCondition() {
        if (TextUtils.isEmpty(mPageViewOne.edUser)) {
            ToastUtils.show("用户名不能为空哦!");
            return false;
        }
        if (TextUtils.isEmpty(mPageViewOne.edPwd)) {
            ToastUtils.show("密码不能为空哦!");
            return false;
        }
        if (TextUtils.getTextLength(mPageViewOne.edPwd) < 6) {
            ToastUtils.show("密码不能少于6位哦!");
            return false;
        }
        return true;
    }

    /**
     * 判断是否满足注册条件
     *
     * @return
     */
    protected boolean isSatisfyRegisterCondition() {
        if (TextUtils.isEmpty(mPageViewTwo.edUser)) {
            ToastUtils.show("用户名不能为空哦！");
            return false;
        }
        if (TextUtils.isEmpty(mPageViewTwo.edPwd)) {
            ToastUtils.show("密码不能为空哦！");
            return false;
        }
        if (TextUtils.getTextLength(mPageViewTwo.edPwd) < 6) {
            ToastUtils.show("密码不能少于6位哦!");
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        int currentItem = mBinding.viewPager.getCurrentItem();
        if (currentItem == 0) {
            super.onBackPressed();
        } else {
            selectPage(currentItem - 1);
        }
    }
}
