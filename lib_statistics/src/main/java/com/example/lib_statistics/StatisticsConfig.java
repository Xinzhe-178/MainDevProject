package com.example.lib_statistics;

/**
 * Created by 王鑫哲 on 2023/2/8 2:10 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public interface StatisticsConfig {

    interface Type {
        String Click = "点击";
        String Slide = "滑动";
        String Browse = "浏览";
    }

    interface Path {
        String showPath = "/Statistics";
    }
}
