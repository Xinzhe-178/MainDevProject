package com.example.lib_utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 王鑫哲 on 2022/7/19 10:25 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ListUtil {

    @SafeVarargs
    public static <T> ArrayList create(T... data) {
        if (data == null || data.length <= 0) {
            return new ArrayList<T>();
        }
        return new ArrayList(Arrays.asList(data));
    }
}
