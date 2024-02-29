package com.example.networkpro.ui.fragment;

import androidx.fragment.app.FragmentManager;

import com.example.lib_common.consts.Const;
import com.example.lib_common.fragment.BaseFragment;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_network.callback.Urls;
import com.example.networkpro.R;
import com.example.networkpro.call.IFragmentSwitchType;
import com.example.networkpro.databinding.FragmentSeekValuePictureLayoutBinding;
import com.example.networkpro.viewmodel.SeekInputViewModel;

/**
 * Created by 王鑫哲 on 2022/9/8 21:25
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekValueFragment extends BaseFragment<FragmentSeekValuePictureLayoutBinding> implements IFragmentSwitchType {

    private final SeekInputViewModel mViewModel;

    private FragmentManager mChildFragmentManager;

    public SeekValueFragment(SeekInputViewModel viewModel) {
        super();
        mViewModel = viewModel;
    }

    @Override
    protected void initView() {
        mChildFragmentManager = getChildFragmentManager();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seek_value_picture_layout;
    }

    @Override
    public void setValueFragmentState(String type) {
        BaseFragment fragment = null;
        switch (type) {
            case Const.SeekValueShowType.SEEK_VALUE_ANDROID:
                fragment = new SeekValueAndroidFragment(mViewModel);
                break;
            case Const.SeekValueShowType.SEEK_HISTORY_PICTURE:
                fragment = new SeekValuePictureFragment(mViewModel);
                break;
            case Const.SeekValueShowType.SEEK_LENOVO_WIKIPEDIA:
                String loadUrl = Urls.BAI_DU_BAI_KE_URl.concat(mViewModel.SEEK_KEY);
                JumpUtils.jumpWeb(true, loadUrl, "");
                mViewModel.setValueFragmentState(Const.SeekInputShowType.SEEK_HISTORY);
                break;
        }
        if (fragment != null) {
            mChildFragmentManager.beginTransaction().replace(R.id.fl_group, fragment).commit();
        }
    }
}