package com.example.lib_bean.bean;

/**
 * Created by 王鑫哲 on 2022/7/13 4:49 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BeautyBean {

    public boolean success;
    public String imgurl;
    public InfoDTO info;

    public static class InfoDTO {
        public int width;
        public int height;
        public String type;
    }
}
