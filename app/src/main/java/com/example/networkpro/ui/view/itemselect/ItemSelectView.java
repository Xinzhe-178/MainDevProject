package com.example.networkpro.ui.view.itemselect;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.databinding.DataBindingUtil;

import com.example.lib_common.view.SpinnerView;
import com.example.lib_common.view.SwitchButton;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.TextUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ItemSelectLayoutBinding;

import java.util.List;

/**
 * Created by 王鑫哲 on 2023/3/1 3:08 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ItemSelectView extends NestedScrollView implements IItemSelect<ItemSelectView> {

    private final Context mContext;

    private View mEndView;

    public ItemSelectView(@NonNull Context context) {
        this(context, null);
    }

    public ItemSelectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemSelectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = getContext();
    }

    private View createView(ItemSelectBean bean) {
        ItemSelectLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_select_layout, null, false);

        if (!TextUtils.isEmpty(bean.startHintText)) {
            binding.tvStartHint.setText(bean.startHintText);
        }

        FrameLayout.LayoutParams layoutParams = (LayoutParams) binding.viewBg.getLayoutParams();
        layoutParams.leftMargin = bean.leftMargin;
        layoutParams.rightMargin = bean.rightMargin;
        layoutParams.topMargin = bean.topMargin;
        layoutParams.bottomMargin = bean.bottomMargin;
        binding.viewBg.setLayoutParams(layoutParams);

        addEndView(bean, binding);

        if (bean.mInitListener != null) {
            bean.mInitListener.onInit(mEndView, bean.mType);
        }

        return binding.getRoot();
    }

    @Override
    public void create(@NonNull List<ItemSelectBean> beans) {
        LinearLayout linearLayout = new LinearLayout(mContext);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
        linearLayout.setLayoutParams(layoutParams);

        for (ItemSelectBean bean : beans) {
            linearLayout.addView(createView(bean));
        }
        addView(linearLayout);
    }

    @Override
    public View getEndView() {
        return mEndView;
    }

    private void addEndView(ItemSelectBean bean, ItemSelectLayoutBinding binding) {
        ViewGroup.LayoutParams endLayoutParams = binding.viewEnd.getLayoutParams();
        switch (bean.mType) {
            case TYPE_SWITCH_BUTTON:
                mEndView = new SwitchButton(mContext);
                FrameLayout.LayoutParams switchLayoutParams = new FrameLayout.LayoutParams(DensityUtils.dp(70), endLayoutParams.height);
                switchLayoutParams.leftMargin = DensityUtils.dp(20);
                switchLayoutParams.rightMargin = DensityUtils.dp(15);
                mEndView.setLayoutParams(switchLayoutParams);
                break;
            case TYPE_SPINNER:
                mEndView = new SpinnerView(mContext);
                FrameLayout.LayoutParams spinnerLayoutParams = new FrameLayout.LayoutParams(endLayoutParams.width - DensityUtils.dp(35), binding.viewBg.getLayoutParams().height - DensityUtils.dp(15));
                spinnerLayoutParams.leftMargin = DensityUtils.dp(20);
                spinnerLayoutParams.rightMargin = DensityUtils.dp(15);
                mEndView.setLayoutParams(spinnerLayoutParams);
                break;
        }
        binding.viewEnd.addView(mEndView);
    }

    public interface onInitListener {
        void onInit(View endView, ItemSelectType type);
    }
}
