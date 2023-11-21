package com.example.networkpro.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_common.Interfac.AbsTextWatcher;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.CusSeekViewLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/9/3 20:14
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CusSeekView extends BaseFrameLayout<CusSeekViewLayoutBinding> {

    /**
     * 展示类型
     * 不可输入 不获取焦点 只有触摸操作
     */
    public static final String TYPE_SHOW = "type_show";
    /**
     * 输入类型
     * 和正常输入框一样，可以进行搜索
     */
    public static final String TYPE_INPUT = "type_input";
    /**
     * 所属类型
     */
    private String type = TYPE_SHOW;
    /**
     * 是否可以输入空格
     */
    public boolean isInputSpace = false;
    /**
     * 左边的搜索logo是否展示
     */
    private boolean leftSeekLogoIsShow = true;
    /**
     * 最大输入长度
     */
    private int maxInputLength = 20;

    private OnBindingClickCall mOnTouchListener;

    public void setOnTouchListener(OnBindingClickCall onTouchListener) {
        mOnTouchListener = onTouchListener;
    }

    public CusSeekView setMaxInputLength(int maxInputLength) {
        this.maxInputLength = maxInputLength;
        return this;
    }

    private OnBindingClickParamsCall<String> mOnSeekClick;

    public void setOnSeekClick(OnBindingClickParamsCall<String> onSeekClick) {
        mOnSeekClick = onSeekClick;
    }

    private OnBindingClickParamsCall<String> mAddTextChangedListener;

    public void addTextChangedListener(OnBindingClickParamsCall<String> onCall) {
        mAddTextChangedListener = onCall;
    }

    public CusSeekView setType(String type) {
        this.type = type;
        return this;
    }

    public CusSeekView setLeftSeekLogoIsShow(boolean leftSeekLogoIsShow) {
        this.leftSeekLogoIsShow = leftSeekLogoIsShow;
        return this;
    }

    public CusSeekView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void build() {
        mBinding.ivLeft.setVisibility(leftSeekLogoIsShow ? VISIBLE : GONE);

        if (!isInputSpace) {
            // 禁止输入空格
            InputFilter filter = (source, start, end, dest, dstart, dend) -> {
                if (source.equals(" ")) return "";
                else return null;
            };
            // 最大长度InputFilter
            InputFilter.LengthFilter inputFilter = new InputFilter.LengthFilter(maxInputLength);
            mBinding.edInput.setFilters(new InputFilter[]{filter, inputFilter});
        } else {
            TextUtils.setEditTextMaxLength(mBinding.edInput, maxInputLength);
        }

        switch (type) {
            case TYPE_SHOW:
                mBinding.vClick.setVisibility(VISIBLE);
                mBinding.vClick.setOnTouchListener((v, event) -> {
                    if (mOnTouchListener != null) {
                        mOnTouchListener.clickCall();
                    }
                    return true;
                });
                break;
            case TYPE_INPUT:
                mBinding.vClick.setVisibility(GONE);
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.cus_seek_view_layout;
    }

    public CusSeekView setIsInputSpace(boolean isInputSpace) {
        this.isInputSpace = isInputSpace;
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initView() {
        // 监听输入状态 显示/隐藏清除icon
        mBinding.edInput.addTextChangedListener(new AbsTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                super.onTextChanged(s, start, before, count);
                mBinding.ivClear.setVisibility(TextUtils.getTextLength(mBinding.edInput) > 0 ? VISIBLE : GONE);
                if (mAddTextChangedListener != null) {
                    mAddTextChangedListener.clickCall(s.toString().trim());
                }
            }
        });

        // 点击清除 清除输入框内容
        mBinding.ivClear.setOnClickListener(view -> mBinding.edInput.setText(""));

        mBinding.edInput.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                if (TextUtils.isEmpty(getInputText())) {
                    ToastUtils.show("请输入搜索关键字");
                    return true;
                }
                // 搜索功能主体
                if (mOnSeekClick != null) {
                    mOnSeekClick.clickCall(getInputText());
                }
                return true;
            }
            return false;
        });
    }

    public String getInputText() {
        return TextUtils.getText(mBinding.edInput);
    }

    public EditText getEditText() {
        return mBinding.edInput;
    }
}
