package com.example.networkpro.manage.fragment;

import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * Created by 王鑫哲 on 2023/4/15 14:06
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface IFragmentSwitch {
    void init(FragmentManager manager, @LayoutRes int fragmentLayout, int defIndex, Fragment... fragment);

    void switchPage(int index);

    Fragment getCurSelFragment();

    Fragment[] getAllFragment();
}
