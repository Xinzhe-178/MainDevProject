package com.example.networkpro.ui.fragment;

import android.annotation.SuppressLint;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.fragment.BaseFragment;
import com.example.lib_utils.KeyboardUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.FragmentSeekHistoryLayoutBinding;
import com.example.networkpro.ui.activity.SeekInputActivity;
import com.example.networkpro.ui.view.SeekHistoryView;
import com.example.networkpro.viewmodel.SeekInputViewModel;

/**
 * Created by 王鑫哲 on 2022/9/22 20:27
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekHistoryFragment extends BaseFragment<FragmentSeekHistoryLayoutBinding> {

    private OnBindingClickCall onHideKeyboardCall;

    public void setOnHideKeyboardCall(OnBindingClickCall onHideKeyboardCall) {
        this.onHideKeyboardCall = onHideKeyboardCall;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_seek_history_layout;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        // 触摸搜索历史总模块时 隐藏软键盘
        mBinding.nsvView.setOnTouchListener((view, motionEvent) -> {
            if (onHideKeyboardCall != null) {
                onHideKeyboardCall.clickCall();
            }
            return false;
        });
    }

    @Override
    protected void initListener() {
        FragmentActivity fragmentActivity = getActivity();
        if (fragmentActivity instanceof SeekInputActivity) {
            SeekInputActivity activity = (SeekInputActivity) fragmentActivity;

            // 为编辑模式时 搜索框隐藏光标 隐藏软键盘(进入搜索页面 会自动获取焦点 弹出软键盘 此时长按进入编辑模式 软键盘还在显示)
            mBinding.vSeekHistory.setOnEditModeListener(isEditMode -> {
                EditText editText = activity.mBinding.vSeek.getEditText();
                // 点击软键盘搜索时，隐藏光标
                editText.setCursorVisible(false);
                KeyboardUtils.hide(editText);
            });

            // 对该activity返回键进行监听 在搜索结果页/联想页 返回会回到搜索历史页 若在搜索历史页，又是在编辑模式 则退出编辑模式
            activity.setOnBackListener(() -> {
                SeekInputViewModel viewModel = activity.getViewModel();
                if (viewModel != null) {
                    switch (viewModel.showFragmentType) {
                        case Const.SeekInputShowType.SEEK_HISTORY:
                            SeekHistoryView vSeekHistory = mBinding.vSeekHistory;
                            if (vSeekHistory.isEditMode()) {
                                // 在编辑模式则退出编辑模式
                                vSeekHistory.saveNewHisItemData();
                            } else {
                                activity.finish();
                            }
                            break;
                        case Const.SeekInputShowType.SEEK_LENOVO:
                        case Const.SeekInputShowType.SEEK_VALUE:
                            // 返回搜索历史页面 只需要将EditText内容清空即可 那边做了内容监听 为空会自动展示历史页
                            EditText editText = activity.mBinding.vSeek.getEditText();
                            editText.setText("");
                            break;
                    }
                }
            });
        }
    }
}
