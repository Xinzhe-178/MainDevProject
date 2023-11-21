package com.example.lib_bean.bean;


/**
 * Created by 王鑫哲 on 2022/2/5 上午 11:42
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeBottomShopBean {
    public int coverImageUrl;         // 封面图片
    public int commodityCount;        // 商品数量
    public float commodityTotalPrice; // 商品总价

    public HomeBottomShopBean(int commodityCount, float commodityTotalPrice) {
        this.commodityCount = commodityCount;
        this.commodityTotalPrice = commodityTotalPrice;
    }
}
