package com.example.lib_common.adapter.call;

import androidx.databinding.ViewDataBinding;

import com.example.lib_common.adapter.BaseViewHolder;

/**
 * Created by 王鑫哲 on 2021/9/30 上午 10:26
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface OnItemClickListener<T, VDB extends ViewDataBinding> {
    void OnItemClick(T data, BaseViewHolder<VDB> holder, int pos);
}
