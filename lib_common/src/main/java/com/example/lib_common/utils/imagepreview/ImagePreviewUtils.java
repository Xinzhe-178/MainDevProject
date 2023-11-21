package com.example.lib_common.utils.imagepreview;

import android.app.Activity;

import com.previewlibrary.GPreviewBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 王鑫哲 on 2022/5/28 下午 09:43
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class ImagePreviewUtils {

    public static void start(Activity activity, List<ImageViewInfo> list, int position) {
        if (activity == null || list == null || list.size() == 0) {
            return;
        }
        //图片预览（关键
        GPreviewBuilder.from(activity)
                .setData(list)  //数据
                .setCurrentIndex(position)  //图片下标
                .setSingleFling(true)  //是否在黑屏区域点击返回
                .setDrag(false)  //是否禁用图片拖拽返回
                .setType(GPreviewBuilder.IndicatorType.Number)  //指示器类型
                .start();  //启动
    }

    public static void start(Activity activity, String imagePath, int position) {
        ArrayList<ImageViewInfo> imageViewInfo = new ArrayList<>();
        imageViewInfo.add(new ImageViewInfo(imagePath));
        start(activity, imageViewInfo, position);
    }
}
