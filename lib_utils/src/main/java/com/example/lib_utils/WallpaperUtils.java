package com.example.lib_utils;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.text.TextUtils;

/**
 * Created by 王鑫哲 on 2022/9/4 17:55
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class WallpaperUtils {

    @SuppressLint("MissingPermission")
    public static void setDesktopWallpaper(String imaUrl, OnSetStateCall onSetStateCall) {
        if (TextUtils.isEmpty(imaUrl) || onSetStateCall == null) {
            return;
        }
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(UtilApplication.getInstance());
        new Thread(() -> {
            Bitmap bitmap = SaveUtils.netToLoacalBitmap(imaUrl);
            try {
                wallpaperManager.setBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
                onSetStateCall.setError();
            }
            onSetStateCall.setSuccess();
        }).start();
    }

    public interface OnSetStateCall {
        void setSuccess();

        void setError();
    }
}
