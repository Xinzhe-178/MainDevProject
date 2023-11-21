package com.example.networkpro.ui.adapter;

import android.os.Bundle;

import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_common.consts.Const;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.LogUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.RecyclerLayoutBinding;

/**
 * Created by 王鑫哲 on 2021/9/30 下午 02:58
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class RecyclerAdapter extends BaseAdapter<RecyclerBean, RecyclerLayoutBinding> {

    private final String type;

    public RecyclerAdapter(int BR_id, String type) {
        super(BR_id);
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.recycler_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<RecyclerLayoutBinding> mHolder, int position, RecyclerBean data) {
        addItemClickListener((data1, holder, pos) -> {
            data1.cusViewDataKey = type;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.CommonWebViewPageConst.CUS_VIEW_DATA_KEY, data1);
            JumpUtils.jumpWeb(true, data1.link, data1.title, Const.CommonWebViewPageConst.CUS_VIEW_FAVORITE, bundle);

            if (!getDataBean().get(pos).isShowSm) {
                getDataBean().get(pos).isShowSm = true;
                // 因为这个列表加了header 所以刷新的位置要挪动一位
                notifyItemChanged(mRecyclerViewModel.isHaveHeader() ? pos + 1 : pos);
            }
            LogUtils.PrintE("RecyclerAdapter-->url->" + data1.link);
        });
    }
}
