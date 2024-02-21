package com.example.networkpro.ui.adapter;

import android.os.Bundle;

import com.example.lib_bean.bean.RecyclerBean;
import com.example.lib_common.adapter.BaseAdapter;
import com.example.lib_common.adapter.BaseViewHolder;
import com.example.lib_common.consts.Const;
import com.example.lib_common.utils.JumpUtils;
import com.example.lib_utils.GlideUtils;
import com.example.lib_utils.LogUtils;
import com.example.lib_utils.TextUtils;
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
        mHolder.getBinding().setAdapter(this);

        GlideUtils.setImageResource(mHolder.getBinding().ivImg, data.collect ? R.drawable.ic_favorite_on : R.drawable.ic_favorite_no);

        addItemClickListener((data1, holder, pos) -> {
            data1.cusViewDataKey = type;
            Bundle bundle = new Bundle();
            bundle.putSerializable(Const.CommonWebViewPageConst.CUS_VIEW_DATA_KEY, data1);
            JumpUtils.jumpWeb(true, data1.link, data1.title, Const.CommonWebViewPageConst.CUS_VIEW_FAVORITE, bundle);

            if (!getDataBean().get(pos).isShowSm) {
                // 盖一层蒙版实现已读效果
                getDataBean().get(pos).isShowSm = true;
                // 因为这个列表加了header 所以刷新的位置要挪动一位
                notifyItemChanged(mRecyclerViewModel.isHaveHeader() ? pos + 1 : pos);
            }
            LogUtils.PrintE("RecyclerAdapter-->url->" + data1.link);
        });
    }

    /**
     * 当前是否是收藏
     * 因为广场和收藏用的是同一套代码，而布局显示有稍许不一样
     *
     * @return
     */
    public boolean isCollect() {
        return TextUtils.equals(type, Const.CommonWebViewPageConst.CUS_VIEW_DATA_FAVORITE_LIST);
    }

    /**
     * 获取左边显示内容
     * 由于显示逻辑较为复杂，所以单独抽出来
     *
     * @param bean
     * @return
     */
    public String getLeftText(RecyclerBean bean) {
        String text = "";
        if (isCollect()) {
            // 收藏展示
            text = "分类：" + bean.chapterName;
        } else {
            // 普通展示
            text = !TextUtils.isEmpty(bean.author) ? "作者：" + bean.author : "分享人：" + bean.shareUser;
        }
        return text;
    }
}
