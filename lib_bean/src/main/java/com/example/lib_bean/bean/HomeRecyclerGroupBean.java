package com.example.lib_bean.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/11/24 下午 06:11
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class HomeRecyclerGroupBean implements Serializable {
    public String leftTitle; // 左边列表使用 title
    public boolean isSelect; // 左边列表是否选中
    public String groupId;   // ID 唯一不可重复 取时间戳
    public int relevanceId;  // 此ID用来左右两边列表联动 需和右边ID保持一致 一般为当前位置
    public List<RightGroup> RightGroup; // 右边列表使用

    public HomeRecyclerGroupBean(String leftTitle, List<HomeRecyclerGroupBean.RightGroup> rightGroup) {
        this.leftTitle = leftTitle;
        RightGroup = rightGroup;
    }

    public HomeRecyclerGroupBean(String leftTitle, int relevanceId) {
        this.leftTitle = leftTitle;
        this.groupId = String.valueOf(System.currentTimeMillis());
        this.relevanceId = relevanceId;
        RightGroup = new ArrayList<>();
    }

    public static class RightGroup implements Serializable {
        public String rightTitle;        // title
        public String imageUrl;          // 图片地址
        public String childId;           // ID 唯一不可重复 取时间戳
        public String desc;              // 简介
        public int stockCount;           // 库存数量
        public double price;             // 现价
        public double originalPrice;     // 原价
        public int relevanceId;          // 此ID用来左右两边列表联动 需和上级ID保持一致
        public boolean isSeckill;        // 是否是秒杀商品
        public int count;                // 数量
        public int indexOfLeft;          // 在左边哪个位置
        public ChildImageData childImageData;   //详情图片集合

        /**
         * 详情页所需要的数据
         */
        public static class ChildImageData implements Serializable {
            public String desc;                // 详情页总体简介
            public List<DataList> mImageLists; // 详情页数据集合

            public static class DataList implements Serializable {
                public String imgPath;      // 图片地址
                public boolean isAdd;       // 当前展示是否为 +

                //以下暂未用到
                public String hintText;     // 每张图片的描述
                public String videoPath;    // 视频地址
                public boolean typeIsVideo; // 当前类型是否为视频  只有两种情况 默认为false 为图片

                public DataList(String imgPath, boolean isAdd) {
                    this.imgPath = imgPath; // 图片全路径
                    this.isAdd = isAdd;     // 当前数据是否为 +  也就是添加图片时末尾那个 + 号
                }
            }
        }
    }
}
