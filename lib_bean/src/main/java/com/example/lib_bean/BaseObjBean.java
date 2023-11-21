package com.example.lib_bean;

import java.io.Serializable;

/**
 * Created by 王鑫哲 on 2021/8/27 下午 02:26
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BaseObjBean<B> implements Serializable {
    public B data;
    public int errorCode;
    public String errorMsg;
}