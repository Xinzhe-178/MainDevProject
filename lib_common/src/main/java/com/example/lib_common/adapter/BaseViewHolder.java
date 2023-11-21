package com.example.lib_common.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by 王鑫哲 on 2021/9/29 下午 04:01
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private final T mBinding;

    public T getBinding() {
        return mBinding;
    }

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        mBinding = DataBindingUtil.bind(itemView);
    }
}
