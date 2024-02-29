package com.example.networkpro.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lib_bean.bean.HomeCardShowBean;
import com.example.lib_common.Interfac.AbsTextWatcher;
import com.example.lib_common.binding.call.OnBindingClickCall;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.consts.Const;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_common.view.SpinnerView;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.CusSeekViewLayoutBinding;
import com.example.networkpro.databinding.SeekTypeItemLayoutBinding;

import java.util.ArrayList;

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

    /**
     * 搜索类型默认值
     */
    private HomeCardShowBean seekTypeValue;

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

    public HomeCardShowBean getSeekTypeValue() {
        return seekTypeValue;
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
                mBinding.ivLeft.setVisibility(VISIBLE);
                mBinding.vClick.setVisibility(VISIBLE);
                mBinding.vSv.setVisibility(GONE);
                mBinding.vClick.setOnTouchListener((v, event) -> {
                    if (mOnTouchListener != null) {
                        mOnTouchListener.clickCall();
                    }
                    return true;
                });
                break;
            case TYPE_INPUT:
                mBinding.vClick.setVisibility(GONE);
                mBinding.ivLeft.setVisibility(GONE);
                mBinding.vSv.setVisibility(VISIBLE);
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

        initSv();
    }

    /**
     * 设置搜索类型view是否显示
     * 只在搜索历史页显示，其他页面隐藏
     */
    public void setSeekTypeViewIsShow(boolean isShow) {
        mBinding.vSv.setVisibility(isShow ? VISIBLE : GONE);
    }

    /**
     * 初始化搜索类型
     */
    @SuppressLint("NotifyDataSetChanged")
    private void initSv() {
        final ArrayList<Object> list = new ArrayList<>();

        list.add(new HomeCardShowBean("安卓", false, Const.SeekValueShowType.SEEK_VALUE_ANDROID));
        list.add(new HomeCardShowBean("图片", false, Const.SeekValueShowType.SEEK_HISTORY_PICTURE));
        list.add(new HomeCardShowBean("百科", false, Const.SeekValueShowType.SEEK_LENOVO_WIKIPEDIA));

        SeekTypeItemLayoutBinding topView = inflate(R.layout.seek_type_item_layout);

        // 默认显示类型
        Object defType = list.get(0);

        if (defType instanceof HomeCardShowBean) {
            HomeCardShowBean data = (HomeCardShowBean) defType;
            seekTypeValue = data;

            data.iconIsShow = true;
            topView.tvTitle.setText(seekTypeValue.title);
        }

        mBinding.vSv
                .setData(list)
                .setTopView(topView.getRoot())
                .setWidthIdEqualTopView(true)
                .setOpenItemView(R.layout.seek_type_item_layout)
                .setItemInitCall((view, o) -> {
                    TextView tv_title = view.findViewById(R.id.tv_title);
                    if (o instanceof HomeCardShowBean) {
                        HomeCardShowBean bean1 = (HomeCardShowBean) o;
                        tv_title.setText(bean1.title);

                        ImageView imageView = view.findViewById(R.id.iv_down_up);
                        // 由于TopView和ItemView使用的是同一个View 而item的图标有点大不好看 这里调小
                        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                        layoutParams.width = DensityUtils.dp(8);
                        layoutParams.height = DensityUtils.dp(8);
                        imageView.setImageResource(R.drawable.ic_circle);
                        // 设置指示标是否显示
                        imageView.setVisibility(bean1.iconIsShow ? View.VISIBLE : View.GONE);
                        // 直接重置为未选中
                        bean1.iconIsShow = false;
                    }
                })
                .setItemClickCall((view, o) -> {

                    TextView tv_title = view.findViewById(R.id.tv_title);
                    if (o instanceof HomeCardShowBean) {
                        HomeCardShowBean bean1 = (HomeCardShowBean) o;
                        seekTypeValue = bean1;
                        tv_title.setText(seekTypeValue.title);
                        // 将当前点击的设置为显示
                        bean1.iconIsShow = true;
                    }
                    SpinnerView.SpinnerAdapter adapter = mBinding.vSv.getSpinnerAdapter();
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                })
                .setOnOpenCall(view -> {
                    // 展开时设置图标指示为收起
                    ImageView imageView = topView.getRoot().findViewById(R.id.iv_down_up);
                    imageView.setImageResource(R.drawable.ic_arrow_up);
                })
                .setOnDismissCall(view -> {
                    // 关闭时设置图标指示为展开
                    ImageView imageView = topView.getRoot().findViewById(R.id.iv_down_up);
                    imageView.setImageResource(R.drawable.ic_down);
                })
                .build();
    }

    public String getInputText() {
        return TextUtils.getText(mBinding.edInput);
    }

    public EditText getEditText() {
        return mBinding.edInput;
    }
}
