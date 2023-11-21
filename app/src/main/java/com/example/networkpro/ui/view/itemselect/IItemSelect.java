package com.example.networkpro.ui.view.itemselect;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * Created by 王鑫哲 on 2023/3/1 3:09 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface IItemSelect<T extends ItemSelectView> {
    void create(@NonNull List<ItemSelectBean> beans);

    View getEndView();
}
