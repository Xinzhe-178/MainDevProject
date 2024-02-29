package com.example.networkpro.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.view.MotionEvent;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.lib_bean.bean.SeekHistoryBean;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.consts.EventPath;
import com.example.lib_common.mvvm.BaseViewModel;
import com.example.lib_utils.KeyboardUtils;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.R;
import com.example.networkpro.call.IFragmentSwitchType;
import com.example.networkpro.databinding.ActivitySeekInputLayoutBinding;
import com.example.networkpro.ui.fragment.SeekHistoryFragment;
import com.example.networkpro.ui.fragment.SeekLenovoFragment;
import com.example.networkpro.ui.fragment.SeekValueFragment;
import com.jeremyliao.liveeventbus.LiveEventBus;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 王鑫哲 on 2022/9/1 21:50
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekInputViewModel extends BaseViewModel implements IFragmentSwitchType {
    public String SEEK_KEY = "";

    /**
     * 搜索结果Fragment
     */
    private SeekValueFragment mSeekValueFragment;
    /**
     * 搜索结果联想Fragment
     */
    private SeekLenovoFragment mSeekLenovoFragment;
    /**
     * 搜索结果历史Fragment
     */
    private SeekHistoryFragment mSeekHistoryFragment;

    private FragmentManager mFragmentManager;
    /**
     * 当前展示的页面(fragment)
     */
    public String showFragmentType;
    /**
     * 页面切换监听
     */
    private OnBindingClickParamsCall<String> onFragmentSwitchStateListener;

    public SeekInputViewModel(@NonNull @NotNull Application application) {
        super(application);
    }

    public SeekValueFragment getSeekValueFragment() {
        return mSeekValueFragment;
    }

    /**
     * 初始化各Fragment
     *
     * @param supportFragmentManager
     */
    public void initValueFragment(@NonNull FragmentManager supportFragmentManager) {
        mSeekValueFragment = new SeekValueFragment(this);
        mSeekLenovoFragment = new SeekLenovoFragment();
        mSeekHistoryFragment = new SeekHistoryFragment();
        mFragmentManager = supportFragmentManager;

        mFragmentManager.beginTransaction()
                .add(R.id.fl_seek_value, mSeekValueFragment)
                .add(R.id.fl_seek_value, mSeekLenovoFragment)
                .add(R.id.fl_seek_value, mSeekHistoryFragment)
                .commit();
    }

    /**
     * 初始化搜索历史View监听
     *
     * @param call    点击搜索后的逻辑处理 如隐藏软键盘，隐藏软键盘需要activity实例 参数传过来不妥
     * @param binding
     */
    @SuppressLint("ClickableViewAccessibility")
    public void initHistoryViewListener(ActivitySeekInputLayoutBinding binding, OnBindingClickCall call) {
        binding.vSeek.setOnSeekClick(s -> {
            SEEK_KEY = s;

            if (call != null) {
                call.clickCall();
            }
            setValueFragmentState(Const.SeekInputShowType.SEEK_VALUE);
            mSeekHistoryFragment.mBinding.vSeekHistory.addInputText(new SeekHistoryBean(s, false));
            // 点击软键盘搜索时，隐藏光标
            EditText editText = binding.vSeek.getEditText();
            editText.setCursorVisible(false);
            // 点击item时 将光标置于末尾 不然点击editText内容末尾时 光标会从开头挪动到末尾 会闪一下
            editText.setSelection(TextUtils.getTextLength(editText));
        });

        /**
         * 搜索item点击接收
         */
        LiveEventBus.get(EventPath.SEEK_HIS_ITEM_CLICK).observe(binding.getActivity(), o -> {
            SEEK_KEY = o.toString().trim();

            // 给搜素Ed设置搜索内容
            EditText editText = binding.vSeek.getEditText();
            editText.setText(SEEK_KEY);

            // 回调需要回传回去 因为要隐藏软键盘
            if (call != null) {
                call.clickCall();
            }
            // 这里需要设置 因为文字内容变动的话只会展示联想页面 这里需要展示结果页
            setValueFragmentState(Const.SeekInputShowType.SEEK_VALUE);
            // 需要添加条目 因为需要排序
            mSeekHistoryFragment.mBinding.vSeekHistory.addInputText(new SeekHistoryBean(SEEK_KEY, false));

            // 点击item时，隐藏光标
            editText.setCursorVisible(false);
            // 点击item时 将光标置于末尾 不然点击editText内容末尾时 光标会从开头挪动到末尾 会闪一下
            editText.setSelection(TextUtils.getTextLength(editText));
        });

        // 点击editText时，获取焦点 用户需要编辑 由于上面隐藏了光标 这里恢复显示
        binding.vSeek.getEditText().setOnTouchListener((view, motionEvent) -> {
            // 只在按下的时候走逻辑
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                LiveEventBus.get(EventPath.SEEK_HIS_SEEK_VIEW_CLICK).post("");
                binding.vSeek.getEditText().setCursorVisible(true);
            }
            return false;
        });

        // 触摸搜索历史总模块时 隐藏软键盘
        mSeekHistoryFragment.setOnHideKeyboardCall(() -> {
            KeyboardUtils.hide(binding.vSeek.getEditText());
        });
    }

    public void setFragmentSwitchStateListener(OnBindingClickParamsCall<String> listener) {
        this.onFragmentSwitchStateListener = listener;
    }

    @Override
    public void setValueFragmentState(String type) {
        if (TextUtils.isEmpty(type)) {
            return;
        }
        this.showFragmentType = type;
        switch (type) {
            case Const.SeekInputShowType.SEEK_LENOVO:
                mFragmentManager
                        .beginTransaction()
                        .hide(mSeekValueFragment)
                        .hide(mSeekHistoryFragment)
                        .show(mSeekLenovoFragment)
                        .commit();
                break;
            case Const.SeekInputShowType.SEEK_VALUE:
                mFragmentManager
                        .beginTransaction()
                        .hide(mSeekLenovoFragment)
                        .hide(mSeekHistoryFragment)
                        .show(mSeekValueFragment)
                        .commit();
                break;
            case Const.SeekInputShowType.SEEK_HISTORY:
                mFragmentManager
                        .beginTransaction()
                        .hide(mSeekValueFragment)
                        .hide(mSeekLenovoFragment)
                        .show(mSeekHistoryFragment)
                        .commit();
                break;
        }
        if (onFragmentSwitchStateListener != null) {
            onFragmentSwitchStateListener.clickCall(showFragmentType);
        }
    }
}
