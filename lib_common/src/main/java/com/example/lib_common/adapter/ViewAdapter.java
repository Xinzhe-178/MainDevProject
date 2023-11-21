package com.example.lib_common.adapter;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.example.lib_common.R;

/**
 * Created by 王鑫哲 on 2022/9/24 11:59
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ViewAdapter {

    /**
     * 设置扫码页面手电筒开/关状态
     *
     * @param imageView
     * @param isOpen
     */
    @BindingAdapter("setScanFlashIconStateIsOpen")
    public static void setScanFlashIconState(ImageView imageView, boolean isOpen) {
        imageView.setImageResource(isOpen ? R.drawable.icon_flash_on : R.drawable.icon_flash_off);
    }
}
