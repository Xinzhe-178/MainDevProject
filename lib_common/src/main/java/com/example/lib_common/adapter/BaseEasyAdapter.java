package com.example.lib_common.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lib_common.adapter.call.OnItemClickListener;
import com.example.lib_common.adapter.call.OnItemLongClickListener;
import com.example.lib_utils.AntiShakeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/9/29 下午 03:58
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public abstract class BaseEasyAdapter<T, VDB extends ViewDataBinding> extends RecyclerView.Adapter<BaseViewHolder<VDB>> {
    /**
     * 上下文
     */
    public Context mContext;
    /**
     * 数据源
     */
    private List<T> mDataBean;
    /**
     * 点击监听
     */
    private OnItemClickListener<T, VDB> mItemClickListener;
    /**
     * 长按点击监听
     */
    private OnItemLongClickListener<T, VDB> mItemLongClickListener;

    protected int mBR_id;

    private VDB mBinding;

    /**
     * 带数据的构造方法
     *
     * @param dataBean 数据源集合
     */
    public BaseEasyAdapter(List<T> dataBean, int BR_id) {
        this.mDataBean = dataBean;
        this.mBR_id = BR_id;
    }

    /**
     * 无数据的构造方法
     */
    public BaseEasyAdapter(int BR_id) {
        mBR_id = BR_id;
        this.mDataBean = new ArrayList<>();
    }

    @NonNull
    @Override
    public BaseViewHolder<VDB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mBinding = inflate(getLayoutId(), parent);
        return new BaseViewHolder<>(mBinding.getRoot());
    }

    /**
     * 获取Item资源Id
     *
     * @return Item资源Id
     */
    protected abstract int getLayoutId();

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VDB> holder, int position) {
        holder.getBinding().setVariable(mBR_id, mDataBean.get(position));
        holder.getBinding().executePendingBindings();
        onBindView(holder, position, mDataBean.get(position));
        setListener(holder, position);
    }

    /**
     * 监听设置，如果是Item复杂监听，只需要重写该方法，不需要继承其中方法
     *
     * @param holder   SuperViewHolder
     * @param position 位置
     */
    @SuppressLint("CheckResult")
    protected void setListener(BaseViewHolder<VDB> holder, int position) {
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(view -> {
                if (AntiShakeUtils.isInvalidClick(view)) return;
                mItemClickListener.OnItemClick(mDataBean.get(position), holder, position);
            });
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(view -> {
                mItemLongClickListener.onItemLongClick(mDataBean.get(position), holder, position);
                return true;
            });
        }
    }

    /**
     * 设置绑定内容
     *
     * @param holder
     * @param position
     */
    protected abstract void onBindView(BaseViewHolder<VDB> holder, int position, T data);

    @Override
    public int getItemCount() {
        return mDataBean == null ? 0 : mDataBean.size();
    }

    /**
     * 获取数据源
     *
     * @return 数据源集合
     */
    public List<T> getDataBean() {
        return mDataBean;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 更新数据源数据（清空数据源 慎用）
     *
     * @param dataBean 新数据
     */
    @SuppressLint("NotifyDataSetChanged")
    public void update(List<T> dataBean) {
        if (mDataBean != null) {
            mDataBean.clear();
            mDataBean.addAll(dataBean);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新数据源数据(不清空数据源)
     *
     * @param dataBean 新数据
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<T> dataBean) {
        if (mDataBean != null) {
            mDataBean.addAll(dataBean);
            notifyDataSetChanged();
        }
    }

    /**
     * 更新数据源数据（清空数据源 慎用）
     *
     * @param dataBean 新数据
     */
    @SuppressLint("NotifyDataSetChanged")
    public void addData(List<T> dataBean) {
        if (mDataBean != null) {
            mDataBean.addAll(dataBean);
            notifyItemRangeChanged(0, mDataBean.size());
        }
    }

    /**
     * 添加数据 在原数据上追加
     *
     * @param position 添加数据位置
     * @param data     需要添加的数据
     */
    public void addDataWithPosition(int position, List<T> data) {
        if (mDataBean != null) {
            mDataBean.addAll(position, data);
            notifyItemInserted(position);
        }
    }

    /**
     * 附加数据源（在源数据基础上附加 慎用）
     *
     * @param dataBean
     */
    @SuppressLint("NotifyDataSetChanged")
    public void add(T dataBean) {
        if (mDataBean != null) {
            mDataBean.add(dataBean);
            notifyDataSetChanged();
        }
    }

    /**
     * Item点击事件监听
     *
     * @param itemClickListener 点击事件
     */
    public void addItemClickListener(OnItemClickListener<T, VDB> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * Item长按事件监听
     *
     * @param itemLongClickListener 长按事件
     */
    public void addItemLongClickListener(OnItemLongClickListener<T, VDB> itemLongClickListener) {
        mItemLongClickListener = itemLongClickListener;
    }

    /**
     * 清除列表中所有数据
     */
    @SuppressLint("NotifyDataSetChanged")
    public void removeAll() {
        if (mDataBean != null && mDataBean.size() > 0) {
            mDataBean.clear();
            notifyDataSetChanged();
        }
    }

    /**
     * 清除Item
     *
     * @param position
     */
    public void removeItem(int position) {
        if (mDataBean != null && mDataBean.size() > 0) {
            mDataBean.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }
    }

    public VDB getBinding() {
        return mBinding;
    }

    /**
     * 获取布局ViewDataBinding
     *
     * @param layoutId
     * @param parent
     * @return
     */
    protected <T extends ViewDataBinding> T inflate(int layoutId, ViewGroup parent) {
        return DataBindingUtil.inflate(LayoutInflater.from(mContext), layoutId, parent, false);
    }
}
