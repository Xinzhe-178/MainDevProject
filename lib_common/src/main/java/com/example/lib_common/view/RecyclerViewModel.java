package com.example.lib_common.view;

import android.view.View;

/**
 * Created by 王鑫哲 on 2022/5/28 上午 11:49
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class RecyclerViewModel {
    private boolean isHaveHeader; // 是否存在Header
    private View header;          // Header
    private boolean isHaveFooter;     // 是否存在Footer
    private View footer;          // Footer

    public boolean isHaveHeader() {
        return isHaveHeader;
    }

    public View getHeader() {
        return header;
    }

    public void setHaveHeader(View header) {
        this.header = header;
        this.isHaveHeader = true;
    }

    public boolean isHaveFooter() {
        return isHaveFooter;
    }

    public View getFooter() {
        return footer;
    }

    public void setHaveFooter(View haveFooter) {
        this.footer = haveFooter;
        this.isHaveFooter = true;
    }
}
