package com.example.networkpro.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_bean.bean.HomeRecyclerGroupBean;
import com.example.lib_utils.DensityUtils;
import com.example.networkpro.ui.adapter.UpdateFoodShopParListAdapter;

import java.util.List;

/**
 * Created by 王鑫哲 on 2022/5/14 下午 09:59
 * E-mail: User_wang_178@163.com
 * Ps: 编辑资料 商品详情页图片展示RecyclerView
 */
public class UpdateFoodShopParListView extends RecyclerView {

    private final Context mContext;

    private UpdateFoodShopParListAdapter mAdapter;

    public UpdateFoodShopParListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    /**
     * 打开相册需要传入Activity 有两个思路
     * 1,将点击回调放在activity中实现 这样可以 但是处理图片展示逻辑会比较麻烦
     * 2,将activity传入adapter中 adapter本就是处理图片展示 放在这里不仅逻辑更加清晰 同时也会替activity分担一定的压力
     * 需要在activity销毁的时候设置为null 避免泄漏风险
     */
    public UpdateFoodShopParListView setActivity(Activity activity) {
        if (activity != null) mAdapter.setActivity(activity);
        return this;
    }

    private void init() {
        setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new UpdateFoodShopParListAdapter(0);
        addItemDecoration(new ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);
                outRect.left = DensityUtils.dp(8);
                outRect.right = DensityUtils.dp(8);
            }
        });
        setAdapter(mAdapter);

        mAdapter.add(new HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList("", true));
    }

    /**
     * 刷新数据
     * 用于每次选择图片后调用
     *
     * @param lists
     */
    public void setData(List<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList> lists) {
        mAdapter.update(lists);
    }

    /**
     * 获取当前选择的图片集合
     * 这里需要处理下末尾+ 情况
     * 如果最后一个是+ 则移除此数据
     *
     * @return
     */
    public List<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList> getSelectImageData() {
        List<HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList> dataBean = mAdapter.getDataBean();
        if (dataBean != null && dataBean.size() > 0) {
            HomeRecyclerGroupBean.RightGroup.ChildImageData.DataList data = dataBean.get(dataBean.size() - 1);
            if (data.isAdd) {
                dataBean.remove(data);
            }
        }
        return dataBean;
    }

    public UpdateFoodShopParListAdapter getAdapter() {
        return mAdapter;
    }
}
