package com.example.lib_utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by 王鑫哲 on 2023/7/27 2:53 下午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class JsonUtils {

    /**
     * 解析本地json
     *
     * @return
     * @throws Exception
     */
    public static String localityAnalyseJsonString(String fileName) throws Exception {
        UtilApplication instance = UtilApplication.getInstance();
        InputStream is = instance.getAssets().open(fileName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len;
        while ((len = is.read(bytes)) != -1) {
            baos.write(bytes, 0, len);
        }
        return baos.toString();
    }
}
