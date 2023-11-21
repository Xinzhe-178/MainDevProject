package com.example.lib_statistics;

/**
 * Created by 王鑫哲 on 2023/2/16 10:36 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class StatisticsConst {

    private static final String APP_NAME = "随心点";
    private static final String SEPARATE = "-";


    public static final String APP_START = concat(StatisticsConfig.Type.Click, "appLogo", "冷启动");


    private static String concat(String... content) {
        String s = "";
        if (content == null || content.length <= 0) {
            return s;
        }
        s = APP_NAME;
        for (int i = 0; i < content.length; i++) {
            s = s.concat(SEPARATE).concat(content[i]);
        }
        return s;
    }
}
