package com.example.lib_statistics;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 王鑫哲 on 2023/2/8 2:26 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class BaseStatistics implements IStatistics {

    protected static final String TAG = "BaseStatistics 日志- ";

    @Override
    public void addRecord(String record) {

    }

    @SuppressLint("SimpleDateFormat")
    protected String getTime() {
        long currentTimeMillis = System.currentTimeMillis();
        //将时间戳转换为时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将时间戳转换为时间
        Date date = new Date(currentTimeMillis);
        //将时间调整为yyyy-MM-dd HH:mm:ss时间样式
        String time = simpleDateFormat.format(date);
        return "\t时间->".concat(time);
    }
}
