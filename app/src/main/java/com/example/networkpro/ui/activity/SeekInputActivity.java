package com.example.networkpro.ui.activity;

import com.example.lib_bean.bean.SeekLenovoBean;
import com.example.lib_common.activity.BaseMvvmActivity;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.topbar.TopBarIsShow;
import com.example.lib_utils.KeyboardUtils;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ActivitySeekInputLayoutBinding;
import com.example.networkpro.ui.view.CusSeekView;
import com.example.networkpro.viewmodel.SeekInputViewModel;

/**
 * Created by 王鑫哲 on 2022/9/1 21:49
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekInputActivity extends BaseMvvmActivity<ActivitySeekInputLayoutBinding, SeekInputViewModel> {

    /**
     * 由SeekLenovoFragment 对其监听，监听输入框内容变化
     */
    private OnBindingClickParamsCall<SeekLenovoBean> onSeekLenovoListener;

    public void setOnSeekLenovoListener(OnBindingClickParamsCall<SeekLenovoBean> onSeekLenovoListener) {
        this.onSeekLenovoListener = onSeekLenovoListener;
    }

    @Override
    protected void initView() {
        mBinding.setActivity(this);
        mBinding.vSeek.setType(CusSeekView.TYPE_INPUT).setMaxInputLength(15).build();
        mViewModel.initValueFragment(getSupportFragmentManager());
        mViewModel.setValueFragmentState(Const.SeekInputShowType.SEEK_HISTORY);
        mViewModel.initHistoryViewListener(mBinding, () -> KeyboardUtils.hide(mBinding.vSeek.getEditText()));
        // 进入页面弹出软键盘
        KeyboardUtils.show(mBinding.vSeek.getEditText());
        mBinding.vSeek.addTextChangedListener(s -> {
            // 别的地方有隐藏光标行为，避免出现bug 输入框内容发生变化即为正在输入场景，显示光标
            mBinding.vSeek.getEditText().setCursorVisible(true);

            if (onSeekLenovoListener != null) {
                onSeekLenovoListener.clickCall(new SeekLenovoBean(s));
            }
            mViewModel.setValueFragmentState(TextUtils.isEmpty(s) ? Const.SeekInputShowType.SEEK_HISTORY : Const.SeekInputShowType.SEEK_LENOVO);
        });
    }

    @Override
    public TopBarIsShow getDefTopBar() {
        return TopBarIsShow.NO_TOP_BAR;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_seek_input_layout;
    }

    @Override
    public Class<SeekInputViewModel> onBindViewModel() {
        return SeekInputViewModel.class;
    }

    public OnBindingClickCall onCancelClick = () -> {
        if (mOnBackListener != null) {
            mOnBackListener.clickCall();
        } else {
            finish();
        }
    };
}
