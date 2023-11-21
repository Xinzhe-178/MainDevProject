package com.example.networkpro.ui.adapter;

import com.alibaba.fastjson.JSON;
import com.example.lib_bean.bean.SeekHotListBean;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.LogUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.SeekHotListItemLayoutBinding;

/**
 * Created by 王鑫哲 on 2022/11/30 19:16
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekHotListAdapter extends BaseEasyAdapter<SeekHotListBean.DataDTO, SeekHotListItemLayoutBinding> {
    public SeekHotListAdapter(int BR_id) {
        super(BR_id);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.seek_hot_list_item_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<SeekHotListItemLayoutBinding> holder, int position, SeekHotListBean.DataDTO data) {
        holder.getBinding().setIsShowNum(data.index > 3);

        addItemClickListener((data1, holder1, pos) -> {
            LogUtils.PrintE("data-->" + JSON.toJSONString(data1));
            JumpUtils.jumpWeb(true, data1.mobilUrl, "");
        });
    }
}
