package com.example.networkpro.ui.adapter;

import android.text.Spanned;

import com.example.lib_bean.bean.SeekLenovoBean;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_common.consts.EventPath;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.StringDesignUtil;
import com.example.networkpro.R;
import com.example.networkpro.databinding.SeekLenovoItemLayoutBinding;
import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * Created by 王鑫哲 on 2023/4/20 4:45 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekLenovoAdapter extends BaseEasyAdapter<SeekLenovoBean, SeekLenovoItemLayoutBinding> {

    /**
     * 当前搜索内容
     */
    private String inputText;

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    public SeekLenovoAdapter(int BR_id) {
        super(BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.seek_lenovo_item_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<SeekLenovoItemLayoutBinding> holder, int position, SeekLenovoBean data) {
        SeekLenovoItemLayoutBinding binding = holder.getBinding();

        // 将右边的箭头旋转一定角度
        GlideUtils.setAngleImage(binding.ivArrow, R.drawable.ic_up_arrow, 315);

        // 设置搜索关键字为红色
        Spanned spanned = StringDesignUtil.getSpanned(data.seekText, inputText, "#FF0000");
        binding.tvText.setText(spanned);

        addItemClickListener((data1, holder1, pos) -> {
            // 点击item进行搜索
            LiveEventBus.get(EventPath.SEEK_HIS_ITEM_CLICK).post(data1.seekText);
        });
    }
}
