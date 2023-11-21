package com.example.networkpro.ui.adapter;

import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.lib_bean.bean.SeekBean;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_utils.DensityUtils;
import com.example.lib_utils.LogUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.SeekAdapterLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/4/2 下午 03:08
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekAdapter extends BaseEasyAdapter<SeekBean.ListDTO, SeekAdapterLayoutBinding> {
    /**
     * 屏幕宽度
     */
    private static final int SCREEN_WIDTH = DensityUtils.getWidth();

    /**
     * item宽度
     * 屏幕宽度-左中右边距/2
     */
    public static final int ITEM_WIDTH = (SCREEN_WIDTH - (DensityUtils.dp(8) * 3)) / 2;

    public SeekAdapter(int BR_id) {
        super(BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.seek_adapter_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<SeekAdapterLayoutBinding> holder, int position, SeekBean.ListDTO data) {
        SeekAdapterLayoutBinding binding = holder.getBinding();
        ViewGroup.LayoutParams params = binding.ivImage.getLayoutParams();
        LogUtils.PrintE("ivWidth-->" + ITEM_WIDTH + " mScreenWidth = " + SCREEN_WIDTH);
        params.width = ITEM_WIDTH;
        binding.ivImage.setLayoutParams(params);

        Glide.with(mContext)
                .load(data.thumb)
                .override(ITEM_WIDTH, Target.SIZE_ORIGINAL)
                .fitCenter()
                .error(R.drawable.ic_placeholder_view)
                .placeholder(R.drawable.ic_placeholder_view)
                .into(binding.ivImage);
    }
}