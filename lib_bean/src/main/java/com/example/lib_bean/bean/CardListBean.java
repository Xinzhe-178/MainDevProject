package com.example.lib_bean.bean;

import androidx.databinding.ViewDataBinding;


/**
 * Created by 王鑫哲 on 2022/8/27 15:14
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class CardListBean {
    public ViewDataBinding binding;
    public OnClosureCall onClosureCall;

    public CardListBean(ViewDataBinding binding, OnClosureCall onClosureCall) {
        this.binding = binding;
        this.onClosureCall = onClosureCall;
    }

    public CardListBean() {

    }

    public interface OnClosureCall {
        void onDismiss();

        void onRemoveItem(int pos);
    }
}
