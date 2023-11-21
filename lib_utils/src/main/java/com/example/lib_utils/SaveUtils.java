package com.example.lib_utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.Looper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * Created by 王鑫哲 on 2022/7/30 12:11 上午
 * E-mail: User_wang_178@163.com
 * Ps:
 */
public class SaveUtils {

    public static void saveImage(String imgUrl, OnSaveCall call) {
        saveImage(String.valueOf(System.currentTimeMillis()), imgUrl, call);
    }

    public static void saveImage(String name, String imgUrl, OnSaveCall call) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                // 涉及到下载图片，调用netToLoacalBitmap时要放在子线程中
                saveImage(name, netToLoacalBitmap(imgUrl), call);
            }
        }.start();
    }

    /**
     * 保存图片到本地
     *
     * @param name   图片的名字，比如传入“123”，最终保存的图片为“123.jpg”
     * @param bitmap 本地图片或者网络图片转成的Bitmap格式的文件
     * @return
     */
    public static void saveImage(String name, Bitmap bitmap, OnSaveCall call) {
        if (TextUtils.isEmpty(name) || bitmap == null || call == null) {
            Looper.prepare();
            ToastUtils.show("保存失败");
            Looper.loop();
            return;
        }
        File pathFile = new File(Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_PICTURES + File.separator);
        if (!pathFile.exists()) {
            pathFile.mkdir();
        }
        File file = new File(pathFile, name + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            // 最后通知图库更新
            Uri localUri = Uri.fromFile(file);
            Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, localUri);
            UtilApplication.getInstance().sendBroadcast(localIntent);

            if (call != null) {
                call.success();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * todo 将网络资源图片转换为Bitmap
     *
     * @param imgUrl 网络资源图片路径
     * @return Bitmap
     * 该方法调用时要放在子线程中
     */
    public static Bitmap netToLoacalBitmap(String imgUrl) {
        Bitmap bitmap;
        InputStream in;
        BufferedOutputStream out;
        try {
            in = new BufferedInputStream(new URL(imgUrl).openStream(), 1024);
            final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
            out = new BufferedOutputStream(dataStream, 1024);
            copy(in, out);
            out.flush();
            byte[] data = dataStream.toByteArray();
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    public interface OnSaveCall {
        void success();
    }
}
