package com.example.lib_common.topbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.lib_common.R;
import com.example.lib_common.databinding.TopbarViewBinding;
import com.example.lib_common.view.BaseFrameLayout;
import com.example.lib_common.view.RollTextView;
import com.example.lib_utils.DensityUtils;

import org.jetbrains.annotations.NotNull;

/**
 * Created by 王鑫哲 on 2021/9/13 上午 11:27
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class TopBarView extends BaseFrameLayout<TopbarViewBinding> {

    private TopBarOnClickListener mTopBarOnClickListener;

    public void setTopBarOnClickListener(TopBarOnClickListener topBarOnClickListener) {
        mTopBarOnClickListener = topBarOnClickListener;
    }

    public TopBarView(@NonNull @NotNull Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.topbar_view;
    }

    @Override
    public void initView() {
        setTitle("");
    }

    @Override
    public void initListener() {
        mBinding.ivFinish.setOnClickListener(v -> {
            if (mTopBarOnClickListener != null) {
                mTopBarOnClickListener.onFinishCLickCall();
            }
        });
        mBinding.ivClose.setOnClickListener(v -> {
            if (mTopBarOnClickListener != null) {
                mTopBarOnClickListener.onCloseClickCall();
            }
        });
        mBinding.tvTitle.setOnClickListener(v -> {
            if (mTopBarOnClickListener != null) {
                mTopBarOnClickListener.onTitleClickCall();
            }
        });
    }

    public TopBarView setTitle(String title) {
        mBinding.tvTitle.setText(title);
        return this;
    }

    public TopBarView setTitleColor(int color) {
        mBinding.tvTitle.setTextColor(color);
        return this;
    }

    public TopBarView setCloseShow(boolean isShow) {
        mBinding.ivClose.setVisibility(isShow ? VISIBLE : GONE);
        return this;
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        mBinding.clMax.setBackgroundColor(color);
    }

    public View getPlaceholderView() {
        return mBinding.viewTopPlaceholder;
    }

    public ImageView getLeftBackView() {
        return mBinding.ivFinish;
    }

    public RollTextView getTitleView() {
        return mBinding.tvTitle;
    }

    /**
     * 如果自己单独写TopBar 则需要改动下设置，这里统一提取出来
     * 只负责更改高度，不涉及颜色大小等
     */
    public void CusTopBarSet(TopBarView topBarView) {
        View placeholderView = topBarView.getPlaceholderView();
        placeholderView.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = placeholderView.getLayoutParams();
        layoutParams.height = DensityUtils.getStatusHeight() + DensityUtils.dp(10);
        placeholderView.setLayoutParams(layoutParams);

        TextView titleView = topBarView.getTitleView();
        ConstraintLayout.LayoutParams titleParams = (ConstraintLayout.LayoutParams) titleView.getLayoutParams();
        titleParams.bottomMargin = DensityUtils.dp(2);
        titleView.setLayoutParams(titleParams);

        ImageView backView = topBarView.getLeftBackView();
        ConstraintLayout.LayoutParams backParams = (ConstraintLayout.LayoutParams) backView.getLayoutParams();
        backParams.bottomMargin = DensityUtils.dp(2);
        backView.setLayoutParams(backParams);
    }
}
