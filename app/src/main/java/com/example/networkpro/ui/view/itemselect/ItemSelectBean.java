package com.example.networkpro.ui.view.itemselect;

import com.example.lib_utils.DensityUtils;

/**
 * Created by 王鑫哲 on 2023/3/1 11:26 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ItemSelectBean {

    public ItemSelectType mType;
    public int leftMargin;
    public int rightMargin;
    public int topMargin;
    public int bottomMargin;
    public String startHintText;
    public ItemSelectView.onInitListener mInitListener;

    public ItemSelectBean(ItemSelectType type, String startHintText) {
        this.startHintText = startHintText;
        this.mType = type;
        this.leftMargin = DensityUtils.dp(10);
        this.rightMargin = DensityUtils.dp(10);
        this.topMargin = DensityUtils.dp(9);
        this.bottomMargin = DensityUtils.dp(9);
    }

    public ItemSelectBean(ItemSelectType type, String startHintText, int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
        this.startHintText = startHintText;
        this.mType = type;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
    }

    /**
     * 对endView进行初始化，需要在createView.create()之前的设置好，最后调用.create() 否则不生效
     *
     * @param listener
     */
    public void init(ItemSelectView.onInitListener listener) {
        mInitListener = listener;
    }
}
