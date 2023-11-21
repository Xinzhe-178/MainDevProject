package com.example.lib_bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 王鑫哲 on 2021/8/27 下午 02:26
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BaseArrBean<B> implements Serializable {
    public DataDTO<B> data;
    public int errorCode;
    public String errorMsg;

    public static class DataDTO<B> implements Serializable {
        public int curPage;
        public List<B> datas;
        public int offset;
        public boolean over;
        public int pageCount;
        public int size;
        public int total;
    }
}
