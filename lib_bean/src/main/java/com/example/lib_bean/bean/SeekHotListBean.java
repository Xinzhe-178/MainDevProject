package com.example.lib_bean.bean;

import java.util.List;

/**
 * Created by 王鑫哲 on 2022/11/30 19:17
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SeekHotListBean {
    public boolean success;
    public String title;
    public String subtitle;
    public String update_time;
    public List<DataDTO> data;

    public static class DataDTO {
        public int index;
        public String title;
        public String desc;
        public String pic;
        public String hot;
        public String url;
        public String mobilUrl;
    }
}
