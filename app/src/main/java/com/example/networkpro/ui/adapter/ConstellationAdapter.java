package com.example.networkpro.ui.adapter;

import android.view.View;

import com.example.lib_bean.bean.ConstellationBean;
import com.example.lib_common.adapter.BaseEasyAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_utils.TextUtils;
import com.example.lib_utils.ToastUtils;
import com.example.networkpro.R;
import com.example.networkpro.databinding.ConstellationItemLayoutBinding;
import com.example.networkpro.ui.view.ConstellationView;

/**
 * Created by 王鑫哲 on 2023/7/6 2:47 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ConstellationAdapter extends BaseEasyAdapter<ConstellationBean.ConstellationItem, ConstellationItemLayoutBinding> {

    private final ConstellationView mConstellationView;

    public ConstellationAdapter(int BR_id, ConstellationView constellationView) {
        super(BR_id);
        mConstellationView = constellationView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.constellation_item_layout;
    }

    @Override
    protected void onBindView(BaseViewHolder<ConstellationItemLayoutBinding> holder, int position, ConstellationBean.ConstellationItem data) {
        holder.getBinding().setIsSel(selectPosition == position);
        holder.itemView.setVisibility(TextUtils.isEmpty(data.ConstellationName) ? View.INVISIBLE : View.VISIBLE);

        addItemClickListener((data1, holder1, pos) -> {
            // 滚动到指定位置
            mConstellationView.scrollToCenter(pos);
        });
    }

    private int selectPosition = -1;

    public void setSelectPosition(int cposition) {
        selectPosition = cposition;
//        notifyItemChanged(cposition);
        notifyDataSetChanged();
    }
}
