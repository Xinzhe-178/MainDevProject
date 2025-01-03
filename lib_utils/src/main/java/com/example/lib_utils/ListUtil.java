package com.example.lib_utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/7/19 10:25 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ListUtil {

    public static <T> List<T> create(T... data) {
        if (data == null || data.length == 0) {
            return new ArrayList<T>();
        }
        return Arrays.asList(data);
    }

    public static <T> boolean isEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return list != null && !list.isEmpty();
    }
}
