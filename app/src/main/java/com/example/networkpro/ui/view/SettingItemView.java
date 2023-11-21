package com.example.networkpro.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import com.example.networkpro.R;
import com.example.networkpro.databinding.SettingItemLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/8/19 21:20
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SettingItemView extends FrameLayout {

    private final SettingItemLayoutBinding mBinding;

    private final String mText;

    private final int mStartIcon;

    private final int mEndIcon;

    private final boolean isGoneBottomLine;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public SettingItemView(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public SettingItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        mText = typedArray.getString(R.styleable.SettingItemView_siv_text);
        mStartIcon = typedArray.getSourceResourceId(R.styleable.SettingItemView_siv_start_icon, R.drawable.ic_tea);
        mEndIcon = typedArray.getSourceResourceId(R.styleable.SettingItemView_siv_end_icon, R.drawable.ic_right);
        isGoneBottomLine = typedArray.getBoolean(R.styleable.SettingItemView_siv_is_gone_bottom_line, false);
        typedArray.recycle();

        mBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.setting_item_layout, null, false);
        addView(mBinding.getRoot());

        initView();
    }

    protected void initView() {
        mBinding.tvHintText.setText(mText);
        mBinding.ivLeftIcon.setImageResource(mStartIcon);
        mBinding.ivRightIcon.setImageResource(mEndIcon);
        mBinding.vGrayLine.setVisibility(isGoneBottomLine ? INVISIBLE : VISIBLE);
    }
}