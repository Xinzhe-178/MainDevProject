package com.example.networkpro.ui.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.widget.GridLayout;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_bean.bean.CardListBean;
import com.example.lib_common.binding.call.OnBindingClickParamsCall;
import com.example.lib_common.dialog.EasyDialog;
import com.example.lib_utils.DensityUtils;
import com.example.networkpro.AppApplication;
import com.example.networkpro.R;
import com.example.networkpro.databinding.CardListDialogLayoutBinding;
import com.example.networkpro.ui.adapter.CardListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/8/27 15:01
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class CardListTopDialog extends EasyDialog<CardListDialogLayoutBinding> implements CardListBean.OnClosureCall {

    private CardListAdapter mAdapter;

    public static final int leftAndRightMargin = DensityUtils.dp(25);

    public static final int middleMargin = DensityUtils.dp(15);

    public CardListTopDialog(FragmentActivity activity) {
        this(activity == null ? AppApplication.getInstance() : activity, R.layout.card_list_dialog_layout);
    }

    protected List<CardListBean> getCardData() {
        return mAdapter.getDataBean() != null ? mAdapter.getDataBean() : new ArrayList<>();
    }

    protected int getCardDataPos(@NonNull CardListBean bean) {
        return getCardData().indexOf(bean);
    }

    public CardListTopDialog(@NonNull Context context, int layoutId) {
        super(context, layoutId);
        setClickWindowIsDismiss(false);
        setWidth(GridLayout.LayoutParams.MATCH_PARENT);
        setHeight(GridLayout.LayoutParams.MATCH_PARENT);
        setCancelable(false);
        build();
    }

    @Override
    protected void initView(CardListDialogLayoutBinding mBinding, Dialog dialog) {
        mBinding.rvList.setLayoutManager(new LinearLayoutManager(mContent, LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new CardListAdapter(getDataBean(), 0);
        mBinding.rvList.setAdapter(mAdapter);
        mBinding.rvList.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, int itemPosition, @NonNull RecyclerView parent) {
                super.getItemOffsets(outRect, itemPosition, parent);

                // 如只有一个item 则两边边距要一致
                if (mAdapter.getItemCount() == 1) {
                    outRect.set(leftAndRightMargin, 0, leftAndRightMargin, 0);
                } else {
                    // 设置item边距，无上下边距，左右两边边距要比中间稍微大点
                    if (itemPosition == 0) {
                        // 第一个条目
                        outRect.set(leftAndRightMargin, 0, middleMargin, 0);
                    } else if (itemPosition == mAdapter.getItemCount() - 1) {
                        // 最后一个条目
                        outRect.set(0, 0, leftAndRightMargin, 0);
                    } else {
                        // 中间条目
                        outRect.set(0, 0, middleMargin, 0);
                    }
                }

            }
        });
    }

    /**
     * 快捷设置数据抽取
     *
     * @param layoutID    加载布局Layout
     * @param clickViewId 点击关闭弹窗 该View的ID
     * @param clickCall   点击回调 如需自定义关闭逻辑 则传入回调即可 如果点击clickCall为null 则走默认关闭逻辑 否则为自定义逻辑
     * @return
     */
    protected CardListBean getData(@LayoutRes int layoutID, @IdRes int clickViewId, OnBindingClickParamsCall<CardListBean> clickCall) {
        ViewDataBinding binding = inflate(layoutID);
        CardListBean bean = new CardListBean(binding, this);
        if (clickViewId != 0) {
            binding.getRoot().findViewById(clickViewId).setOnClickListener(view -> {
                if (clickCall == null) {
                    bean.onClosureCall.onRemoveItem(getCardDataPos(bean));
                } else {
                    clickCall.clickCall(bean);
                }
            });
        }
        return bean;
    }

    protected abstract List<CardListBean> getDataBean();

    public abstract void showDialog();

    @Override
    public void onDismiss() {
        dismiss();
    }

    @Override
    public void onRemoveItem(int pos) {
        if (mAdapter != null && mAdapter.getItemCount() > 0 && pos < mAdapter.getItemCount()) {
            mAdapter.removeItem(pos);
        }

        // 当没有数据的时候直接默认关闭dialog
        if (mAdapter != null && mAdapter.getItemCount() == 0) {
            onDismiss();
        }
    }
}
