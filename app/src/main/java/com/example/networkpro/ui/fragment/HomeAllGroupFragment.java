package com.example.networkpro.ui.fragment;

import android.widget.TextView;

import com.example.lib_common.controller.LoadingDialogController;
import com.example.lib_common.fragment.BaseMvvmFragment;
import com.example.lib_utils.ClipboardUtils;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.HomeAllGroupFragmentLayoutBinding;
import com.example.networkpro.viewmodel.HomeAllGroupViewModel;

/**
 * Created by 王鑫哲 on 2023/7/4 3:40 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeAllGroupFragment extends BaseMvvmFragment<HomeAllGroupViewModel, HomeAllGroupFragmentLayoutBinding> {
    @Override
    protected void initView() {
        mBinding.setFragment(this);
        requestData();
    }

    private void requestData() {
        mViewModel.getRandomJokeTextData();
        mViewModel.getRandomIanTextData();
        mViewModel.getRandomLoveTextData();
    }

    @Override
    protected void initListener() {
        // 随机一句笑话请求回调
        mViewModel.getRandomJokeTextData.observe(this, s -> mBinding.tvJoke.setText(s));

        // 随机一句名言请求回调
        mViewModel.getRandomIanTextData.observe(this, s -> mBinding.tvIan.setText(s));

        // 随机一句情话请求回调
        mViewModel.getRandomLoveTextData.observe(this, s -> mBinding.tvLove.setText(s));

        GlideUtils.setRoundImageUrl(mBinding.ivWeather, "https://api.vvhan.com/api/ip", 10f);

        mBinding.tvJoke.setOnClickListener(v -> copyText(mBinding.tvJoke));
        mBinding.tvIan.setOnClickListener(v -> copyText(mBinding.tvIan));
        mBinding.tvLove.setOnClickListener(v -> copyText(mBinding.tvLove));
    }

    private void copyText(TextView textView) {
        if (textView == null) {
            return;
        }
        ClipboardUtils.copy(TextUtils.getText(textView));
        ToastUtils.show("已复制到剪切板");
    }

    @Override
    public int getLayoutId() {
        return R.layout.home_all_group_fragment_layout;
    }

    @Override
    public Class<HomeAllGroupViewModel> onBindViewModel() {
        return HomeAllGroupViewModel.class;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mViewModel != null) {
            requestData();
        } else {
            LoadingDialogController.getInstance().dismissDialog();
        }
    }
}
