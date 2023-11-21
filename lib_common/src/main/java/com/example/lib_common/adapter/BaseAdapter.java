package com.example.lib_common.adapter;

import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

import com.example.lib_common.consts.Const;
import com.example.lib_common.view.RecyclerViewModel;

import java.util.List;

/**
 * Created by 王鑫哲 on 2021/9/29 下午 03:58
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseAdapter<T, VDB extends ViewDataBinding> extends BaseEasyAdapter<T, VDB> {

    protected RecyclerViewModel mRecyclerViewModel;

    public BaseAdapter(int BR_id) {
        this(BR_id, null);
    }

    public BaseAdapter(int BR_id, RecyclerViewModel model) {
        super(BR_id);
        mRecyclerViewModel = model != null ? model : new RecyclerViewModel();
    }

    public BaseAdapter(List<T> dataBean, int BR_id) {
        this(dataBean, BR_id, null);
    }

    public BaseAdapter(List<T> dataBean, int BR_id, RecyclerViewModel model) {
        super(dataBean, BR_id);
        mRecyclerViewModel = model != null ? model : new RecyclerViewModel();
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VDB> holder, int position) {
        if (getItemViewType(position) == Const.AdapterType.TYPE_HEADER) {
            // TODO: 2022/5/28  header 加载逻辑
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
            mRecyclerViewModel.getHeader().setLayoutParams(layoutParams);
        } else if (getItemViewType(position) == Const.AdapterType.TYPE_BODY) {
            int newPos = mRecyclerViewModel.isHaveHeader() ? position - 1 : position;
            holder.getBinding().setVariable(mBR_id, getDataBean().get(newPos));
            holder.getBinding().executePendingBindings();
            onBindView(holder, newPos, getDataBean().get(newPos));
            setListener(holder, newPos);
        } else if (getItemViewType(position) == Const.AdapterType.TYPE_FOOTER) {
            // TODO: 2022/5/28  footer 加载逻辑
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(GridLayout.LayoutParams.MATCH_PARENT, GridLayout.LayoutParams.WRAP_CONTENT);
            mRecyclerViewModel.getFooter().setLayoutParams(layoutParams);
        }
    }

    @NonNull
    @Override
    public BaseViewHolder<VDB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        switch (viewType) {
            case Const.AdapterType.TYPE_HEADER:
                return new BaseViewHolder<>(mRecyclerViewModel.getHeader());
            case Const.AdapterType.TYPE_FOOTER:
                return new BaseViewHolder<>(mRecyclerViewModel.getFooter());
            default:
                return super.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!mRecyclerViewModel.isHaveHeader() && !mRecyclerViewModel.isHaveFooter()) {
            return Const.AdapterType.TYPE_BODY;
        } else {
            if (mRecyclerViewModel.isHaveHeader() && mRecyclerViewModel.isHaveFooter()) {
                return position == 0 ? Const.AdapterType.TYPE_HEADER : position == getItemCount() - 1 ? Const.AdapterType.TYPE_FOOTER : Const.AdapterType.TYPE_BODY;
            }
            if (mRecyclerViewModel.isHaveHeader()) {
                return position == 0 ? Const.AdapterType.TYPE_HEADER : Const.AdapterType.TYPE_BODY;
            } else {
                return position == getItemCount() - 1 ? Const.AdapterType.TYPE_FOOTER : Const.AdapterType.TYPE_BODY;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (getDataBean() == null) {
            return 0;
        } else {
            if (!mRecyclerViewModel.isHaveHeader() && !mRecyclerViewModel.isHaveFooter()) {
                return getDataBean().size();
            } else {
                if (mRecyclerViewModel.isHaveHeader() && mRecyclerViewModel.isHaveFooter()) {
                    return getDataBean().size() + 2;
                } else {
                    return getDataBean().size() + 1;
                }
            }
        }
    }

    public void setRecyclerViewModel(RecyclerViewModel model) {
        this.mRecyclerViewModel = model != null ? model : new RecyclerViewModel();
    }
}
